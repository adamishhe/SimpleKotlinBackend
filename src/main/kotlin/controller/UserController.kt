package org.example.controller

import org.example.dto.TaskDto
import org.example.dto.TaskStatus
import org.example.dto.UserDto
import org.example.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.security.SecurityProperties
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
class UserContorller (
    val userService: UserService
) {
    @PostMapping("/users")
    fun createUser(user : UserDto) : UserDto {
        return userService.createUser(user)
    }

    @PostMapping("/users/{id}/tasks")
    fun createTask(task : TaskDto, @PathVariable userId : UUID) : TaskDto {
        return userService.createTask(task, userId)
    }

    @GetMapping("/users/{userId}/tasks")
    fun getTasks(userId : UUID ) : List <TaskDto> {
        return userService.getTasks(userId)
    }

    @GetMapping("/users/{userId}/tasks")
    fun getTasksSortedByStatus(userId : UUID ) : List <TaskDto> {
        return userService.getTasksSortedByStatus(userId)
    }

    @GetMapping("/users/{userId}/tasks")
    fun getTasksSortedByPriority(userId : UUID ) : List <TaskDto> {
        return userService.getTasksSortedByPriority(userId)
    }

    @GetMapping("/users/{userId}/tasks")
    fun getTasksSortedByCreatedAt(userId : UUID ) : List <TaskDto> {
        return userService.getTasksSortedByCreatedAt(userId)
    }

    @PatchMapping("/tasks/{taskId}/status")
    fun patchTask(taskId : UUID, status : TaskStatus ) : TaskDto {
        return userService.patchTask(taskId, status)
    }

    @DeleteMapping("/tasks/{userId}/tasks/completed")
    fun deleteDoneTasks(userId : UUID) {
        userService.deleteDoneTasks(userId)
    }
}