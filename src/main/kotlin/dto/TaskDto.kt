package org.example.dto

import java.time.LocalDateTime
import java.util.UUID

data class TaskDto (
    val taskId : UUID,
    val title : String,
    val status : TaskStatus,
    val priority : Int,
    val createdAt : LocalDateTime
)