package com.forum.app.dto.request;

import javax.validation.constraints.NotNull;

import com.forum.app.dto.request.base.AnswerBaseInput;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SaveAnswerInput extends AnswerBaseInput {
	@NotNull
    private Long questionId;

	@NotNull
    private Long userId;
}