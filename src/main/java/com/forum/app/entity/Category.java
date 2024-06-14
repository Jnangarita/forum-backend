package com.forum.app.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Entity
@Table(name = "categoria")
public class Category {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "nombre_categoria", nullable = false)
	private String categoryName;

	@Column(name = "usuario_creador")
	private Long createdBy;

	@Column(name = "usuario_modificador")
	private Long modifiedBy;

	@Column(name = "fecha_creacion", updatable = false)
	private LocalDateTime createdDate;

	@Column(name = "fecha_modificacion")
	private LocalDateTime modifiedDate;

	@Column(name = "eliminado")
	private Boolean deleted;

	@PrePersist
	protected void onCreate() {
		this.createdDate = LocalDateTime.now();
	}

	@PreUpdate
	protected void onUpdate() {
		this.modifiedDate = LocalDateTime.now();
	}
}