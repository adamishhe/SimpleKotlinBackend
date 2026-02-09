package org.example.exception.exceptions

class EmailAlreadyExistsException(
    message: String = "Пользователь с таким email уже существует"
) : RuntimeException(message)