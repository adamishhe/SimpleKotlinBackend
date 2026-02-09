package org.example.dto

import java.time.LocalDateTime
import java.util.UUID

data class UserDto (
    val userId : UUID,
    val email : String,
    val createdAt: LocalDateTime
)