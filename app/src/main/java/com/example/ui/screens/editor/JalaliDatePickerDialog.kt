package com.example.ui.screens.editor

import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import android.icu.util.ULocale
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import java.util.Date

private val reminderPersianLocale = ULocale("fa_IR@calendar=persian")

@Composable
fun JalaliDatePickerDialog(
    onDismiss: () -> Unit,
    onDateSelected: (Long) -> Unit
) {
    val today = remember { Calendar.getInstance(reminderPersianLocale) }
    var monthOffset by remember { mutableIntStateOf(0) }
    var selectedMillis by remember { mutableLongStateOf(today.timeInMillis) }
    val month = remember(monthOffset) {
        Calendar.getInstance(reminderPersianLocale).apply {
            timeInMillis = today.timeInMillis
            add(Calendar.MONTH, monthOffset)
            set(Calendar.DAY_OF_MONTH, 1)
            set(Calendar.HOUR_OF_DAY, 12)
        }
    }
    val monthTitle = remember(month.timeInMillis) {
        SimpleDateFormat("MMMM yyyy", reminderPersianLocale).format(Date(month.timeInMillis))
    }
    val count = month.getActualMaximum(Calendar.DAY_OF_MONTH)
    val offset = (month.get(Calendar.DAY_OF_WEEK) - month.firstDayOfWeek + 7) % 7
    val selected = Calendar.getInstance(reminderPersianLocale).apply { timeInMillis = selectedMillis }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Column {
                Text("انتخاب تاریخ یادآور", style = MaterialTheme.typography.titleLarge)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = { monthOffset-- }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "ماه قبل")
                    }
                    Text(monthTitle, style = MaterialTheme.typography.titleMedium)
                    IconButton(onClick = { monthOffset++ }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = "ماه بعد")
                    }
                }
            }
        },
        text = {
            Column {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround) {
                    listOf("ش", "ی", "د", "س", "چ", "پ", "ج").forEach { Text(it) }
                }
                repeat(6) { week ->
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround) {
                        repeat(7) { dayColumn ->
                            val day = week * 7 + dayColumn - offset + 1
                            if (day in 1..count) {
                                val dayCalendar = (month.clone() as Calendar).apply {
                                    set(Calendar.DAY_OF_MONTH, day)
                                }
                                val isSelected = dayCalendar.get(Calendar.YEAR) == selected.get(Calendar.YEAR) &&
                                    dayCalendar.get(Calendar.MONTH) == selected.get(Calendar.MONTH) &&
                                    dayCalendar.get(Calendar.DAY_OF_MONTH) == selected.get(Calendar.DAY_OF_MONTH)
                                Box(
                                    modifier = Modifier
                                        .padding(vertical = 3.dp)
                                        .size(34.dp)
                                        .clip(CircleShape)
                                        .background(if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface)
                                        .clickable { selectedMillis = dayCalendar.timeInMillis },
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        day.toString(),
                                        color = if (isSelected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface
                                    )
                                }
                            } else {
                                Box(modifier = Modifier.size(40.dp))
                            }
                        }
                    }
                }
            }
        },
        confirmButton = {
            TextButton(onClick = { onDateSelected(selectedMillis) }) { Text("تأیید") }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("لغو") }
        }
    )
}

private fun jalaliDateTimeLabel(timeMillis: Long): String =
    SimpleDateFormat("yyyy/MM/dd HH:mm", reminderPersianLocale).format(Date(timeMillis))
