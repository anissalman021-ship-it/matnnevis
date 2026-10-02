package com.example.ui.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.MatnnevisApp
import com.example.core.i18n.AppStrings
import com.example.core.i18n.Language
import com.example.data.local.UserPreferences
import com.example.data.local.entity.CategoryEntity
import com.example.data.local.entity.NoteEntity
import com.example.data.local.entity.NoteRevisionEntity
import com.example.data.local.model.AttachmentItem
import com.example.data.local.model.AttachmentType
import com.example.data.local.model.ChecklistItem
import com.example.receiver.ReminderManager
import com.example.service.FloatingBubbleService
import com.example.ui.theme.AppThemePreset
import com.example.ui.theme.TextSizeScale
import com.example.ui.theme.ThemeMode
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.json.JSONArray
import org.json.JSONObject

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = (application as MatnnevisApp).repository
    private val preferences = (application as MatnnevisApp).preferences

    val userPreferences: StateFlow<UserPreferences> = preferences.userPreferencesFlow
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Eagerly,
            initialValue = UserPreferences()
        )

    val activeNotes: StateFlow<List<NoteEntity>> = repository.activeNotes
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val favoriteNotes: StateFlow<List<NoteEntity>> = repository.favoriteNotes
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val archivedNotes: StateFlow<List<NoteEntity>> = repository.archivedNotes
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val trashNotes: StateFlow<List<NoteEntity>> = repository.trashNotes
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val categories: StateFlow<List<CategoryEntity>> = repository.categories
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val activeCount: StateFlow<Int> = repository.activeCount
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0)

    val favoritesCount: StateFlow<Int> = repository.favoritesCount
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0)

    val archivedCount: StateFlow<Int> = repository.archivedCount
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0)

    val trashCount: StateFlow<Int> = repository.trashCount
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0)

    // Search and Filtering
    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _selectedCategoryId = MutableStateFlow<Long?>(null)
    val selectedCategoryId: StateFlow<Long?> = _selectedCategoryId.asStateFlow()

    private val _selectedTag = MutableStateFlow<String?>(null)
    val selectedTag: StateFlow<String?> = _selectedTag.asStateFlow()

    private val _sortOrder = MutableStateFlow("newest") // newest, oldest, title, modified
    val sortOrder: StateFlow<String> = _sortOrder.asStateFlow()

    // Filtered Notes for Home
    val displayNotes: StateFlow<List<NoteEntity>> = combine(
        activeNotes,
        _searchQuery,
        _selectedCategoryId,
        _selectedTag,
        _sortOrder
    ) { notes, query, catId, tag, sort ->
        var list = notes

        if (catId != null) {
            list = list.filter { it.categoryId == catId }
        }

        if (!tag.isNullOrBlank()) {
            list = list.filter { it.tagsCsv.split(",").map { t -> t.trim() }.contains(tag) }
        }

        if (query.isNotBlank()) {
            val q = query.lowercase().trim()
            list = list.filter {
                it.title.lowercase().contains(q) ||
                it.content.lowercase().contains(q) ||
                it.tagsCsv.lowercase().contains(q)
            }
        }

        when (sort) {
            "oldest" -> list.sortedWith(compareBy({ !it.isPinned }, { it.createdAt }))
            "title" -> list.sortedWith(compareBy({ !it.isPinned }, { it.title.lowercase() }))
            "modified" -> list.sortedWith(compareBy({ !it.isPinned }, { -it.updatedAt }))
            else -> list.sortedWith(compareBy({ !it.isPinned }, { -it.updatedAt }))
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // Selection Mode (Batch operations)
    private val _isSelectionMode = MutableStateFlow(false)
    val isSelectionMode: StateFlow<Boolean> = _isSelectionMode.asStateFlow()

    private val _selectedNoteIds = MutableStateFlow<Set<Long>>(emptySet())
    val selectedNoteIds: StateFlow<Set<Long>> = _selectedNoteIds.asStateFlow()

    // App Lock PIN authentication state
    private val _isAppUnlocked = MutableStateFlow(false)
    val isAppUnlocked: StateFlow<Boolean> = _isAppUnlocked.asStateFlow()

    // Active Note for Editing
    private val _currentEditingNote = MutableStateFlow<NoteEntity?>(null)
    val currentEditingNote: StateFlow<NoteEntity?> = _currentEditingNote.asStateFlow()

    private val _currentChecklists = MutableStateFlow<List<ChecklistItem>>(emptyList())
    val currentChecklists: StateFlow<List<ChecklistItem>> = _currentChecklists.asStateFlow()

    private val _currentAttachments = MutableStateFlow<List<AttachmentItem>>(emptyList())
    val currentAttachments: StateFlow<List<AttachmentItem>> = _currentAttachments.asStateFlow()

    private val _currentRevisions = MutableStateFlow<List<NoteRevisionEntity>>(emptyList())
    val currentRevisions: StateFlow<List<NoteRevisionEntity>> = _currentRevisions.asStateFlow()

    // User Message Toast / SnackBar
    private val _messageEvent = MutableSharedFlow<(AppStrings.Strings) -> String>()
    val messageEvent: SharedFlow<(AppStrings.Strings) -> String> = _messageEvent.asSharedFlow()

    private var autoSaveJob: Job? = null

    fun setSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun setSelectedCategory(catId: Long?) {
        _selectedCategoryId.value = catId
    }

    fun setSelectedTag(tag: String?) {
        _selectedTag.value = tag
    }

    fun setSortOrder(sort: String) {
        _sortOrder.value = sort
        viewModelScope.launch { preferences.setDefaultSort(sort) }
    }

    fun unlockApp() {
        _isAppUnlocked.value = true
    }

    // Batch operations
    fun toggleNoteSelection(noteId: Long) {
        val current = _selectedNoteIds.value.toMutableSet()
        if (current.contains(noteId)) {
            current.remove(noteId)
        } else {
            current.add(noteId)
        }
        _selectedNoteIds.value = current
        _isSelectionMode.value = current.isNotEmpty()
    }

    fun selectAllNotes() {
        val allIds = displayNotes.value.map { it.id }.toSet()
        _selectedNoteIds.value = allIds
        _isSelectionMode.value = allIds.isNotEmpty()
    }

    fun clearSelection() {
        _selectedNoteIds.value = emptySet()
        _isSelectionMode.value = false
    }

    fun batchDeleteSelected() {
        viewModelScope.launch {
            val ids = _selectedNoteIds.value.toList()
            repository.batchDelete(ids)
            clearSelection()
            _messageEvent.emit { it.batchDeleteSuccess }
        }
    }

    fun batchArchiveSelected(archive: Boolean) {
        viewModelScope.launch {
            val ids = _selectedNoteIds.value.toList()
            repository.batchArchive(ids, archive)
            clearSelection()
            _messageEvent.emit { if (archive) it.batchArchiveSuccess else it.batchUnarchiveSuccess }
        }
    }

    fun batchFavoriteSelected(fav: Boolean) {
        viewModelScope.launch {
            val ids = _selectedNoteIds.value.toList()
            repository.batchFavorite(ids, fav)
            clearSelection()
        }
    }

    fun batchSetCategorySelected(categoryId: Long?) {
        viewModelScope.launch {
            val ids = _selectedNoteIds.value.toList()
            repository.batchSetCategory(ids, categoryId)
            clearSelection()
        }
    }

    // Individual Note Actions
    fun togglePin(note: NoteEntity) {
        viewModelScope.launch { repository.togglePin(note) }
    }

    fun toggleFavorite(note: NoteEntity) {
        viewModelScope.launch { repository.toggleFavorite(note) }
    }

    fun toggleArchive(note: NoteEntity) {
        viewModelScope.launch {
            repository.toggleArchive(note)
            _messageEvent.emit { if (!note.isArchived) it.batchArchiveSuccess else it.batchUnarchiveSuccess }
        }
    }

    fun moveToTrash(note: NoteEntity) {
        viewModelScope.launch {
            repository.moveToTrash(note.id)
            _messageEvent.emit { it.noteMovedToTrash }
        }
    }

    fun restoreFromTrash(note: NoteEntity) {
        viewModelScope.launch {
            repository.restoreFromTrash(note.id)
            _messageEvent.emit { it.noteRestoredMsg }
        }
    }

    fun deletePermanently(note: NoteEntity) {
        viewModelScope.launch {
            repository.deletePermanently(note)
            _messageEvent.emit { it.notePermanentlyDeletedMsg }
        }
    }

    fun emptyTrash() {
        viewModelScope.launch {
            repository.emptyTrash()
            _messageEvent.emit { it.trashEmptiedMsg }
        }
    }

    // Editor Loading & Auto-Save
    fun loadNoteForEditing(noteId: Long?) {
        viewModelScope.launch {
            if (noteId == null || noteId == 0L) {
                _currentEditingNote.value = NoteEntity(
                    colorId = userPreferences.value.defaultNoteColor
                )
                _currentChecklists.value = emptyList()
                _currentAttachments.value = emptyList()
                _currentRevisions.value = emptyList()
            } else {
                val note = repository.getNoteByIdSync(noteId)
                if (note != null) {
                    _currentEditingNote.value = note
                    _currentChecklists.value = parseChecklists(note.checklistsJson)
                    _currentAttachments.value = parseAttachments(note.attachmentsJson)
                    loadRevisions(note.id)
                }
            }
        }
    }

    private fun loadRevisions(noteId: Long) {
        viewModelScope.launch {
            repository.getRevisionsForNote(noteId).collect {
                _currentRevisions.value = it
            }
        }
    }

    fun updateEditorTitle(title: String) {
        _currentEditingNote.value = _currentEditingNote.value?.copy(title = title)
        triggerDebouncedAutoSave()
    }

    fun updateEditorContent(content: String) {
        _currentEditingNote.value = _currentEditingNote.value?.copy(content = content)
        triggerDebouncedAutoSave()
    }

    fun updateEditorColor(colorId: String) {
        _currentEditingNote.value = _currentEditingNote.value?.copy(colorId = colorId)
        triggerDebouncedAutoSave()
    }

    fun updateEditorCategory(categoryId: Long?) {
        _currentEditingNote.value = _currentEditingNote.value?.copy(categoryId = categoryId)
        triggerDebouncedAutoSave()
    }

    fun updateEditorTags(tagsCsv: String) {
        _currentEditingNote.value = _currentEditingNote.value?.copy(tagsCsv = tagsCsv)
        triggerDebouncedAutoSave()
    }

    fun setNoteReminder(timeMillis: Long?) {
        val current = _currentEditingNote.value ?: return
        val updated = current.copy(reminderTime = timeMillis)
        _currentEditingNote.value = updated
        saveCurrentNoteImmediately()

        if (timeMillis != null && timeMillis > System.currentTimeMillis()) {
            val strings = AppStrings.get(userPreferences.value.language)
            ReminderManager.scheduleReminder(
                getApplication(),
                updated.id,
                updated.title.ifBlank { strings.reminderNotificationTitle },
                updated.content.ifBlank { strings.reminderNotificationBody },
                timeMillis
            )
        } else if (timeMillis == null) {
            ReminderManager.cancelReminder(getApplication(), updated.id)
        }
    }

    // Checklists management in Editor
    fun addChecklistItem(text: String) {
        if (text.isBlank()) return
        val current = _currentChecklists.value.toMutableList()
        current.add(ChecklistItem(text = text, order = current.size))
        _currentChecklists.value = current
        saveCurrentNoteImmediately()
    }

    fun toggleChecklistItem(id: String) {
        val current = _currentChecklists.value.map {
            if (it.id == id) it.copy(isChecked = !it.isChecked) else it
        }
        _currentChecklists.value = current
        saveCurrentNoteImmediately()
    }

    fun removeChecklistItem(id: String) {
        val current = _currentChecklists.value.filter { it.id != id }
        _currentChecklists.value = current
        saveCurrentNoteImmediately()
    }

    // Attachments management in Editor
    fun addAttachment(type: AttachmentType, uriOrPath: String, title: String) {
        val current = _currentAttachments.value.toMutableList()
        current.add(AttachmentItem(type = type, uriOrPath = uriOrPath, title = title))
        _currentAttachments.value = current
        saveCurrentNoteImmediately()
    }

    fun removeAttachment(id: String) {
        val current = _currentAttachments.value.filter { it.id != id }
        _currentAttachments.value = current
        saveCurrentNoteImmediately()
    }

    // Auto-save logic
    private fun triggerDebouncedAutoSave() {
        if (!userPreferences.value.autoSaveEnabled) return
        autoSaveJob?.cancel()
        autoSaveJob = viewModelScope.launch {
            delay(800)
            saveCurrentNoteImmediately()
        }
    }

    fun saveCurrentNoteImmediately() {
        val note = _currentEditingNote.value ?: return
        if (note.title.isBlank() && note.content.isBlank() && _currentChecklists.value.isEmpty() && _currentAttachments.value.isEmpty()) {
            return
        }

        viewModelScope.launch(Dispatchers.IO) {
            val checklistsStr = serializeChecklists(_currentChecklists.value)
            val attachmentsStr = serializeAttachments(_currentAttachments.value)
            val toSave = note.copy(
                checklistsJson = checklistsStr,
                attachmentsJson = attachmentsStr
            )
            val savedId = repository.saveNote(toSave)
            _currentEditingNote.value = toSave.copy(id = savedId)
        }
    }

    fun restoreRevision(revision: NoteRevisionEntity) {
        val current = _currentEditingNote.value ?: return
        viewModelScope.launch {
            repository.restoreRevision(current.id, revision)
            _currentEditingNote.value = current.copy(
                title = revision.title,
                content = revision.content
            )
            _messageEvent.emit { it.revisionRestoredMsg }
        }
    }

    // Category CRUD
    fun saveCategory(category: CategoryEntity) {
        viewModelScope.launch {
            repository.saveCategory(category)
            _messageEvent.emit { it.categorySavedMsg }
        }
    }

    fun deleteCategory(category: CategoryEntity) {
        viewModelScope.launch {
            repository.deleteCategory(category)
            _messageEvent.emit { it.categoryDeletedMsg }
        }
    }

    // Settings actions
    fun setThemePreset(preset: AppThemePreset) {
        viewModelScope.launch { preferences.setThemePreset(preset) }
    }

    fun setThemeMode(mode: ThemeMode) {
        viewModelScope.launch { preferences.setThemeMode(mode) }
    }

    fun setTextSizeScale(scale: TextSizeScale) {
        viewModelScope.launch { preferences.setTextSizeScale(scale) }
    }

    fun setLanguage(language: Language) {
        viewModelScope.launch {
            val locale = java.util.Locale(language.code)
            java.util.Locale.setDefault(locale)
            preferences.setLanguage(language)
        }
    }

    fun setWordCounterEnabled(enabled: Boolean) {
        viewModelScope.launch { preferences.setWordCounterEnabled(enabled) }
    }

    fun setAutoSaveEnabled(enabled: Boolean) {
        viewModelScope.launch { preferences.setAutoSaveEnabled(enabled) }
    }

    fun setDefaultNoteColor(colorId: String) {
        viewModelScope.launch { preferences.setDefaultNoteColor(colorId) }
    }

    fun setAppLock(pin: String, enabled: Boolean) {
        viewModelScope.launch {
            preferences.setAppLock(pin, enabled)
            _messageEvent.emit { if (enabled) it.appLockEnabledMsg else it.appLockDisabledMsg }
        }
    }

    fun setFloatingModeEnabled(context: Context, enabled: Boolean) {
        viewModelScope.launch {
            preferences.setFloatingModeEnabled(enabled)
            if (enabled && FloatingBubbleService.hasOverlayPermission(context)) {
                FloatingBubbleService.start(context)
            } else {
                FloatingBubbleService.stop(context)
            }
        }
    }

    fun setShowDashboard(show: Boolean) {
        viewModelScope.launch { preferences.setShowDashboard(show) }
    }

    fun setOnboardingDone(done: Boolean) {
        viewModelScope.launch { preferences.setOnboardingDone(done) }
    }

    // Backup & Restore
    suspend fun exportBackupJson(): String = repository.exportBackupJson()

    fun importBackup(jsonString: String, onComplete: (Boolean) -> Unit) {
        viewModelScope.launch {
            val result = repository.importBackupJson(jsonString)
            onComplete(result)
            if (result) {
                _messageEvent.emit { it.backupSuccessMsg }
            } else {
                _messageEvent.emit { it.backupErrorMsg }
            }
        }
    }

    fun exportNoteAsTxt(note: NoteEntity): String = repository.exportNoteAsTxt(note)

    // JSON serialization helpers
    private fun parseChecklists(json: String): List<ChecklistItem> {
        if (json.isBlank()) return emptyList()
        val list = mutableListOf<ChecklistItem>()
        try {
            val array = JSONArray(json)
            for (i in 0 until array.length()) {
                val obj = array.getJSONObject(i)
                list.add(
                    ChecklistItem(
                        id = obj.optString("id", java.util.UUID.randomUUID().toString()),
                        text = obj.optString("text", ""),
                        isChecked = obj.optBoolean("isChecked", false),
                        order = obj.optInt("order", i)
                    )
                )
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return list
    }

    private fun serializeChecklists(items: List<ChecklistItem>): String {
        val array = JSONArray()
        items.forEach {
            array.put(JSONObject().apply {
                put("id", it.id)
                put("text", it.text)
                put("isChecked", it.isChecked)
                put("order", it.order)
            })
        }
        return array.toString()
    }

    private fun parseAttachments(json: String): List<AttachmentItem> {
        if (json.isBlank()) return emptyList()
        val list = mutableListOf<AttachmentItem>()
        try {
            val array = JSONArray(json)
            for (i in 0 until array.length()) {
                val obj = array.getJSONObject(i)
                list.add(
                    AttachmentItem(
                        id = obj.optString("id", java.util.UUID.randomUUID().toString()),
                        type = AttachmentType.valueOf(obj.optString("type", AttachmentType.IMAGE.name)),
                        uriOrPath = obj.optString("uriOrPath", ""),
                        title = obj.optString("title", ""),
                        timestamp = obj.optLong("timestamp", System.currentTimeMillis())
                    )
                )
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return list
    }

    private fun serializeAttachments(items: List<AttachmentItem>): String {
        val array = JSONArray()
        items.forEach {
            array.put(JSONObject().apply {
                put("id", it.id)
                put("type", it.type.name)
                put("uriOrPath", it.uriOrPath)
                put("title", it.title)
                put("timestamp", it.timestamp)
            })
        }
        return array.toString()
    }
}
