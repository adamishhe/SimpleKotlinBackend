package org.example.dto

import jakarta.validation.constraints.NotNull


data class UpdateTaskStatusDto(

    @field:NotNull(message = "Статус задачи обязателен")
    val status: TaskStatus
)