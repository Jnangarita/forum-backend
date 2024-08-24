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

	@Column(name = "descripcion", nullable = false)
	private String description;

	@Column(name = "usuario_creador", nullable = false)
	private Long createdBy;

	@Column(name = "usuario_modificador")
	private Long modifiedBy;

	@Column(name = "fecha_creacion", updatable = false)
	private LocalDateTime createdDate;

	@Column(name = "fecha_modificacion", nullable = false)
	private LocalDateTime modifiedDate;

	@Column(name = "eliminado")
	private boolean deleted;

	@PrePersist
	protected void onCreate() {
		this.createdDate = LocalDateTime.now();
	}

	@PreUpdate
	protected void onUpdate() {
		this.modifiedDate = LocalDateTime.now();
	}
}