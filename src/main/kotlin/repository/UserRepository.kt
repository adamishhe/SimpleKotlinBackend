package org.example.repository

import org.example.exception.exceptions.UserNotFoundException
import org.example.model.UserModel
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface UserRepository : JpaRepository<UserModel, UUID> {
    fun getUserByUserId(userId : UUID) : UserModel = findById(userId).orElseThrow {
        UserNotFoundException("Пользователь с user_id $userId не найден")
    }

    fun existsByEmail(email: String): Boolean
}