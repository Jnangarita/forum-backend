CREATE TABLE pregunta (
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_categoria INT NOT NULL,
    titulo_pregunta TEXT NOT NULL,
    pregunta TEXT NOT NULL,
    id_usuario INT NOT NULL,
    estado_pregunta VARCHAR(1) NOT NULL,
    vistas INT,
    votos INT,
    fecha_creacion TIMESTAMP NOT NULL,
    fecha_modificacion TIMESTAMP,
    eliminado BOOLEAN NOT NULL,
    CONSTRAINT fk_categoria FOREIGN KEY (id_categoria) REFERENCES categoria(id),
    CONSTRAINT fk_usuario_pregunta FOREIGN KEY (id_usuario) REFERENCES usuario(id)
);