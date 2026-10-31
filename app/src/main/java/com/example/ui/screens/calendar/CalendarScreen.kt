package com.example.ui.screens.calendar

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.EditNote
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.core.i18n.LocalAppStrings
import com.example.ui.screens.home.NoteCard
import com.example.ui.viewmodel.MainViewModel
import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar as PersianCalendar
import android.icu.util.ULocale
import java.util.Date

private val persianCalendarLocale = ULocale("fa_IR@calendar=persian")

private fun jalaliDateKey(timeMillis: Long): String {
    val calendar = PersianCalendar.getInstance(persianCalendarLocale).apply { this.timeInMillis = timeMillis }
    return "${calendar.get(PersianCalendar.YEAR)}-${calendar.get(PersianCalendar.MONTH)}-${calendar.get(PersianCalendar.DAY_OF_MONTH)}"
}

private fun jalaliDateLabel(timeMillis: Long): String =
    SimpleDateFormat("yyyy/MM/dd", persianCalendarLocale).format(Date(timeMillis))

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalendarScreen(
    viewModel: MainViewModel,
    onNavigateBack: () -> Unit,
    onNavigateToEditor: (Long) -> Unit
) {
    val strings = LocalAppStrings.current
    val activeNotes by viewModel.activeNotes.collectAsState()
    val categories by viewModel.categories.collectAsState()

    var calendarMonthOffset by remember { mutableIntStateOf(0) }
    var selectedDayTimeMillis by remember { mutableStateOf(System.currentTimeMillis()) }
    var selectedDayKey by remember { mutableStateOf(jalaliDateKey(selectedDayTimeMillis)) }

    val currentCalendar = remember(calendarMonthOffset) {
        PersianCalendar.getInstance(persianCalendarLocale).apply {
            timeInMillis = System.currentTimeMillis()
            add(PersianCalendar.MONTH, calendarMonthOffset)
            set(PersianCalendar.DAY_OF_MONTH, 1)
        }
    }

    val monthYearTitle = remember(currentCalendar.timeInMillis) {
        SimpleDateFormat("MMMM yyyy", persianCalendarLocale).format(Date(currentCalendar.timeInMillis))
    }

    val daysInMonth = currentCalendar.getActualMaximum(PersianCalendar.DAY_OF_MONTH)
    val firstDayOfWeek = currentCalendar.get(PersianCalendar.DAY_OF_WEEK)
    val leadingEmptyCells = (firstDayOfWeek - currentCalendar.firstDayOfWeek + 7) % 7

    val notesByDay = remember(activeNotes) {
        val map = mutableMapOf<String, Int>()
        activeNotes.forEach { note ->
            val key = jalaliDateKey(note.createdAt)
            map[key] = (map[key] ?: 0) + 1
        }
        map
    }

    val notesForSelectedDay = remember(selectedDayKey, activeNotes) {
        activeNotes.filter { note -> jalaliDateKey(note.createdAt) == selectedDayKey }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = strings.calendar,
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = strings.backBtn)
                    }
                },
                actions = {
                    IconButton(onClick = {
                        calendarMonthOffset = 0
                        selectedDayTimeMillis = System.currentTimeMillis()
                        selectedDayKey = jalaliDateKey(selectedDayTimeMillis)
                    }) {
                        Icon(imageVector = Icons.Default.CalendarToday, contentDescription = strings.todayBtn)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp)
        ) {
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)),
                border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp, vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    IconButton(onClick = { calendarMonthOffset-- }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = strings.prevMonth)
                    }

                    Text(
                        text = monthYearTitle,
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                        color = MaterialTheme.colorScheme.primary
                    )

                    IconButton(onClick = { calendarMonthOffset++ }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = strings.nextMonth)
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Localized Days of week
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                strings.daysOfWeek.forEach { dayLabel ->
                    Text(
                        text = dayLabel,
                        style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            Spacer(modifier = Modifier.height(6.dp))

            // Calendar Grid
            val totalCells = daysInMonth + leadingEmptyCells
            LazyVerticalGrid(
                columns = GridCells.Fixed(7),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(210.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp),
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                items(totalCells) { index ->
                    val dayNumber = index - leadingEmptyCells + 1
                    if (dayNumber in 1..daysInMonth) {
                        val tempCal = (currentCalendar.clone() as PersianCalendar).apply {
                            set(PersianCalendar.DAY_OF_MONTH, dayNumber)
                        }
                        val dateKey = jalaliDateKey(tempCal.timeInMillis)
                        val isSelected = (dateKey == selectedDayKey)
                        val notesCount = notesByDay[dateKey] ?: 0

                        Box(
                            modifier = Modifier
                                .size(36.dp)
                                .clip(CircleShape)
                                .background(if (isSelected) MaterialTheme.colorScheme.primary else Color.Transparent)
                                .border(
                                    width = if (isSelected) 0.dp else 1.dp,
                                    color = if (notesCount > 0) MaterialTheme.colorScheme.primary.copy(alpha = 0.5f) else Color.Transparent,
                                    shape = CircleShape
                                )
                                .clickable {
                                    selectedDayTimeMillis = tempCal.timeInMillis
                                    selectedDayKey = dateKey
                                },
                            contentAlignment = Alignment.Center
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text(
                                    text = "$dayNumber",
                                    fontSize = 13.sp,
                                    fontWeight = if (isSelected || notesCount > 0) FontWeight.Bold else FontWeight.Normal,
                                    color = if (isSelected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface
                                )
                                if (notesCount > 0) {
                                    Box(
                                        modifier = Modifier
                                            .size(4.dp)
                                            .clip(CircleShape)
                                            .background(if (isSelected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.primary)
                                    )
                                }
                            }
                        }
                    } else {
                        Box(modifier = Modifier.size(36.dp))
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Header for Selected Day Notes
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        text = "${strings.notesForDateLabel} ${jalaliDateLabel(selectedDayTimeMillis)}",
                        style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                        color = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        text = "${notesForSelectedDay.size} ${strings.notesFoundCountLabel}",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                Button(
                    onClick = {
                        viewModel.loadNoteForEditing(0L)
                        viewModel.updateEditorTitle("${strings.dailyNote} — ${jalaliDateLabel(selectedDayTimeMillis)}")
                        viewModel.updateEditorTags(strings.dailyNote)
                        viewModel.saveCurrentNoteImmediately()
                        onNavigateToEditor(0L)
                    }
                ) {
                    Icon(Icons.Default.EditNote, contentDescription = null, modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(strings.dailyNote, fontSize = 12.sp)
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            if (notesForSelectedDay.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = strings.noNotesInSelectedDay,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f)
                    )
                }
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    contentPadding = PaddingValues(bottom = 16.dp)
                ) {
                    items(notesForSelectedDay, key = { it.id }) { note ->
                        val cat = categories.firstOrNull { it.id == note.categoryId }
                        NoteCard(
                            note = note,
                            category = cat,
                            isSelected = false,
                            isSelectionMode = false,
                            onNoteClick = { onNavigateToEditor(note.id) },
                            onNoteLongClick = { },
                            onTogglePin = { viewModel.togglePin(note) },
                            onToggleFavorite = { viewModel.toggleFavorite(note) },
                            onToggleArchive = { viewModel.toggleArchive(note) },
                            onDelete = { viewModel.moveToTrash(note) }
                        )
                    }
                }
            }
        }
    }
}
