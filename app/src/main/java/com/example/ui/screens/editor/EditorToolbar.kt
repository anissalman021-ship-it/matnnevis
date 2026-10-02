package com.example.ui.screens.editor

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.FormatListBulleted
import androidx.compose.material.icons.filled.Code
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.FormatBold
import androidx.compose.material.icons.filled.FormatItalic
import androidx.compose.material.icons.filled.FormatListNumbered
import androidx.compose.material.icons.filled.FormatQuote
import androidx.compose.material.icons.filled.FormatStrikethrough
import androidx.compose.material.icons.filled.FormatUnderlined
import androidx.compose.material.icons.filled.Link
import androidx.compose.material.icons.filled.Redo
import androidx.compose.material.icons.filled.Title
import androidx.compose.material.icons.filled.Undo
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.example.core.i18n.LocalAppStrings

@Composable
fun EditorToolbar(
    onBold: () -> Unit,
    onItalic: () -> Unit,
    onUnderline: () -> Unit,
    onStrikethrough: () -> Unit,
    onHeading: () -> Unit,
    onQuote: () -> Unit,
    onBulletList: () -> Unit,
    onNumberedList: () -> Unit,
    onCode: () -> Unit,
    onLink: () -> Unit,
    onUndo: () -> Unit,
    onRedo: () -> Unit,
    onCopy: () -> Unit,
    modifier: Modifier = Modifier
) {
    val strings = LocalAppStrings.current

    Surface(
        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.7f),
        shape = RoundedCornerShape(12.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.25f)),
        modifier = modifier.fillMaxWidth()
    ) {
        val scrollState = rememberScrollState()
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 6.dp, vertical = 2.dp)
                .horizontalScroll(scrollState),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            ToolbarIconButton(Icons.Default.Undo, strings.undo, onUndo)
            ToolbarIconButton(Icons.Default.Redo, strings.redo, onRedo)
            ToolbarIconButton(Icons.Default.FormatBold, strings.bold, onBold)
            ToolbarIconButton(Icons.Default.FormatItalic, strings.italic, onItalic)
            ToolbarIconButton(Icons.Default.FormatUnderlined, strings.underline, onUnderline)
            ToolbarIconButton(Icons.Default.FormatStrikethrough, strings.strikethrough, onStrikethrough)
            ToolbarIconButton(Icons.Default.Title, strings.heading, onHeading)
            ToolbarIconButton(Icons.Default.FormatQuote, strings.quote, onQuote)
            ToolbarIconButton(Icons.AutoMirrored.Filled.FormatListBulleted, strings.bulletList, onBulletList)
            ToolbarIconButton(Icons.Default.FormatListNumbered, strings.numberedList, onNumberedList)
            ToolbarIconButton(Icons.Default.Code, strings.code, onCode)
            ToolbarIconButton(Icons.Default.Link, strings.link, onLink)
            ToolbarIconButton(Icons.Default.ContentCopy, strings.copy, onCopy)
        }
    }
}

@Composable
private fun ToolbarIconButton(
    icon: ImageVector,
    description: String,
    onClick: () -> Unit
) {
    IconButton(
        onClick = onClick,
        modifier = Modifier.size(36.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = description,
            tint = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.size(19.dp)
        )
    }
}
