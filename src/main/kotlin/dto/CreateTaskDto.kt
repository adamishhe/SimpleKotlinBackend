package org.example.dto

import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class CreateTaskDto(

    @field:NotBlank(message = "Название задачи не может быть пустым")
    @field:Size(min = 3, max = 100, message = "Название задачи должно быть от 3 до 100 символов")
    val title: String,

    @field:Min(value = 1, message = "Приоритет должен быть не менее 1")
    @field:Max(value = 10, message = "Приоритет должен быть не более 10")
    val priority: Int
)