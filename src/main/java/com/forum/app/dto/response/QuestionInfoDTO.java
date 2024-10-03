package com.forum.app.dto.response;

import java.time.LocalDateTime;
import java.util.List;

import com.forum.app.dto.IdValueDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class QuestionInfoDTO {
	private List<IdValueDTO> category;
	private LocalDateTime createdAt;
	private boolean dislike;
	private Long id;
	private Integer like;
	private String photo;
	private String questionContent;
	private String questionTitle;
	private Integer reputation;
	private boolean saved;
	private LocalDateTime updatedAt;
	private String username;
	private Integer views;
}