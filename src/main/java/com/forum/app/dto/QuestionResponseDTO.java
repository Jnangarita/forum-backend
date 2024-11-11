package com.forum.app.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.forum.app.dto.request.IdValueInput;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class QuestionResponseDTO {
	private Integer answers;
	private List<IdValueInput> categories;
	private String photo;
	private Integer questionId;
	private char questionStatus;
	private String questionTitle;
	private LocalDateTime creationDate;
	private String user;
	private Integer userId;
	private Integer views;
	private Integer votes;
}