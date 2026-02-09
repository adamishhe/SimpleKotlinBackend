package org.example.exception.exceptions

class TaskNotFoundException(
    message: String = "Задача не найдена"
) : RuntimeException(message)