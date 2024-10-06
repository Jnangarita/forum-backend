package com.forum.app.enumeration;

public enum DbColumns {
	CATEGORIES("lista_categoria"),
	CREATION_DATE("fecha_creacion"),
	ID("id"),
	MODIFICATION_DATE("fecha_modificacion"),
	PHOTO("foto"),
	TITLE_QUESTION("titulo_pregunta"),
	USER_NAME("nombre_usuario"),
	VIEWS("vistas");

	private String columns;

	DbColumns(String columns) {
		this.columns = columns;
	}

	public String getColumns() {
		return this.columns;
	}
}