package org.example.repository

import org.example.dto.TaskStatus
import org.example.exception.exceptions.TaskNotFoundException
import org.example.model.TaskModel
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface TaskRepository : JpaRepository<TaskModel, UUID> {
    fun getTaskByTaskId(taskId : UUID) : TaskModel = findById(taskId).orElseThrow {
        TaskNotFoundException("Task with id $taskId not found")
    }

    fun deleteByUser_UserIdAndStatus(userId: UUID, status: TaskStatus): Int
}