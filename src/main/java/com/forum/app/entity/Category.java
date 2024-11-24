package com.forum.app.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

import com.forum.app.entity.base.Audit;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "categoria")
public class Category extends Audit {
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
	private Long updatedBy;
}