package com.forum.app.dto.request.base;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public abstract class AnswerBaseInput {
    @NotBlank
    private String answerTxt;
}
