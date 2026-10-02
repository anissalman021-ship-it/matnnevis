package com.example.data.local.model

data class ChecklistItem(
    val id: String = java.util.UUID.randomUUID().toString(),
    val text: String,
    val isChecked: Boolean = false,
    val order: Int = 0
)

data class AttachmentItem(
    val id: String = java.util.UUID.randomUUID().toString(),
    val type: AttachmentType,
    val uriOrPath: String,
    val title: String,
    val timestamp: Long = System.currentTimeMillis()
)

enum class AttachmentType {
    IMAGE,
    AUDIO,
    DRAWING,
    FILE,
    LINK
}
