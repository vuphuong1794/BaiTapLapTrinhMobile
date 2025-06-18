package com.example.baitapmobile.responseModel

import kotlinx.serialization.Serializable

@Serializable
data class TaskResponse(
    val isSuccess: Boolean,
    val message: String,
    val data: List<Task>
)

@Serializable
data class Task(
    val id: Int,
    val title: String,
    val description: String,
    val status: String,
    val priority: String,
    val category: String,
    val dueDate: String,
    val createdAt: String,
    val updatedAt: String,
    val subtasks: List<Subtask>,
    val attachments: List<Attachment>,
    val reminders: List<Reminder>
)

@Serializable
data class Subtask(
    val id: Int,
    val title: String,
    val isCompleted: Boolean
)

@Serializable
data class Attachment(
    val id: Int,
    val fileName: String,
    val fileUrl: String
)

@Serializable
data class Reminder(
    val id: Int,
    val time: String,
    val type: String
)

@Serializable
data class TaskDetailResponse(
    val isSuccess: Boolean,
    val message: String,
    val data: Task  // Single task, not a list
)