package org.example.service

import org.example.dto.CreateUserDto
import org.example.dto.UserDto
import org.example.exception.EmailAlreadyExistsException
import org.example.mapper.UserMapper
import org.example.model.UserModel
import org.example.repository.UserRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Isolation
import org.springframework.transaction.annotation.Transactional

@Service
class UserService (
    private val userRepository : UserRepository,
    private val userMapper: UserMapper
){
    private val log: Logger = LoggerFactory.getLogger(this::class.java)

    @Transactional(isolation = Isolation.SERIALIZABLE)
    fun createUser(newUserDto : CreateUserDto) : UserDto {
        log.info("CREATE_USER email={}", newUserDto.email)

        if (userRepository.existsByEmail(newUserDto.email)) {
            throw EmailAlreadyExistsException("Пользователь с email ${newUserDto.email} уже существует")
        }

        val user = UserModel(email = newUserDto.email)
        return userMapper.toDto(userRepository.save(user))
    }
}