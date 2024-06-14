ALTER TABLE categoria ADD COLUMN usuario_creador INT,
ADD COLUMN usuario_modificador INT,
ADD COLUMN fecha_creacion TIMESTAMP,
ADD COLUMN fecha_modificacion TIMESTAMP,
ADD COLUMN eliminado BOOLEAN,
ADD CONSTRAINT fk_usuario_creador FOREIGN KEY (usuario_creador) REFERENCES usuario(id),
ADD CONSTRAINT fk_usuario_modificador FOREIGN KEY (usuario_modificador) REFERENCES usuario(id);