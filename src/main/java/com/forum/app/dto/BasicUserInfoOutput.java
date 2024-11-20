package com.forum.app.dto;

import com.forum.app.entity.Category;
import com.forum.app.entity.City;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class BasicUserInfoOutput {
	private Long id;
	private String photo;
	private City city;
	private String profileName;
	private Integer reputation;
	private List<Category> category;
}