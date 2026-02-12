package org.example.service

import org.example.dto.*
import org.example.mapper.TaskMapper
import org.example.model.TaskModel
import org.example.repository.TaskRepository
import org.example.repository.UserRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class TaskService(
    private val userRepository : UserRepository,
    private val taskRepository : TaskRepository,
    private val taskMapper: TaskMapper
) {

    private val log: Logger = LoggerFactory.getLogger(this::class.java)

    @Transactional
    fun createTask(newTaskDto : CreateTaskDto, userId : UUID) : TaskDto {
        log.info("CREATE_TASK title ={}, priority ={}", newTaskDto.title, newTaskDto.priority)
        val user = userRepository.getUserByUserId(userId)
        val task = TaskModel(title = newTaskDto.title, priority = newTaskDto.priority, user = user)
        return taskMapper.toDto(taskRepository.save(task))
    }

    fun getTasks(userId : UUID, sortBy : TaskSortField?) : List <TaskDto> {
        val tasks = if (sortBy != null) {
            val sort = Sort.by(Sort.Direction.ASC, sortBy.dbField)
            taskRepository.findByUserUserId(userId, sort)
        } else {
            taskRepository.findByUserUserId(userId)
        }
        return taskMapper.toDtoList(tasks)
    }

    @Transactional
    fun patchTask(taskId : UUID, newStatusDto : UpdateTaskStatusDto) : TaskDto {
        val taskModel = taskRepository.getTaskByTaskId(taskId)
        taskModel.status = newStatusDto.status
        return taskMapper.toDto(taskModel)
    }
    @Transactional
    fun deleteDoneTasks(userId : UUID) {
        log.info("DELETE_DONE_TASKS userId={}", userId)
        taskRepository.deleteByUserUserIdAndStatus(userId, TaskStatus.DONE)
    }
}