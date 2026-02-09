package org.example.service

import org.example.dto.CreateTaskDto
import org.example.dto.CreateUserDto
import org.example.dto.TaskDto
import org.example.dto.TaskStatus
import org.example.dto.UpdateTaskStatusDto
import org.example.dto.UserDto
import org.example.exception.exceptions.EmailAlreadyExistsException
import org.example.mapper.TaskMapper
import org.example.mapper.UserMapper
import org.example.model.TaskModel
import org.example.model.UserModel
import org.example.repository.TaskRepository
import org.example.repository.UserRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
@Transactional
class UserService (
    private val userRepository : UserRepository,
    private val taskRepository : TaskRepository,
    private val userMapper: UserMapper,
    private val taskMapper: TaskMapper
){
    private val log: Logger = LoggerFactory.getLogger(this::class.java)

    fun createUser(newUserDto : CreateUserDto) : UserDto {
        log.info("CREATE_USER email={}", newUserDto.email)

        if (userRepository.existsByEmail(newUserDto.email)) {
            throw EmailAlreadyExistsException("Пользователь с email ${newUserDto.email} уже существует")
        }

        val user = UserModel(email = newUserDto.email)
        return userMapper.toDto(userRepository.save(user))
    }

    fun createTask(newTaskDto : CreateTaskDto, userId : UUID) : TaskDto {
        log.info("CREATE_TASK title ={}, priority ={}", newTaskDto.title, newTaskDto.priority)
        val user = userRepository.getUserByUserId(userId)
        val task = TaskModel(title = newTaskDto.title, priority = newTaskDto.priority, user = user)
        return taskMapper.toDto(taskRepository.save(task))
    }

    fun getTasks(userId : UUID) : List <TaskDto> {
        val userModel = userRepository.getUserByUserId(userId)
        return taskMapper.toDtoList(userModel.tasks)
    }

    fun getTasksSortedByStatus(userId : UUID) : List <TaskDto> {
        val userModel = userRepository.getUserByUserId(userId)
        return taskMapper.toDtoList(userModel.tasks).sortedBy { task -> task.status }
    }

    fun getTasksSortedByPriority(userId : UUID) : List <TaskDto> {
        val userModel = userRepository.getUserByUserId(userId)
        return taskMapper.toDtoList(userModel.tasks).sortedBy { task -> task.priority }
    }

    fun getTasksSortedByCreatedAt(userId : UUID) : List <TaskDto> {
        val userModel = userRepository.getUserByUserId(userId)
        return taskMapper.toDtoList(userModel.tasks).sortedBy { task -> task.createdAt }
    }

    fun patchTask(taskId : UUID, newStatusDto : UpdateTaskStatusDto) : TaskDto {
        log.info("PATCH_TASK_STATUS status={}", newStatusDto.status)
        val taskModel = taskRepository.getTaskByTaskId(taskId).also { it.status = newStatusDto.status }
        taskRepository.save(taskModel)
        return taskMapper.toDto(taskModel)
    }

    fun deleteDoneTasks(userId : UUID) {
        log.info("DELETE_DONE_TASKS userId={}", userId)
        val userModel = userRepository.getUserByUserId(userId)
        userModel.tasks.removeIf { task -> task.status == TaskStatus.DONE }
        userRepository.save(userModel)
    }
}