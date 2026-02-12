package org.example.dto

enum class TaskSortField(val dbField: String) {
    STATUS("status"),
    PRIORITY("priority"),
    CREATED_AT("createdAt")
}