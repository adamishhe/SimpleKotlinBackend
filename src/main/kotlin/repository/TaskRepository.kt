package org.example.repository

import org.example.dto.TaskStatus
import org.example.exception.TaskNotFoundException
import org.example.model.TaskModel
import org.springframework.data.domain.Sort
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface TaskRepository : JpaRepository<TaskModel, UUID> {
    fun getTaskByTaskId(taskId : UUID) : TaskModel = findById(taskId).orElseThrow {
        TaskNotFoundException("Task with id $taskId not found")
    }

    fun findByUserUserId(userId: UUID, sort: Sort): List<TaskModel>

    fun findByUserUserId(userId: UUID): List<TaskModel>

    fun deleteByUserUserIdAndStatus(userId: UUID, status: TaskStatus)
}