package br.com.todoserver.todoapp.mappers;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import br.com.todoserver.todoapp.dtos.TaskDTO;
import br.com.todoserver.todoapp.dtos.UserDTO;
import br.com.todoserver.todoapp.entities.TaskEntity;
import br.com.todoserver.todoapp.entities.UserEntity;

@Mapper(componentModel = "spring")
public interface UpdateMapper {
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateUserFromDto(UserDTO dto, @MappingTarget UserEntity entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateTaskFromDto(TaskDTO dto, @MappingTarget TaskEntity entity);
}
