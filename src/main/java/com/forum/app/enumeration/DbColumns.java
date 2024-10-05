package com.forum.app.enumeration;

public enum DbColumns {
	TITLE_QUESTION("titulo_pregunta"),
	USER_NAME("nombre_usuario"),
	CREATION_DATE("fecha_creacion"),
	PHOTO("foto"),
	VIEWS("vistas");

	private String columns;

	DbColumns(String columns) {
		this.columns = columns;
	}

	public String getColumns() {
		return this.columns;
	}
}