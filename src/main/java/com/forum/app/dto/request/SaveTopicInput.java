package com.forum.app.dto.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.forum.app.dto.request.base.TopicBaseInput;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SaveTopicInput extends TopicBaseInput {
	@NotBlank
	private String titleQuestion;

	@NotNull
	private Long userId;
}