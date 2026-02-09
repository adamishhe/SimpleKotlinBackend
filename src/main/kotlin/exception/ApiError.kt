package org.example.exception

data class ApiError(
    val error: String,
    val message: String,
    val details: Map<String, String> = emptyMap()
)