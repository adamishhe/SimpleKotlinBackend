package org.example.mapper

import org.example.dto.TaskDto
import org.example.model.TaskModel
import org.example.model.UserModel
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.MappingTarget

@Mapper(componentModel = "spring")
interface TaskMapper {
    fun toDto(model: TaskModel): TaskDto

    fun toDtoList(models: List<TaskModel>): List<TaskDto>
}