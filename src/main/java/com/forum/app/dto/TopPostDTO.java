package com.forum.app.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TopPostDTO {
	private LocalDateTime creationDate;
	private Long postId;
	private String post;
	private char postType;
	private char status;
	private Long userId;
}