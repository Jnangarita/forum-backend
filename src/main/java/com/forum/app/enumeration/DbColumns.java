package com.forum.app.enumeration;

public enum DbColumns {
	ANSWER_NUMBER("numero_respuestas"),
	ANSWER_STATUS("estado_respuesta"),
	ANSWER("respuesta"),
	CATEGORIES("lista_categoria"),
	CATEGORY_NAME("nombre_categoria"),
	CITY("ciudad"),
	CODE("codigo"),
	COUNTRY("pais"),
	CREATION_DATE("fecha_creacion"),
	DATE("fecha"),
	DELETED("eliminado"),
	DESCRIPTION("descripcion"),
	DISLIKE("no_me_gusta"),
	EMAIL("correo_electronico"),
	FIRST_NAME("primer_nombre"),
	ID("id"),
	LAST_NAME("apellido"),
	LIKE("me_gusta"),
	MODIFICATION_DATE("fecha_modificacion"),
	PHOTO("foto"),
	QUESTION_ID("id_pregunta"),
	QUESTION_NUMBER("numero_preguntas"),
	QUESTION_STATUS("estado_pregunta"),
	QUESTION("pregunta"),
	REPUTATION("reputacion"),
	ROLE_ID("id_rol"),
	ROLE_NAME("nombre_rol"),
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