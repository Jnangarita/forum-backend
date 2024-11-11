package com.forum.app.dto.request.base;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public abstract class TopicBaseInput {
    @NotNull
    Long categoryId;

    @NotBlank
    String question;
}