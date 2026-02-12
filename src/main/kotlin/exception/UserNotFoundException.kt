package org.example.exception

class UserNotFoundException(
    message: String  = "Пользователь не найден"
) : RuntimeException(message)