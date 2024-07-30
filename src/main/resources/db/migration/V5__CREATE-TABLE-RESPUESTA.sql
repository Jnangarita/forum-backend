CREATE TABLE respuesta (
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_pregunta INT,
    respuesta TEXT,
    id_usuario INT,
    estado_respuesta VARCHAR(1),
    fecha_creacion TIMESTAMP,
    fecha_modificacion TIMESTAMP,
    eliminado BOOLEAN,
    CONSTRAINT fk_pregunta FOREIGN KEY (id_pregunta) REFERENCES pregunta(id),
    CONSTRAINT fk_usuario_respuesta FOREIGN KEY (id_usuario) REFERENCES usuario(id)
);