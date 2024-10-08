package com.forum.app.enumeration;

public enum DbColumns {
	ANSWER("respuesta"),
	ANSWER_STATUS("estado_respuesta"),
	CATEGORIES("lista_categoria"),
	CATEGORY_NAME("nombre_categoria"),
	COUNTRY("pais"),
	CREATION_DATE("fecha_creacion"),
	DATE("fecha"),
	DESCRIPTION("descripcion"),
	DISLIKE("no_me_gusta"),
	ID("id"),
	LIKE("me_gusta"),
	MODIFICATION_DATE("fecha_modificacion"),
	PHOTO("foto"),
	QUESTION("pregunta"),
	QUESTION_ID("id_pregunta"),
	QUESTION_NUMBER("numero_preguntas"),
	QUESTION_STATUS("estado_pregunta"),
	REPUTATION("reputacion"),
	SAVED("guardado"),
	TITLE_QUESTION("titulo_pregunta"),
	TYPE("tipo"),
	USER_ID("id_usuario"),
	USER_NAME("nombre_usuario"),
	VIEWS("vistas"),
	VOTES("votos");

	private String columns;

	DbColumns(String columns) {
		this.columns = columns;
	}

	public String getColumns() {
		return this.columns;
	}
}