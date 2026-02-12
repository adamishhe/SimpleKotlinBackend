package org.example.exception

class TaskNotFoundException(
    message: String = "Задача не найдена"
) : RuntimeException(message)