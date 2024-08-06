package com.forum.app.dto;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class QuestionResponseDTO {
	private Integer answers;
	private List<IdValueDTO> categories;
	private String photo;
	private Integer questionId;
	private String questionStatus;
	private String questionTitle;
	private LocalDateTime creationDate;
	private String user;
	private Integer userId;
	private Integer views;
	private Integer votes;
}