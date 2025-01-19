package com.forum.app.mapper;

import com.forum.app.dto.BasicUserInfoOutput;
import com.forum.app.dto.UserOutput;
import com.forum.app.dto.request.UserInput;
import com.forum.app.entity.User;
import com.forum.app.mapper.helper.UserMapperHelper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = {UserMapperHelper.class})
public interface UserMapper {
    @Mapping(target = "numberResponses", constant = "0")
    @Mapping(target = "numberQuestions", constant = "0")
    @Mapping(target = "reputation", constant = "0")
    @Mapping(target = "profileName", expression = "java(user.getFirstName() + \" \" + user.getLastName())")
    @Mapping(target = "role", constant = "3", qualifiedByName = "idToRole") // TODO: Pasar una constante en lugar del 3
    @Mapping(target = "password", source = "password", qualifiedByName = "encodePassword")
    @Mapping(target = "city", constant = "106", qualifiedByName = "idToCity") // TODO: Pasar una constante en lugar del 106
    @Mapping(target = "country", constant = "22", qualifiedByName = "idToCountry") // TODO: Pasar una constante en lugar del 22
    User convertDtoToEntity(UserInput userInput);

    @Mapping(target = "city", source = "cityId", qualifiedByName = "idToCity")
    @Mapping(target = "country", source = "countryId", qualifiedByName = "idToCountry")
    @Mapping(target = "password", ignore = true)
    void updateUserFromDto(UserInput userInput, @MappingTarget User user);

    UserOutput toUserOutput(User user);

    @Mapping(target = "photo", source = "user", qualifiedByName = "userPhoto")
    BasicUserInfoOutput entityToBasicUserInfo(User user);
}