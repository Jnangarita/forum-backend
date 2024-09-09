package com.forum.app.dto.response;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class ResponseDTO {
	private LocalDateTime createdAt;
	private boolean deleted;
	private Long id;
	private String message;
	private LocalDateTime updatedAt;

	protected ResponseDTO(LocalDateTime createdAt, boolean deleted, Long id, String message, LocalDateTime updatedAt) {
		this.createdAt = createdAt;
		this.deleted = deleted;
		this.id = id;
		this.message = message;
		this.updatedAt = updatedAt == null ? createdAt : updatedAt;
	}
}