package com.forum.app.dto.response;

import java.time.LocalDateTime;
import java.util.List;

import com.forum.app.dto.IdValueDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class QuestionInfoDTO {
	private List<IdValueDTO> categories;
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
	private String userName;
	private Integer views;
}