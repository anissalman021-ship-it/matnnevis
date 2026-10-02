package com.example.data.repository

import com.example.core.config.AppConfig
import com.example.data.local.dao.CategoryDao
import com.example.data.local.dao.NoteDao
import com.example.data.local.dao.NoteRevisionDao
import com.example.data.local.entity.CategoryEntity
import com.example.data.local.entity.NoteEntity
import com.example.data.local.entity.NoteRevisionEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONObject

class NoteRepository(
    private val noteDao: NoteDao,
    private val categoryDao: CategoryDao,
    private val revisionDao: NoteRevisionDao
) {
    val activeNotes: Flow<List<NoteEntity>> = noteDao.getActiveNotes()
    val favoriteNotes: Flow<List<NoteEntity>> = noteDao.getFavoriteNotes()
    val archivedNotes: Flow<List<NoteEntity>> = noteDao.getArchivedNotes()
    val trashNotes: Flow<List<NoteEntity>> = noteDao.getTrashNotes()
    val categories: Flow<List<CategoryEntity>> = categoryDao.getAllCategories()

    val activeCount: Flow<Int> = noteDao.getActiveNotesCount()
    val favoritesCount: Flow<Int> = noteDao.getFavoritesCount()
    val archivedCount: Flow<Int> = noteDao.getArchivedCount()
    val trashCount: Flow<Int> = noteDao.getTrashCount()

    fun getNotesByCategory(categoryId: Long): Flow<List<NoteEntity>> =
        noteDao.getNotesByCategory(categoryId)

    fun getNoteById(id: Long): Flow<NoteEntity?> = noteDao.getNoteById(id)

    suspend fun getNoteByIdSync(id: Long): NoteEntity? = noteDao.getNoteByIdSync(id)

    fun searchNotes(query: String): Flow<List<NoteEntity>> = noteDao.searchNotes(query)

    fun getRevisionsForNote(noteId: Long): Flow<List<NoteRevisionEntity>> =
        revisionDao.getRevisionsForNote(noteId)

    suspend fun saveNote(note: NoteEntity): Long = withContext(Dispatchers.IO) {
        val now = System.currentTimeMillis()
        val noteToSave = note.copy(updatedAt = now)

        val id = if (note.id == 0L) {
            val insertedId = noteDao.insertNote(noteToSave.copy(createdAt = now))
            revisionDao.insertRevision(
                NoteRevisionEntity(
                    noteId = insertedId,
                    title = note.title,
                    content = note.content,
                    timestamp = now
                )
            )
            insertedId
        } else {
            noteDao.updateNote(noteToSave)
            // Save revision snapshot
            val existing = noteDao.getNoteByIdSync(note.id)
            if (existing != null && (existing.title != note.title || existing.content != note.content)) {
                revisionDao.insertRevision(
                    NoteRevisionEntity(
                        noteId = note.id,
                        title = note.title,
                        content = note.content,
                        timestamp = now
                    )
                )
            }
            note.id
        }
        id
    }

    suspend fun restoreRevision(noteId: Long, revision: NoteRevisionEntity) = withContext(Dispatchers.IO) {
        val current = noteDao.getNoteByIdSync(noteId) ?: return@withContext
        val updated = current.copy(
            title = revision.title,
            content = revision.content,
            updatedAt = System.currentTimeMillis()
        )
        noteDao.updateNote(updated)
    }

    suspend fun moveToTrash(noteId: Long) = withContext(Dispatchers.IO) {
        noteDao.moveToTrash(noteId)
    }

    suspend fun restoreFromTrash(noteId: Long) = withContext(Dispatchers.IO) {
        noteDao.restoreFromTrash(noteId)
    }

    suspend fun deletePermanently(note: NoteEntity) = withContext(Dispatchers.IO) {
        revisionDao.deleteRevisionsForNote(note.id)
        noteDao.deletePermanently(note)
    }

    suspend fun emptyTrash() = withContext(Dispatchers.IO) {
        noteDao.emptyTrash()
    }

    suspend fun togglePin(note: NoteEntity) = withContext(Dispatchers.IO) {
        noteDao.updateNote(note.copy(isPinned = !note.isPinned, updatedAt = System.currentTimeMillis()))
    }

    suspend fun toggleFavorite(note: NoteEntity) = withContext(Dispatchers.IO) {
        noteDao.updateNote(note.copy(isFavorite = !note.isFavorite, updatedAt = System.currentTimeMillis()))
    }

    suspend fun toggleArchive(note: NoteEntity) = withContext(Dispatchers.IO) {
        noteDao.updateNote(note.copy(isArchived = !note.isArchived, isPinned = false, updatedAt = System.currentTimeMillis()))
    }

    // Categories
    suspend fun saveCategory(category: CategoryEntity): Long = withContext(Dispatchers.IO) {
        if (category.id == 0L) {
            categoryDao.insertCategory(category)
        } else {
            categoryDao.updateCategory(category)
            category.id
        }
    }

    suspend fun deleteCategory(category: CategoryEntity) = withContext(Dispatchers.IO) {
        categoryDao.deleteCategory(category)
    }

    // Batch operations
    suspend fun batchDelete(noteIds: List<Long>) = withContext(Dispatchers.IO) {
        noteIds.forEach { noteDao.moveToTrash(it) }
    }

    suspend fun batchArchive(noteIds: List<Long>, archive: Boolean) = withContext(Dispatchers.IO) {
        noteIds.forEach { id ->
            val note = noteDao.getNoteByIdSync(id)
            if (note != null) {
                noteDao.updateNote(note.copy(isArchived = archive, isPinned = false, updatedAt = System.currentTimeMillis()))
            }
        }
    }

    suspend fun batchFavorite(noteIds: List<Long>, favorite: Boolean) = withContext(Dispatchers.IO) {
        noteIds.forEach { id ->
            val note = noteDao.getNoteByIdSync(id)
            if (note != null) {
                noteDao.updateNote(note.copy(isFavorite = favorite, updatedAt = System.currentTimeMillis()))
            }
        }
    }

    suspend fun batchSetCategory(noteIds: List<Long>, categoryId: Long?) = withContext(Dispatchers.IO) {
        noteIds.forEach { id ->
            val note = noteDao.getNoteByIdSync(id)
            if (note != null) {
                noteDao.updateNote(note.copy(categoryId = categoryId, updatedAt = System.currentTimeMillis()))
            }
        }
    }

    // Backup & Restore
    suspend fun exportBackupJson(): String = withContext(Dispatchers.IO) {
        val notes = noteDao.getAllNotesSync()
        val categories = categoryDao.getAllCategoriesSync()

        val root = JSONObject().apply {
            put("appName", AppConfig.APP_NAME)
            put("author", AppConfig.AUTHOR_NAME)
            put("version", AppConfig.VERSION)
            put("timestamp", System.currentTimeMillis())

            val categoriesArray = JSONArray()
            categories.forEach { c ->
                categoriesArray.put(JSONObject().apply {
                    put("id", c.id)
                    put("name", c.name)
                    put("colorHex", c.colorHex)
                    put("iconName", c.iconName)
                    put("isDefault", c.isDefault)
                })
            }
            put("categories", categoriesArray)

            val notesArray = JSONArray()
            notes.forEach { n ->
                notesArray.put(JSONObject().apply {
                    put("title", n.title)
                    put("content", n.content)
                    put("colorId", n.colorId)
                    put("categoryId", n.categoryId ?: JSONObject.NULL)
                    put("isPinned", n.isPinned)
                    put("isFavorite", n.isFavorite)
                    put("isArchived", n.isArchived)
                    put("isInTrash", n.isInTrash)
                    put("isLocked", n.isLocked)
                    put("reminderTime", n.reminderTime ?: JSONObject.NULL)
                    put("createdAt", n.createdAt)
                    put("updatedAt", n.updatedAt)
                    put("tagsCsv", n.tagsCsv)
                    put("checklistsJson", n.checklistsJson)
                    put("attachmentsJson", n.attachmentsJson)
                })
            }
            put("notes", notesArray)
        }
        root.toString(2)
    }

    suspend fun importBackupJson(jsonString: String): Boolean = withContext(Dispatchers.IO) {
        try {
            val root = JSONObject(jsonString)
            if (!root.has("notes")) return@withContext false

            val categoriesArray = root.optJSONArray("categories")
            if (categoriesArray != null) {
                for (i in 0 until categoriesArray.length()) {
                    val obj = categoriesArray.getJSONObject(i)
                    val cat = CategoryEntity(
                        name = obj.getString("name"),
                        colorHex = obj.optString("colorHex", "#3B82F6"),
                        iconName = obj.optString("iconName", "folder"),
                        isDefault = obj.optBoolean("isDefault", false)
                    )
                    categoryDao.insertCategory(cat)
                }
            }

            val notesArray = root.getJSONArray("notes")
            for (i in 0 until notesArray.length()) {
                val obj = notesArray.getJSONObject(i)
                val catId = if (obj.isNull("categoryId")) null else obj.optLong("categoryId")
                val reminder = if (obj.isNull("reminderTime")) null else obj.optLong("reminderTime")

                val note = NoteEntity(
                    title = obj.optString("title", ""),
                    content = obj.optString("content", ""),
                    colorId = obj.optString("colorId", "default"),
                    categoryId = catId,
                    isPinned = obj.optBoolean("isPinned", false),
                    isFavorite = obj.optBoolean("isFavorite", false),
                    isArchived = obj.optBoolean("isArchived", false),
                    isInTrash = obj.optBoolean("isInTrash", false),
                    isLocked = obj.optBoolean("isLocked", false),
                    reminderTime = reminder,
                    createdAt = obj.optLong("createdAt", System.currentTimeMillis()),
                    updatedAt = obj.optLong("updatedAt", System.currentTimeMillis()),
                    tagsCsv = obj.optString("tagsCsv", ""),
                    checklistsJson = obj.optString("checklistsJson", ""),
                    attachmentsJson = obj.optString("attachmentsJson", "")
                )
                noteDao.insertNote(note)
            }
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    fun exportNoteAsTxt(note: NoteEntity): String {
        return buildString {
            appendLine(note.title.ifBlank { AppConfig.APP_NAME })
            appendLine("=".repeat(30))
            if (note.tagsCsv.isNotBlank()) {
                appendLine("برچسب‌ها: ${note.tagsCsv}")
            }
            appendLine()
            appendLine(note.content)
            appendLine()
            appendLine("—".repeat(30))
            appendLine("نوشته شده با متنویس اثر ${AppConfig.AUTHOR_NAME}")
        }
    }
}
