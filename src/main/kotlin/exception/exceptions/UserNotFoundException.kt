package org.example.exception.exceptions

class UserNotFoundException(
    message: String  = "Пользователь не найден"
) : RuntimeException(message)