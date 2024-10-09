package com.forum.app.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PopularQuestionDTO {
	private Long id;
	private String photo;
	private String questionTitle;
	private String userName;
}