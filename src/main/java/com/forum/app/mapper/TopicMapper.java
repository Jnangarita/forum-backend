package com.forum.app.mapper;

import com.forum.app.dto.TopPostDTO;
import com.forum.app.entity.Topic;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TopicMapper {
    @Mapping(target = "postType", constant = "'Q'")
    @Mapping(target = "postId", source = "topic.id")
    @Mapping(target = "post", source = "topic.question")
    @Mapping(target = "status", source = "topic.questionStatus")
    TopPostDTO convertTopicEntityToTopPost(Topic topic);
}