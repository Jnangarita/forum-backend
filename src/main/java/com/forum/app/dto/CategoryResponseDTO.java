package com.forum.app.dto;

import lombok.Data;
import java.time.LocalDateTime;

import com.forum.app.entity.Category;

@Data
public class CategoryResponseDTO {
	private Long id;
	private String categoryName;
	private LocalDateTime createdDate;

	public CategoryResponseDTO(Category category) {
		this.id = category.getId();
		this.categoryName = category.getCategoryName();
		this.createdDate = category.getCreatedDate();
	}
}