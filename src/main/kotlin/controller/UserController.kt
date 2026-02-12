package org.example.controller

import jakarta.validation.Valid
import org.example.dto.CreateTaskDto
import org.example.dto.CreateUserDto
import org.example.dto.TaskDto
import org.example.dto.TaskSortField
import org.example.dto.TaskStatus
import org.example.dto.UpdateTaskStatusDto
import org.example.dto.UserDto
import org.example.repository.TaskRepository
import org.example.service.TaskService
import org.example.service.UserService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
class UserController (
    private val userService: UserService,
    private val taskService: TaskService
) {
    @PostMapping("/users")
    @ResponseStatus(HttpStatus.CREATED)
    fun createUser(
        @Valid @RequestBody newUser : CreateUserDto
    ) = userService.createUser(newUser)

    @PostMapping("/users/{userId}/tasks")
    @ResponseStatus(HttpStatus.CREATED)
    fun createTask(
        @Valid @RequestBody newTask : CreateTaskDto,
                         @PathVariable userId : UUID
    ) = taskService.createTask(newTask, userId)

    @GetMapping("/users/{userId}/tasks")
    fun getTasks(
        @PathVariable userId: UUID,
        @RequestParam(required = false) sortBy: TaskSortField?
    ) = taskService.getTasks(userId, sortBy)

    @PatchMapping("/tasks/{taskId}/status")
    fun patchTask(
        @PathVariable taskId : UUID,
        @Valid @RequestBody newStatusDto : UpdateTaskStatusDto
    ) = taskService.patchTask(taskId, newStatusDto)


    @DeleteMapping("/users/{userId}/tasks/completed")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteDoneTasks(@PathVariable userId : UUID) {
        taskService.deleteDoneTasks(userId)
    }
}