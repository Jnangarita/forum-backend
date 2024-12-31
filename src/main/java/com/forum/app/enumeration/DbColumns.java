package com.forum.app.enumeration;

public enum DbColumns {
	CATEGORY_NAME("nombre_categoria"),
	DATE("fecha"),
	DESCRIPTION("descripcion"),
	ID("id"),
	QUESTION_NUMBER("numero_preguntas");

	private String columns;

	DbColumns(String columns) {
		this.columns = columns;
	}

	public String getColumns() {
		return this.columns;
	}
}