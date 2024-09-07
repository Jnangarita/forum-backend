package com.forum.app.entity.base;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@MappedSuperclass
public abstract class Audit {

	@Column(name = "fecha_creacion", nullable = false, updatable = false)
	private LocalDateTime createdAt;

	@Column(name = "fecha_modificacion")
	private LocalDateTime updatedAt;

	@Column(name = "eliminado", nullable = false)
	private boolean deleted;

	@PrePersist
	protected void onCreate() {
		this.createdAt = LocalDateTime.now();
		this.deleted = false;
	}

	@PreUpdate
	protected void onUpdate() {
		this.updatedAt = LocalDateTime.now();
	}
}