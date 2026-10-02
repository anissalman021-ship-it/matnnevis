package com.example.ui.screens.category

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.core.i18n.LocalAppStrings
import com.example.data.local.entity.CategoryEntity
import com.example.ui.viewmodel.MainViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryScreen(
    viewModel: MainViewModel,
    onNavigateBack: () -> Unit
) {
    val strings = LocalAppStrings.current
    val categories by viewModel.categories.collectAsState()
    var showAddEditDialog by remember { mutableStateOf(false) }
    var editingCategory by remember { mutableStateOf<CategoryEntity?>(null) }
    var categoryToDelete by remember { mutableStateOf<CategoryEntity?>(null) }

    var categoryNameInput by remember { mutableStateOf("") }
    var selectedColorHex by remember { mutableStateOf("#3B82F6") }

    val presetColors = listOf(
        "#3B82F6", "#10B981", "#F59E0B", "#8B5CF6",
        "#EC4899", "#14B8A6", "#EF4444", "#6B7280"
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = strings.categories,
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = strings.backBtn)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    editingCategory = null
                    categoryNameInput = ""
                    selectedColorHex = "#3B82F6"
                    showAddEditDialog = true
                },
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary,
                shape = CircleShape
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = strings.newCategory)
            }
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(categories, key = { it.id }) { cat ->
                val catColor = try {
                    Color(android.graphics.Color.parseColor(cat.colorHex))
                } catch (e: Exception) {
                    MaterialTheme.colorScheme.primary
                }

                Card(
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)),
                    border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(16.dp)
                                    .clip(CircleShape)
                                    .background(catColor)
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            Column {
                                Text(
                                    text = strings.localizeCategory(cat.name),
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 15.sp,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                            }
                        }

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            IconButton(onClick = {
                                editingCategory = cat
                                categoryNameInput = cat.name
                                selectedColorHex = cat.colorHex
                                showAddEditDialog = true
                            }) {
                                Icon(Icons.Default.Edit, contentDescription = strings.editCategory, modifier = Modifier.size(18.dp))
                            }
                            if (!cat.isDefault) {
                                IconButton(onClick = { categoryToDelete = cat }) {
                                    Icon(Icons.Default.Delete, contentDescription = strings.delete, tint = Color(0xFFEF4444), modifier = Modifier.size(18.dp))
                                }
                            }
                        }
                    }
                }
            }
        }

        // Add / Edit Category Dialog
        if (showAddEditDialog) {
            AlertDialog(
                onDismissRequest = { showAddEditDialog = false },
                title = {
                    Text(
                        text = if (editingCategory == null) strings.newCategory else strings.editCategory,
                        fontWeight = FontWeight.Bold
                    )
                },
                text = {
                    Column(modifier = Modifier.fillMaxWidth()) {
                        OutlinedTextField(
                            value = categoryNameInput,
                            onValueChange = { categoryNameInput = it },
                            label = { Text(strings.categoryName) },
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth()
                        )

                        Spacer(modifier = Modifier.height(14.dp))
                        Text(text = strings.categoryColor, style = MaterialTheme.typography.labelMedium)
                        Spacer(modifier = Modifier.height(8.dp))

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .horizontalScroll(rememberScrollState()),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            presetColors.forEach { hex ->
                                val col = Color(android.graphics.Color.parseColor(hex))
                                Box(
                                    modifier = Modifier
                                        .size(36.dp)
                                        .clip(CircleShape)
                                        .background(col)
                                        .border(
                                            width = if (selectedColorHex.equals(hex, ignoreCase = true)) 3.dp else 1.dp,
                                            color = if (selectedColorHex.equals(hex, ignoreCase = true)) MaterialTheme.colorScheme.primary else Color.Transparent,
                                            shape = CircleShape
                                        )
                                        .clickable { selectedColorHex = hex }
                                )
                            }
                        }
                    }
                },
                confirmButton = {
                    Button(
                        onClick = {
                            if (categoryNameInput.isNotBlank()) {
                                val cat = CategoryEntity(
                                    id = editingCategory?.id ?: 0L,
                                    name = categoryNameInput.trim(),
                                    colorHex = selectedColorHex,
                                    iconName = "folder",
                                    isDefault = editingCategory?.isDefault ?: false
                                )
                                viewModel.saveCategory(cat)
                                showAddEditDialog = false
                            }
                        }
                    ) {
                        Text(strings.save)
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showAddEditDialog = false }) {
                        Text(strings.cancel)
                    }
                }
            )
        }

        // Delete Category Confirmation Dialog
        categoryToDelete?.let { cat ->
            AlertDialog(
                onDismissRequest = { categoryToDelete = null },
                title = { Text(strings.delete, fontWeight = FontWeight.Bold) },
                text = { Text("${strings.categoryConfirmDelete} (${cat.name})") },
                confirmButton = {
                    Button(
                        onClick = {
                            viewModel.deleteCategory(cat)
                            categoryToDelete = null
                        }
                    ) {
                        Text(strings.delete)
                    }
                },
                dismissButton = {
                    TextButton(onClick = { categoryToDelete = null }) {
                        Text(strings.cancel)
                    }
                }
            )
        }
    }
}
