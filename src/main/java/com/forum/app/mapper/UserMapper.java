package com.forum.app.mapper;

import com.forum.app.dto.request.CreateUserInput;
import com.forum.app.dto.request.UpdateUserInput;
import com.forum.app.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User convertDtoToEntity(CreateUserInput userInput);
    void updateUserFromDto(UpdateUserInput userInput, @MappingTarget User user);
}