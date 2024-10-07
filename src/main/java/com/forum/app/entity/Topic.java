package com.forum.app.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.forum.app.entity.base.Audit;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "pregunta")
public class Topic extends Audit {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "titulo_pregunta")
	private String titleQuestion;

	@Column(name = "id_categoria")
	private Long categoryId;

	@Column(name = "pregunta")
	private String question;

	@Column(name = "id_usuario")
	private Long userId;

	@Column(name = "estado_pregunta")
	private char questionStatus;

	@Column(name = "vistas")
	private Integer views;

	@Column(name = "votos")
	private Integer votes;

	@Column(name = "me_gusta")
	private Integer likes;

	@Column(name = "no_me_gusta")
	private boolean dislikes;

	@Column(name = "guardado")
	private boolean saved;
}