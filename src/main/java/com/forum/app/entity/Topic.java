package com.forum.app.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "pregunta")
public class Topic {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "id_categoria")
	private Long idCategory;

	@Column(name = "pregunta")
	private String question;

	@Column(name = "id_usuario")
	private Long idUser;

	@Column(name = "fecha_creacion")
	private LocalDateTime creationDate;

	@Column(name = "fecha_modificacion")
	private LocalDateTime modificationDate;

	@Column(name = "eliminado")
	private boolean deleted;
}