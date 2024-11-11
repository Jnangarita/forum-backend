package com.forum.app.mapper;

import com.forum.app.dto.request.CreateUserInput;
import com.forum.app.dto.request.UpdateUserInput;
import com.forum.app.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(target = "numberResponses", constant = "0")
    @Mapping(target = "numberQuestions", constant = "0")
    @Mapping(target = "profileName", expression = "java(user.getFirstName() + \" \" + user.getLastName())")
    User convertDtoToEntity(CreateUserInput userInput);
    void updateUserFromDto(UpdateUserInput userInput, @MappingTarget User user);
}