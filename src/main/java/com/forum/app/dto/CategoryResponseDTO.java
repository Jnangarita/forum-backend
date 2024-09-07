package com.forum.app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.time.LocalDateTime;

import com.forum.app.entity.Category;

@Data
@AllArgsConstructor
public class CategoryResponseDTO {
	private String categoryName;
	private String description;
	private Long id;
	private Integer numberQuestion;
	private LocalDateTime time;

	public CategoryResponseDTO(Category category) {
		this.id = category.getId();
		this.categoryName = category.getCategoryName();
		this.description = category.getDescription();
		this.time = category.getCreatedAt();
	}
}