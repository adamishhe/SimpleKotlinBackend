package org.example.exception

class EmailAlreadyExistsException(
    message: String = "Пользователь с таким email уже существует"
) : RuntimeException(message)