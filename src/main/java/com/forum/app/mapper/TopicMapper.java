package com.forum.app.mapper;

import com.forum.app.dto.PopularQuestionDTO;
import com.forum.app.dto.QuestionOutput;
import com.forum.app.dto.TopPostDTO;
import com.forum.app.dto.request.TopicInput;
import com.forum.app.dto.response.QuestionInfoDTO;
import com.forum.app.entity.Topic;
import com.forum.app.enumeration.QuestionStatus;
import com.forum.app.mapper.helper.UtilMapperHelper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.Map;

@Mapper(componentModel = "spring", uses = {UtilMapperHelper.class})
public interface TopicMapper {
    char QUESTION_STATUS = QuestionStatus.UNANSWERED.getStatus();

    @Mapping(target = "postType", constant = "'Q'")
    @Mapping(target = "postId", source = "topic.id")
    @Mapping(target = "post", source = "topic.question")
    @Mapping(target = "status", source = "topic.questionStatus")
    TopPostDTO convertTopicEntityToTopPost(Topic topic);

    @Mapping(target = "questionStatus", expression = "java(QUESTION_STATUS)")
    @Mapping(target = "views", constant = "0")
    @Mapping(target = "votes", constant = "0")
    @Mapping(target = "likes", constant = "0")
    @Mapping(target = "dislikes", expression = "java(false)")
    @Mapping(target = "saved", expression = "java(false)")
    Topic topicInputToEntity(TopicInput payload);

    @Mapping(target = "createdAt", source = "fecha_creacion", qualifiedByName = "mapToDate")
    @Mapping(target = "categories", source = "lista_categoria", qualifiedByName = "mapToIdValueInputList")
    @Mapping(target = "dislike", source = "no_me_gusta", qualifiedByName = "mapToBoolean")
    @Mapping(target = "id", source = "id", qualifiedByName = "mapToLong")
    @Mapping(target = "views", source = "vistas", qualifiedByName = "mapToInteger")
    @Mapping(target = "like", source = "me_gusta", qualifiedByName = "mapToInteger")
    @Mapping(target = "userName", source = "nombre_usuario", qualifiedByName = "mapToString")
    @Mapping(target = "photo", source = "foto", qualifiedByName = "mapToString")
    @Mapping(target = "questionContent", source = "pregunta", qualifiedByName = "mapToString")
    @Mapping(target = "questionTitle", source = "titulo_pregunta", qualifiedByName = "mapToString")
    @Mapping(target = "reputation", source = "reputacion", qualifiedByName = "mapToInteger")
    @Mapping(target = "saved", source = "guardado", qualifiedByName = "mapToBoolean")
    @Mapping(target = "updatedAt", source = "fecha_modificacion", qualifiedByName = "mapToDate")
    QuestionInfoDTO mapToDto(Map<String, Object> question);

    @Mapping(target = "titleQuestion", ignore = true)
    @Mapping(target = "userId", ignore = true)
    Topic topicInputToEntityUpdate(TopicInput payload, @MappingTarget Topic topic);

    @Mapping(target = "answers", source = "respuesta", qualifiedByName = "mapToString")
    @Mapping(target = "questionId", source = "id_pregunta", qualifiedByName = "mapToInteger")
    @Mapping(target = "questionTitle", source = "titulo_pregunta", qualifiedByName = "mapToString")
    @Mapping(target = "questionStatus", source = "estado_pregunta", qualifiedByName = "mapToString")
    @Mapping(target = "creationDate", source = "fecha_creacion", qualifiedByName = "mapToDate")
    @Mapping(target = "user", source = "nombre_usuario", qualifiedByName = "mapToString")
    @Mapping(target = "userId", source = "id_usuario", qualifiedByName = "mapToInteger")
    @Mapping(target = "photo", source = "foto", qualifiedByName = "mapToString")
    @Mapping(target = "views", source = "vistas", qualifiedByName = "mapToInteger")
    @Mapping(target = "votes", source = "votos", qualifiedByName = "mapToInteger")
    @Mapping(target = "categories", source = "lista_categoria", qualifiedByName = "mapToIdValueInputList")
    QuestionOutput mapToQuestionOutput(Map<String, Object> topic);

    @Mapping(target = "id", source = "id", qualifiedByName = "mapToLong")
    @Mapping(target = "photo", source = "foto", qualifiedByName = "mapToString")
    @Mapping(target = "questionTitle", source = "titulo_pregunta", qualifiedByName = "mapToString")
    @Mapping(target = "userName", source = "nombre_usuario", qualifiedByName = "mapToString")
    PopularQuestionDTO mapToPopularQuestionDTO(Map<String, Object> topic);
}