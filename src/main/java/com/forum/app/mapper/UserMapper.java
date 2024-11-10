package com.forum.app.mapper;

import com.forum.app.dto.request.UserInput;
import com.forum.app.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User convertDtoToEntity(UserInput userInput);
}