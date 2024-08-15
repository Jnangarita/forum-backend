CREATE TABLE documento (
    id_documento INT AUTO_INCREMENT PRIMARY KEY,
    codigo_usuario VARCHAR(10) NOT NULL,
    tipo_documento VARCHAR(50),
	nombre_documento VARCHAR(50) NOT NULL,
	ruta_documento VARCHAR(255) NOT NULL,
    fecha_creacion TIMESTAMP,
    fecha_modificacion TIMESTAMP,
    eliminado BOOLEAN,
    CONSTRAINT fk_codigo_usuario FOREIGN KEY (codigo_usuario) REFERENCES usuario(codigo)
);