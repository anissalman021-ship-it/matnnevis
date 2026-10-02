package com.example.ui.screens.home

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Archive
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.PushPin
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.SelectAll
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.core.config.AppConfig
import com.example.core.i18n.LocalAppStrings
import com.example.ui.viewmodel.MainViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: MainViewModel,
    onNavigateToEditor: (Long) -> Unit,
    onNavigateToFavorites: () -> Unit,
    onNavigateToArchive: () -> Unit,
    onNavigateToTrash: () -> Unit,
    onNavigateToCategories: () -> Unit,
    onNavigateToCalendar: () -> Unit,
    onNavigateToSettings: () -> Unit
) {
    val strings = LocalAppStrings.current
    val prefs by viewModel.userPreferences.collectAsState()

    val displayNotes by viewModel.displayNotes.collectAsState()
    val categories by viewModel.categories.collectAsState()
    val activeCount by viewModel.activeCount.collectAsState()
    val favoritesCount by viewModel.favoritesCount.collectAsState()
    val archivedCount by viewModel.archivedCount.collectAsState()
    val trashCount by viewModel.trashCount.collectAsState()

    val searchQuery by viewModel.searchQuery.collectAsState()
    val selectedCategory by viewModel.selectedCategoryId.collectAsState()

    val isSelectionMode by viewModel.isSelectionMode.collectAsState()
    val selectedNoteIds by viewModel.selectedNoteIds.collectAsState()

    var showSortMenu by remember { mutableStateOf(false) }
    var showQuickNoteDialog by remember { mutableStateOf(false) }
    var showBatchDeleteConfirm by remember { mutableStateOf(false) }

    val pinnedNotes = remember(displayNotes) { displayNotes.filter { it.isPinned } }
    val regularNotes = remember(displayNotes) { displayNotes.filter { !it.isPinned } }

    Scaffold(
        topBar = {
            if (isSelectionMode) {
                TopAppBar(
                    title = {
                        Text(
                            text = "${selectedNoteIds.size} ${strings.selectedCount}",
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = { viewModel.clearSelection() }) {
                            Icon(imageVector = Icons.Default.Close, contentDescription = strings.clearSelection)
                        }
                    },
                    actions = {
                        IconButton(onClick = { viewModel.selectAllNotes() }) {
                            Icon(imageVector = Icons.Default.SelectAll, contentDescription = strings.selectAll)
                        }
                        IconButton(onClick = { viewModel.batchFavoriteSelected(true) }) {
                            Icon(imageVector = Icons.Default.Favorite, contentDescription = strings.favorite)
                        }
                        IconButton(onClick = { viewModel.batchArchiveSelected(true) }) {
                            Icon(imageVector = Icons.Default.Archive, contentDescription = strings.archive)
                        }
                        IconButton(onClick = { showBatchDeleteConfirm = true }) {
                            Icon(imageVector = Icons.Default.Delete, contentDescription = strings.delete, tint = Color(0xFFEF4444))
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer
                    )
                )
            } else {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            Text(
                                text = AppConfig.APP_NAME,
                                style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
                                color = MaterialTheme.colorScheme.primary
                            )
                            Text(
                                text = AppConfig.AUTHOR_NAME,
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            // Quick Note button
                            IconButton(
                                onClick = { showQuickNoteDialog = true },
                                modifier = Modifier.testTag("quick_note_button")
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Speed,
                                    contentDescription = strings.quickNote,
                                    tint = MaterialTheme.colorScheme.primary
                                )
                            }

                            // Sort selector
                            Box {
                                IconButton(onClick = { showSortMenu = true }) {
                                    Icon(imageVector = Icons.Default.FilterList, contentDescription = strings.moreOptions)
                                }
                                DropdownMenu(
                                    expanded = showSortMenu,
                                    onDismissRequest = { showSortMenu = false }
                                ) {
                                    DropdownMenuItem(
                                        text = { Text(strings.sortNewest) },
                                        onClick = {
                                            viewModel.setSortOrder("newest")
                                            showSortMenu = false
                                        }
                                    )
                                    DropdownMenuItem(
                                        text = { Text(strings.sortOldest) },
                                        onClick = {
                                            viewModel.setSortOrder("oldest")
                                            showSortMenu = false
                                        }
                                    )
                                    DropdownMenuItem(
                                        text = { Text(strings.sortTitle) },
                                        onClick = {
                                            viewModel.setSortOrder("title")
                                            showSortMenu = false
                                        }
                                    )
                                    DropdownMenuItem(
                                        text = { Text(strings.sortModified) },
                                        onClick = {
                                            viewModel.setSortOrder("modified")
                                            showSortMenu = false
                                        }
                                    )
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    // Search Bar
                    OutlinedTextField(
                        value = searchQuery,
                        onValueChange = { viewModel.setSearchQuery(it) },
                        placeholder = { Text(strings.searchPlaceholder, fontSize = 14.sp) },
                        leadingIcon = {
                            Icon(imageVector = Icons.Default.Search, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                        },
                        trailingIcon = {
                            if (searchQuery.isNotEmpty()) {
                                IconButton(onClick = { viewModel.setSearchQuery("") }) {
                                    Icon(imageVector = Icons.Default.Clear, contentDescription = strings.clearSelection)
                                }
                            }
                        },
                        singleLine = true,
                        shape = RoundedCornerShape(16.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = MaterialTheme.colorScheme.primary,
                            unfocusedBorderColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.4f),
                            focusedContainerColor = MaterialTheme.colorScheme.surface,
                            unfocusedContainerColor = MaterialTheme.colorScheme.surface
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("search_notes_field")
                    )
                }
            }
        },
        bottomBar = {
            NavigationBar(
                containerColor = MaterialTheme.colorScheme.surface,
                tonalElevation = 8.dp
            ) {
                NavigationBarItem(
                    selected = true,
                    onClick = { /* Already at home */ },
                    icon = { Icon(Icons.Default.Description, contentDescription = strings.allNotes) },
                    label = { Text(strings.allNotes, fontSize = 11.sp) }
                )
                NavigationBarItem(
                    selected = false,
                    onClick = onNavigateToCategories,
                    icon = { Icon(Icons.Default.Category, contentDescription = strings.categories) },
                    label = { Text(strings.categories, fontSize = 11.sp) }
                )
                NavigationBarItem(
                    selected = false,
                    onClick = onNavigateToCalendar,
                    icon = { Icon(Icons.Default.CalendarMonth, contentDescription = strings.calendar) },
                    label = { Text(strings.calendar, fontSize = 11.sp) }
                )
                NavigationBarItem(
                    selected = false,
                    onClick = onNavigateToFavorites,
                    icon = { Icon(Icons.Default.Favorite, contentDescription = strings.favorites) },
                    label = { Text(strings.favorites, fontSize = 11.sp) }
                )
                NavigationBarItem(
                    selected = false,
                    onClick = onNavigateToSettings,
                    icon = { Icon(Icons.Default.Settings, contentDescription = strings.settings) },
                    label = { Text(strings.settings, fontSize = 11.sp) }
                )
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { onNavigateToEditor(0L) },
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary,
                shape = CircleShape,
                modifier = Modifier.testTag("add_note_fab")
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = strings.newNote)
            }
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            // Dashboard summary
            if (prefs.showDashboard && searchQuery.isBlank() && selectedCategory == null) {
                item {
                    DashboardView(
                        totalNotes = activeCount,
                        favoriteNotes = favoritesCount,
                        archivedNotes = archivedCount,
                        trashNotes = trashCount,
                        onNavigateFavorites = onNavigateToFavorites,
                        onNavigateArchive = onNavigateToArchive,
                        onNavigateTrash = onNavigateToTrash
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                }
            }

            // Category Chips Scroll Row
            item {
                val scrollState = rememberScrollState()
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(scrollState),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    FilterChip(
                        selected = selectedCategory == null,
                        onClick = { viewModel.setSelectedCategory(null) },
                        label = { Text(strings.allCategories) },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                            selectedLabelColor = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    )

                    categories.forEach { cat ->
                        FilterChip(
                            selected = selectedCategory == cat.id,
                            onClick = {
                                if (selectedCategory == cat.id) {
                                    viewModel.setSelectedCategory(null)
                                } else {
                                    viewModel.setSelectedCategory(cat.id)
                                }
                            },
                            label = { Text(strings.localizeCategory(cat.name)) },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                                selectedLabelColor = MaterialTheme.colorScheme.onPrimaryContainer
                            )
                        )
                    }
                }
            }

            // Empty State
            if (displayNotes.isEmpty()) {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 48.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Description,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.3f),
                                modifier = Modifier.size(72.dp)
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            Text(
                                text = if (searchQuery.isNotEmpty()) strings.noNotesFound else strings.noNotesFound,
                                style = MaterialTheme.typography.titleMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = strings.createFirstNotePrompt,
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f)
                            )
                        }
                    }
                }
            }

            // Pinned Notes Section
            if (pinnedNotes.isNotEmpty()) {
                item {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(top = 6.dp, bottom = 2.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.PushPin,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = strings.pinnedNotes,
                            style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold),
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }

                items(pinnedNotes, key = { it.id }) { note ->
                    val cat = categories.firstOrNull { it.id == note.categoryId }
                    NoteCard(
                        note = note,
                        category = cat,
                        isSelected = selectedNoteIds.contains(note.id),
                        isSelectionMode = isSelectionMode,
                        onNoteClick = {
                            if (isSelectionMode) {
                                viewModel.toggleNoteSelection(note.id)
                            } else {
                                onNavigateToEditor(note.id)
                            }
                        },
                        onNoteLongClick = { viewModel.toggleNoteSelection(note.id) },
                        onTogglePin = { viewModel.togglePin(note) },
                        onToggleFavorite = { viewModel.toggleFavorite(note) },
                        onToggleArchive = { viewModel.toggleArchive(note) },
                        onDelete = { viewModel.moveToTrash(note) }
                    )
                }
            }

            // Regular Notes Section
            if (regularNotes.isNotEmpty()) {
                if (pinnedNotes.isNotEmpty()) {
                    item {
                        Text(
                            text = strings.allNotes,
                            style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold),
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.padding(top = 8.dp, bottom = 2.dp)
                        )
                    }
                }

                items(regularNotes, key = { it.id }) { note ->
                    val cat = categories.firstOrNull { it.id == note.categoryId }
                    NoteCard(
                        note = note,
                        category = cat,
                        isSelected = selectedNoteIds.contains(note.id),
                        isSelectionMode = isSelectionMode,
                        onNoteClick = {
                            if (isSelectionMode) {
                                viewModel.toggleNoteSelection(note.id)
                            } else {
                                onNavigateToEditor(note.id)
                            }
                        },
                        onNoteLongClick = { viewModel.toggleNoteSelection(note.id) },
                        onTogglePin = { viewModel.togglePin(note) },
                        onToggleFavorite = { viewModel.toggleFavorite(note) },
                        onToggleArchive = { viewModel.toggleArchive(note) },
                        onDelete = { viewModel.moveToTrash(note) }
                    )
                }
            }
        }
    }

    // Quick Note Dialog
    if (showQuickNoteDialog) {
        var quickTitle by remember { mutableStateOf("") }
        var quickContent by remember { mutableStateOf("") }

        AlertDialog(
            onDismissRequest = { showQuickNoteDialog = false },
            title = { Text(strings.quickNote, fontWeight = FontWeight.Bold) },
            text = {
                Column(modifier = Modifier.fillMaxWidth()) {
                    OutlinedTextField(
                        value = quickTitle,
                        onValueChange = { quickTitle = it },
                        placeholder = { Text(strings.titlePlaceholder) },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    OutlinedTextField(
                        value = quickContent,
                        onValueChange = { quickContent = it },
                        placeholder = { Text(strings.contentPlaceholder) },
                        minLines = 4,
                        maxLines = 8,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        if (quickTitle.isNotBlank() || quickContent.isNotBlank()) {
                            viewModel.loadNoteForEditing(0L)
                            viewModel.updateEditorTitle(quickTitle.ifBlank { strings.quickNote })
                            viewModel.updateEditorContent(quickContent)
                            viewModel.saveCurrentNoteImmediately()
                        }
                        showQuickNoteDialog = false
                    }
                ) {
                    Text(strings.save)
                }
            },
            dismissButton = {
                TextButton(onClick = { showQuickNoteDialog = false }) {
                    Text(strings.cancel)
                }
            }
        )
    }

    // Batch Delete Confirmation
    if (showBatchDeleteConfirm) {
        AlertDialog(
            onDismissRequest = { showBatchDeleteConfirm = false },
            title = { Text(strings.batchDeleteConfirmTitle, fontWeight = FontWeight.Bold) },
            text = { Text("${strings.batchDeleteConfirmMsg} (${selectedNoteIds.size} ${strings.selectedCount})") },
            confirmButton = {
                Button(
                    onClick = {
                        viewModel.batchDeleteSelected()
                        showBatchDeleteConfirm = false
                    }
                ) {
                    Text(strings.delete)
                }
            },
            dismissButton = {
                TextButton(onClick = { showBatchDeleteConfirm = false }) {
                    Text(strings.cancel)
                }
            }
        )
    }
}
