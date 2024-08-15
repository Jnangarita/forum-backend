CREATE TABLE categoria (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre_categoria VARCHAR(50) NOT NULL,
    descripcion TEXT NOT NULL,
    usuario_creador INT NOT NULL,
    usuario_modificador INT,
    fecha_creacion TIMESTAMP NOT NULL,
    fecha_modificacion TIMESTAMP,
    eliminado BOOLEAN,
    CONSTRAINT fk_usuario_creador FOREIGN KEY (usuario_creador) REFERENCES usuario(id),
    CONSTRAINT fk_usuario_modificador FOREIGN KEY (usuario_modificador) REFERENCES usuario(id)
);