package com.forum.app.mapper;

import com.forum.app.dto.request.SaveUserInput;
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
    User convertDtoToEntity(SaveUserInput userInput);
    void updateUserFromDto(UpdateUserInput userInput, @MappingTarget User user);
}