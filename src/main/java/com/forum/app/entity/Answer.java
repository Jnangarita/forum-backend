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
@Table(name = "respuesta")
public class Answer extends Audit {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "id_pregunta")
	private Long questionId;

	@Column(name = "respuesta")
	private String answerTxt;

	@Column(name = "id_usuario")
	private Long userId;

	@Column(name = "estado_respuesta")
	private char answerStatus;
}