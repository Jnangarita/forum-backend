package com.forum.app.mapper;

import com.forum.app.dto.TopPostDTO;
import com.forum.app.entity.Answer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AnswerMapper {
    @Mapping(target = "postType", constant = "'A'")
    @Mapping(target = "postId", source = "answer.id")
    @Mapping(target = "post", source = "answer.answerTxt")
    @Mapping(target = "status", source = "answer.answerStatus")
    TopPostDTO convertAnswerEntityToTopPost(Answer answer);
}