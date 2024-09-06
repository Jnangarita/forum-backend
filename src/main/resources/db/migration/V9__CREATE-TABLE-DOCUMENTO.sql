CREATE TABLE documento (
    id_documento INT AUTO_INCREMENT PRIMARY KEY,
    id_usuario INT NOT NULL,
    tipo_documento VARCHAR(4) NOT NULL,
	nombre_documento VARCHAR(50) NOT NULL,
	ruta_documento VARCHAR(255) NOT NULL,
    fecha_creacion TIMESTAMP,
    CONSTRAINT fk_codigo_usuario FOREIGN KEY (id_usuario) REFERENCES usuario(id)
);