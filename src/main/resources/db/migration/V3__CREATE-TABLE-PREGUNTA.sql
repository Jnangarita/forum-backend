CREATE TABLE pregunta (
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_categoria INT,
    pregunta TEXT,
    id_usuario INT,
    estado_pregunta VARCHAR(1),
    fecha_creacion TIMESTAMP,
    fecha_modificacion TIMESTAMP,
    eliminado BOOLEAN,
    CONSTRAINT fk_categoria FOREIGN KEY (id_categoria) REFERENCES categoria(id),
    CONSTRAINT fk_usuario FOREIGN KEY (id_usuario) REFERENCES usuario(id)
);