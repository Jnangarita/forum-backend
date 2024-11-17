package com.forum.app.mapper;

import com.forum.app.dto.request.SaveUserInput;
import com.forum.app.dto.request.UpdateUserInput;
import com.forum.app.entity.User;
import com.forum.app.mapper.helper.UserMapperHelper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = {UserMapperHelper.class})
public interface UserMapper {
    @Mapping(target = "numberResponses", constant = "0")
    @Mapping(target = "numberQuestions", constant = "0")
    @Mapping(target = "profileName", expression = "java(user.getFirstName() + \" \" + user.getLastName())")
    @Mapping(target = "role", source = "roleId", qualifiedByName = "idToRole")
    User convertDtoToEntity(SaveUserInput userInput);
    void updateUserFromDto(UpdateUserInput userInput, @MappingTarget User user);
}