package org.example.controller.advice

import org.example.dto.ApiError
import org.example.exception.EmailAlreadyExistsException
import org.example.exception.TaskNotFoundException
import org.example.exception.UserNotFoundException
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {

    private val log: Logger = LoggerFactory.getLogger(this::class.java)

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleValidationError(ex: MethodArgumentNotValidException): ResponseEntity<ApiError> {
        log.warn("Validation failed: {}", ex.message)
        log.debug("Validation details: {}", ex.bindingResult.fieldErrors)

        val details = ex.bindingResult.fieldErrors
            .associate { it.field to (it.defaultMessage ?: "Ошибка") }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
            ApiError(
                error = "VALIDATION_ERROR",
                message = "Ошибка валидации данных",
                details = details
            )
        )
    }

    @ExceptionHandler(UserNotFoundException::class)
    fun handleUserNotFound(ex: UserNotFoundException): ResponseEntity<ApiError> {
        log.info("User not found: {}", ex.message)

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
            ApiError(
                error = "USER_NOT_FOUND",
                message = ex.message!!
            )
        )
    }

    @ExceptionHandler(TaskNotFoundException::class)
    fun handleTaskNotFound(ex: TaskNotFoundException): ResponseEntity<ApiError> {
        log.info("Task not found: {}", ex.message)

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
            ApiError(
                error = "TASK_NOT_FOUND",
                message = ex.message!!
            )
        )
    }

    @ExceptionHandler(EmailAlreadyExistsException::class)
    fun handleEmailConflict(ex: EmailAlreadyExistsException): ResponseEntity<ApiError> {
        log.info("Email already exists: {}", ex.message)

        return ResponseEntity.status(HttpStatus.CONFLICT).body(
            ApiError(
                error = "EMAIL_ALREADY_EXISTS",
                message = ex.message!!
            )
        )
    }

    @ExceptionHandler(Exception::class)
    fun handleAllExceptions(ex: Exception): ResponseEntity<ApiError> {
        log.error("Unhandled exception occurred", ex)

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
            ApiError(
                error = "INTERNAL_ERROR",
                message = "Внутренняя ошибка сервера"
            )
        )
    }
}