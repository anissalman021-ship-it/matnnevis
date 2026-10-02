package com.example.ui.screens.editor

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Archive
import androidx.compose.material.icons.filled.Brush
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Checklist
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.PushPin
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Stop
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.outlined.PushPin
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.core.i18n.LocalAppStrings
import com.example.data.local.model.AttachmentType
import com.example.media.AudioPlayerHelper
import com.example.media.AudioRecorderHelper
import com.example.ui.theme.NoteColors
import com.example.ui.viewmodel.MainViewModel
import java.io.File
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun NoteEditorScreen(
    noteId: Long,
    viewModel: MainViewModel,
    onNavigateBack: () -> Unit,
    onNavigateToFocus: (Long) -> Unit,
    onNavigateToReading: (Long) -> Unit
) {
    val context = LocalContext.current
    val strings = LocalAppStrings.current
    val clipboardManager = LocalClipboardManager.current

    LaunchedEffect(noteId) {
        viewModel.loadNoteForEditing(noteId)
    }

    val prefs by viewModel.userPreferences.collectAsState()
    val currentNote by viewModel.currentEditingNote.collectAsState()
    val checklists by viewModel.currentChecklists.collectAsState()
    val attachments by viewModel.currentAttachments.collectAsState()
    val revisions by viewModel.currentRevisions.collectAsState()
    val categories by viewModel.categories.collectAsState()

    var titleValue by remember { mutableStateOf(TextFieldValue("")) }
    var contentValue by remember { mutableStateOf(TextFieldValue("")) }

    // Synchronize initial state from DB
    LaunchedEffect(currentNote) {
        currentNote?.let {
            if (titleValue.text != it.title) {
                titleValue = TextFieldValue(it.title, TextRange(it.title.length))
            }
            if (contentValue.text != it.content) {
                contentValue = TextFieldValue(it.content, TextRange(it.content.length))
            }
        }
    }

    var showColorDialog by remember { mutableStateOf(false) }
    var showCategoryDialog by remember { mutableStateOf(false) }
    var showRevisionsDialog by remember { mutableStateOf(false) }
    var showDrawingDialog by remember { mutableStateOf(false) }
    var showMoreMenu by remember { mutableStateOf(false) }
    var newChecklistInput by remember { mutableStateOf("") }
    var newTagInput by remember { mutableStateOf("") }

    val audioRecorder = remember { AudioRecorderHelper(context) }
    val audioPlayer = remember { AudioPlayerHelper() }
    var isRecording by remember { mutableStateOf(false) }
    var isPlayingAudio by remember { mutableStateOf(false) }

    val isDark = isSystemInDarkTheme()
    val noteColor = NoteColors.fromId(currentNote?.colorId ?: "default")
    val editorBg = if (currentNote?.colorId == "default") {
        MaterialTheme.colorScheme.background
    } else {
        if (isDark) noteColor.darkBg else noteColor.lightBg
    }

    val wordCount = remember(contentValue.text) {
        contentValue.text.trim().split("\\s+".toRegex()).count { it.isNotBlank() }
    }
    val charCount = remember(contentValue.text) { contentValue.text.length }
    val lineCount = remember(contentValue.text) { contentValue.text.lines().size }

    BackHandler {
        viewModel.saveCurrentNoteImmediately()
        onNavigateBack()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = if (noteId == 0L) strings.newNote else strings.save,
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            viewModel.saveCurrentNoteImmediately()
                            onNavigateBack()
                        },
                        modifier = Modifier.testTag("editor_back_button")
                    ) {
                        Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = strings.backBtn)
                    }
                },
                actions = {
                    // Pin
                    IconButton(onClick = {
                        currentNote?.let { viewModel.togglePin(it) }
                    }) {
                        Icon(
                            imageVector = if (currentNote?.isPinned == true) Icons.Default.PushPin else Icons.Outlined.PushPin,
                            contentDescription = strings.pin,
                            tint = if (currentNote?.isPinned == true) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
                        )
                    }

                    // Favorite
                    IconButton(onClick = {
                        currentNote?.let { viewModel.toggleFavorite(it) }
                    }) {
                        Icon(
                            imageVector = if (currentNote?.isFavorite == true) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                            contentDescription = strings.favorite,
                            tint = if (currentNote?.isFavorite == true) Color(0xFFEF4444) else MaterialTheme.colorScheme.onSurface
                        )
                    }

                    // Reminder
                    IconButton(onClick = {
                        val cal = Calendar.getInstance()
                        DatePickerDialog(context, { _, year, month, day ->
                            TimePickerDialog(context, { _, hour, minute ->
                                val target = Calendar.getInstance().apply {
                                    set(year, month, day, hour, minute, 0)
                                }
                                viewModel.setNoteReminder(target.timeInMillis)
                                Toast.makeText(
                                    context,
                                    "${strings.reminder}: ${SimpleDateFormat("yyyy/MM/dd HH:mm", Locale.getDefault()).format(target.time)}",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), true).show()
                        }, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH)).show()
                    }) {
                        Icon(
                            imageVector = Icons.Default.Notifications,
                            contentDescription = strings.reminder,
                            tint = if (currentNote?.reminderTime != null) Color(0xFFF59E0B) else MaterialTheme.colorScheme.onSurface
                        )
                    }

                    // Color
                    IconButton(onClick = { showColorDialog = true }) {
                        Icon(imageVector = Icons.Default.Palette, contentDescription = strings.color, tint = MaterialTheme.colorScheme.primary)
                    }

                    // More Menu
                    Box {
                        IconButton(onClick = { showMoreMenu = true }) {
                            Icon(imageVector = Icons.Default.MoreVert, contentDescription = strings.moreOptions)
                        }
                        DropdownMenu(
                            expanded = showMoreMenu,
                            onDismissRequest = { showMoreMenu = false }
                        ) {
                            DropdownMenuItem(
                                leadingIcon = { Icon(Icons.Default.Visibility, contentDescription = null) },
                                text = { Text(strings.readingMode) },
                                onClick = {
                                    showMoreMenu = false
                                    viewModel.saveCurrentNoteImmediately()
                                    onNavigateToReading(currentNote?.id ?: 0L)
                                }
                            )
                            DropdownMenuItem(
                                leadingIcon = { Icon(Icons.Default.Brush, contentDescription = null) },
                                text = { Text(strings.focusMode) },
                                onClick = {
                                    showMoreMenu = false
                                    viewModel.saveCurrentNoteImmediately()
                                    onNavigateToFocus(currentNote?.id ?: 0L)
                                }
                            )
                            DropdownMenuItem(
                                leadingIcon = { Icon(Icons.Default.History, contentDescription = null) },
                                text = { Text(strings.versionHistory) },
                                onClick = {
                                    showMoreMenu = false
                                    showRevisionsDialog = true
                                }
                            )
                            DropdownMenuItem(
                                leadingIcon = { Icon(Icons.Default.Share, contentDescription = null) },
                                text = { Text(strings.share) },
                                onClick = {
                                    showMoreMenu = false
                                    currentNote?.let {
                                        val shareText = viewModel.exportNoteAsTxt(it)
                                        val sendIntent = Intent().apply {
                                            action = Intent.ACTION_SEND
                                            putExtra(Intent.EXTRA_TEXT, shareText)
                                            type = "text/plain"
                                        }
                                        context.startActivity(Intent.createChooser(sendIntent, strings.share))
                                    }
                                }
                            )
                            DropdownMenuItem(
                                leadingIcon = { Icon(Icons.Default.Archive, contentDescription = null) },
                                text = { Text(if (currentNote?.isArchived == true) strings.unarchive else strings.archive) },
                                onClick = {
                                    showMoreMenu = false
                                    currentNote?.let { viewModel.toggleArchive(it) }
                                }
                            )
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = editorBg
                )
            )
        },
        bottomBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(editorBg)
                    .imePadding()
            ) {
                EditorToolbar(
                    onBold = {
                        val sel = contentValue.selection
                        val text = contentValue.text
                        val newText = text.replaceRange(sel.min, sel.max, "**${text.substring(sel.min, sel.max)}**")
                        contentValue = TextFieldValue(newText, TextRange(sel.max + 4))
                        viewModel.updateEditorContent(newText)
                    },
                    onItalic = {
                        val sel = contentValue.selection
                        val text = contentValue.text
                        val newText = text.replaceRange(sel.min, sel.max, "*${text.substring(sel.min, sel.max)}*")
                        contentValue = TextFieldValue(newText, TextRange(sel.max + 2))
                        viewModel.updateEditorContent(newText)
                    },
                    onUnderline = {
                        val sel = contentValue.selection
                        val text = contentValue.text
                        val newText = text.replaceRange(sel.min, sel.max, "_${text.substring(sel.min, sel.max)}_")
                        contentValue = TextFieldValue(newText, TextRange(sel.max + 2))
                        viewModel.updateEditorContent(newText)
                    },
                    onStrikethrough = {
                        val sel = contentValue.selection
                        val text = contentValue.text
                        val newText = text.replaceRange(sel.min, sel.max, "~~${text.substring(sel.min, sel.max)}~~")
                        contentValue = TextFieldValue(newText, TextRange(sel.max + 4))
                        viewModel.updateEditorContent(newText)
                    },
                    onHeading = {
                        val sel = contentValue.selection
                        val text = contentValue.text
                        val newText = text.replaceRange(sel.min, sel.min, "\n# ")
                        contentValue = TextFieldValue(newText, TextRange(sel.min + 3))
                        viewModel.updateEditorContent(newText)
                    },
                    onQuote = {
                        val sel = contentValue.selection
                        val text = contentValue.text
                        val newText = text.replaceRange(sel.min, sel.min, "\n> ")
                        contentValue = TextFieldValue(newText, TextRange(sel.min + 3))
                        viewModel.updateEditorContent(newText)
                    },
                    onBulletList = {
                        val sel = contentValue.selection
                        val text = contentValue.text
                        val newText = text.replaceRange(sel.min, sel.min, "\n• ")
                        contentValue = TextFieldValue(newText, TextRange(sel.min + 3))
                        viewModel.updateEditorContent(newText)
                    },
                    onNumberedList = {
                        val sel = contentValue.selection
                        val text = contentValue.text
                        val newText = text.replaceRange(sel.min, sel.min, "\n1. ")
                        contentValue = TextFieldValue(newText, TextRange(sel.min + 4))
                        viewModel.updateEditorContent(newText)
                    },
                    onCode = {
                        val sel = contentValue.selection
                        val text = contentValue.text
                        val newText = text.replaceRange(sel.min, sel.max, "`${text.substring(sel.min, sel.max)}`")
                        contentValue = TextFieldValue(newText, TextRange(sel.max + 2))
                        viewModel.updateEditorContent(newText)
                    },
                    onLink = {
                        val sel = contentValue.selection
                        val text = contentValue.text
                        val newText = text.replaceRange(sel.min, sel.max, "[${text.substring(sel.min, sel.max)}](https://)")
                        contentValue = TextFieldValue(newText, TextRange(sel.max + 11))
                        viewModel.updateEditorContent(newText)
                    },
                    onUndo = {
                        if (revisions.isNotEmpty()) {
                            viewModel.restoreRevision(revisions.first())
                        }
                    },
                    onRedo = { },
                    onCopy = {
                        clipboardManager.setText(AnnotatedString(contentValue.text))
                        Toast.makeText(context, strings.textCopied, Toast.LENGTH_SHORT).show()
                    }
                )

                if (prefs.wordCounterEnabled) {
                    Surface(
                        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp, vertical = 4.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "$wordCount ${strings.wordsCount} | $charCount ${strings.charsCount} | $lineCount ${strings.linesCount}",
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Text(
                                text = strings.noteSaved,
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                }
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(editorBg)
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            val currentCategory = categories.firstOrNull { it.id == currentNote?.categoryId }
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                FilterChip(
                    selected = currentCategory != null,
                    onClick = { showCategoryDialog = true },
                    label = { Text(currentCategory?.let { strings.localizeCategory(it.name) } ?: strings.selectCategory) }
                )

                FilterChip(
                    selected = false,
                    onClick = { showDrawingDialog = true },
                    leadingIcon = { Icon(Icons.Default.Brush, contentDescription = null, modifier = Modifier.size(16.dp)) },
                    label = { Text(strings.drawing) }
                )

                FilterChip(
                    selected = isRecording,
                    onClick = {
                        if (!isRecording) {
                            val recordedPath = audioRecorder.startRecording()
                            if (recordedPath != null) {
                                isRecording = true
                                Toast.makeText(context, "${strings.recordVoice}...", Toast.LENGTH_SHORT).show()
                            }
                        } else {
                            val savedPath = audioRecorder.stopRecording()
                            isRecording = false
                            if (savedPath != null) {
                                viewModel.addAttachment(AttachmentType.AUDIO, savedPath, strings.recordVoice)
                                Toast.makeText(context, strings.noteSaved, Toast.LENGTH_SHORT).show()
                            }
                        }
                    },
                    leadingIcon = {
                        Icon(
                            imageVector = if (isRecording) Icons.Default.Stop else Icons.Default.Mic,
                            contentDescription = null,
                            tint = if (isRecording) Color.Red else MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(16.dp)
                        )
                    },
                    label = { Text(if (isRecording) strings.stopVoice else strings.recordVoice) }
                )
            }

            // Tags row
            val tagsList = (currentNote?.tagsCsv ?: "").split(",").filter { it.isNotBlank() }
            if (tagsList.isNotEmpty() || newTagInput.isNotEmpty()) {
                Spacer(modifier = Modifier.height(6.dp))
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    tagsList.forEach { tag ->
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = MaterialTheme.colorScheme.primary.copy(alpha = 0.15f),
                            border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.3f)),
                            modifier = Modifier.clickable {
                                val updated = tagsList.filter { it != tag }.joinToString(",")
                                viewModel.updateEditorTags(updated)
                            }
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                            ) {
                                Text(text = "#$tag", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.primary)
                                Spacer(modifier = Modifier.width(4.dp))
                                Icon(Icons.Default.Close, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(12.dp))
                            }
                        }
                    }
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = newTagInput,
                    onValueChange = { newTagInput = it },
                    placeholder = { Text(strings.addTagPlaceholder, fontSize = 12.sp) },
                    singleLine = true,
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.weight(1f)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Button(
                    onClick = {
                        if (newTagInput.isNotBlank()) {
                            val currentTags = (currentNote?.tagsCsv ?: "").split(",").filter { it.isNotBlank() }.toMutableList()
                            currentTags.add(newTagInput.trim())
                            viewModel.updateEditorTags(currentTags.joinToString(","))
                            newTagInput = ""
                        }
                    }
                ) {
                    Text(strings.addTagBtn)
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Title
            TextField(
                value = titleValue,
                onValueChange = {
                    titleValue = it
                    viewModel.updateEditorTitle(it.text)
                },
                placeholder = {
                    Text(
                        text = strings.titlePlaceholder,
                        style = MaterialTheme.typography.headlineSmall.copy(
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f)
                        )
                    )
                },
                textStyle = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("note_title_input")
            )

            Spacer(modifier = Modifier.height(4.dp))

            // Content
            TextField(
                value = contentValue,
                onValueChange = {
                    contentValue = it
                    viewModel.updateEditorContent(it.text)
                },
                placeholder = {
                    Text(
                        text = strings.contentPlaceholder,
                        style = MaterialTheme.typography.bodyLarge.copy(
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f)
                        )
                    )
                },
                textStyle = MaterialTheme.typography.bodyLarge,
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                minLines = 8,
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("note_content_input")
            )

            // Checklists
            Spacer(modifier = Modifier.height(16.dp))
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)),
                border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.Checklist, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = strings.checklist,
                                style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
                            )
                        }

                        if (checklists.isNotEmpty()) {
                            val completed = checklists.count { it.isChecked }
                            val progress = completed.toFloat() / checklists.size
                            Text(
                                text = "$completed / ${checklists.size} (${(progress * 100).toInt()}%)",
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                    }

                    if (checklists.isNotEmpty()) {
                        val completed = checklists.count { it.isChecked }
                        val progress = completed.toFloat() / checklists.size
                        Spacer(modifier = Modifier.height(8.dp))
                        LinearProgressIndicator(
                            progress = { progress },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(6.dp)
                                .clip(RoundedCornerShape(3.dp))
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    checklists.forEach { item ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 2.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Checkbox(
                                checked = item.isChecked,
                                onCheckedChange = { viewModel.toggleChecklistItem(item.id) }
                            )
                            Text(
                                text = item.text,
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    textDecoration = if (item.isChecked) TextDecoration.LineThrough else TextDecoration.None,
                                    color = if (item.isChecked) MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f) else MaterialTheme.colorScheme.onSurface
                                ),
                                modifier = Modifier.weight(1f)
                            )
                            IconButton(
                                onClick = { viewModel.removeChecklistItem(item.id) },
                                modifier = Modifier.size(28.dp)
                            ) {
                                Icon(Icons.Default.Close, contentDescription = strings.delete, tint = Color.Gray, modifier = Modifier.size(16.dp))
                            }
                        }
                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        OutlinedTextField(
                            value = newChecklistInput,
                            onValueChange = { newChecklistInput = it },
                            placeholder = { Text(strings.addItem, fontSize = 13.sp) },
                            singleLine = true,
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier.weight(1f)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Button(
                            onClick = {
                                if (newChecklistInput.isNotBlank()) {
                                    viewModel.addChecklistItem(newChecklistInput.trim())
                                    newChecklistInput = ""
                                }
                            }
                        ) {
                            Icon(Icons.Default.Add, contentDescription = null)
                        }
                    }
                }
            }

            // Attachments
            if (attachments.isNotEmpty()) {
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = strings.attachments,
                    style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.height(8.dp))

                attachments.forEach { att ->
                    Card(
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)),
                        border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(10.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(1f)) {
                                when (att.type) {
                                    AttachmentType.DRAWING, AttachmentType.IMAGE -> {
                                        Image(
                                            painter = rememberAsyncImagePainter(File(att.uriOrPath)),
                                            contentDescription = att.title,
                                            modifier = Modifier
                                                .size(54.dp)
                                                .clip(RoundedCornerShape(8.dp))
                                        )
                                        Spacer(modifier = Modifier.width(10.dp))
                                        Column {
                                            Text(att.title, fontWeight = FontWeight.SemiBold, fontSize = 14.sp)
                                            Text(strings.addImage, fontSize = 11.sp, color = Color.Gray)
                                        }
                                    }
                                    AttachmentType.AUDIO -> {
                                        IconButton(onClick = {
                                            if (isPlayingAudio) {
                                                audioPlayer.stop()
                                                isPlayingAudio = false
                                            } else {
                                                audioPlayer.play(att.uriOrPath) {
                                                    isPlayingAudio = false
                                                }
                                                isPlayingAudio = true
                                            }
                                        }) {
                                            Icon(
                                                imageVector = if (isPlayingAudio) Icons.Default.Stop else Icons.Default.PlayArrow,
                                                contentDescription = strings.recordVoice,
                                                tint = MaterialTheme.colorScheme.primary
                                            )
                                        }
                                        Spacer(modifier = Modifier.width(6.dp))
                                        Column {
                                            Text(att.title, fontWeight = FontWeight.SemiBold, fontSize = 14.sp)
                                            Text(strings.recordVoice, fontSize = 11.sp, color = Color.Gray)
                                        }
                                    }
                                    else -> {
                                        Icon(Icons.Default.Image, contentDescription = null)
                                        Spacer(modifier = Modifier.width(10.dp))
                                        Text(att.title)
                                    }
                                }
                            }

                            IconButton(onClick = { viewModel.removeAttachment(att.id) }) {
                                Icon(Icons.Default.Delete, contentDescription = strings.delete, tint = Color(0xFFEF4444))
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(80.dp))
        }
    }

    // Color Picker Dialog
    if (showColorDialog) {
        AlertDialog(
            onDismissRequest = { showColorDialog = false },
            title = { Text(strings.color, fontWeight = FontWeight.Bold) },
            text = {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    NoteColors.all.forEach { nc ->
                        Box(
                            modifier = Modifier
                                .size(44.dp)
                                .clip(CircleShape)
                                .background(nc.accent)
                                .border(
                                    width = if (currentNote?.colorId == nc.id) 3.dp else 1.dp,
                                    color = if (currentNote?.colorId == nc.id) Color.White else Color.Transparent,
                                    shape = CircleShape
                                )
                                .clickable {
                                    viewModel.updateEditorColor(nc.id)
                                    showColorDialog = false
                                }
                        )
                    }
                }
            },
            confirmButton = {
                TextButton(onClick = { showColorDialog = false }) {
                    Text(strings.cancel)
                }
            }
        )
    }

    // Category Selector Dialog
    if (showCategoryDialog) {
        AlertDialog(
            onDismissRequest = { showCategoryDialog = false },
            title = { Text(strings.selectCategory, fontWeight = FontWeight.Bold) },
            text = {
                Column(modifier = Modifier.fillMaxWidth()) {
                    TextButton(
                        onClick = {
                            viewModel.updateEditorCategory(null)
                            showCategoryDialog = false
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(strings.noCategory)
                    }
                    categories.forEach { cat ->
                        TextButton(
                            onClick = {
                                viewModel.updateEditorCategory(cat.id)
                                showCategoryDialog = false
                            },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(strings.localizeCategory(cat.name))
                        }
                    }
                }
            },
            confirmButton = {
                TextButton(onClick = { showCategoryDialog = false }) {
                    Text(strings.cancel)
                }
            }
        )
    }

    // Revision History Dialog
    if (showRevisionsDialog) {
        AlertDialog(
            onDismissRequest = { showRevisionsDialog = false },
            title = { Text(strings.versionHistory, fontWeight = FontWeight.Bold) },
            text = {
                if (revisions.isEmpty()) {
                    Text(strings.noHistoryYet)
                } else {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .verticalScroll(rememberScrollState())
                    ) {
                        revisions.forEach { rev ->
                            Card(
                                shape = RoundedCornerShape(10.dp),
                                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)),
                                border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.dp)
                            ) {
                                Column(modifier = Modifier.padding(10.dp)) {
                                    Text(
                                        text = SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.getDefault()).format(Date(rev.timestamp)),
                                        style = MaterialTheme.typography.labelSmall,
                                        color = MaterialTheme.colorScheme.primary
                                    )
                                    Text(
                                        text = rev.title.ifBlank { strings.titlePlaceholder },
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 14.sp
                                    )
                                    Text(
                                        text = rev.content.take(60),
                                        style = MaterialTheme.typography.bodySmall,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Button(
                                        onClick = {
                                            viewModel.restoreRevision(rev)
                                            showRevisionsDialog = false
                                        }
                                    ) {
                                        Text(strings.restoreVersion, fontSize = 12.sp)
                                    }
                                }
                            }
                        }
                    }
                }
            },
            confirmButton = {
                TextButton(onClick = { showRevisionsDialog = false }) {
                    Text(strings.cancel)
                }
            }
        )
    }

    // Drawing Canvas Modal
    if (showDrawingDialog) {
        DrawingCanvasDialog(
            onDismiss = { showDrawingDialog = false },
            onSaveDrawing = { path ->
                viewModel.addAttachment(AttachmentType.DRAWING, path, strings.drawing)
                showDrawingDialog = false
            }
        )
    }
}
