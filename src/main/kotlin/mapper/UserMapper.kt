package org.example.mapper

import org.example.dto.UserDto
import org.example.model.UserModel
import org.mapstruct.Mapper

@Mapper(componentModel = "spring")
interface UserMapper {
    fun toDto(model: UserModel): UserDto

}