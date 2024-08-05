CREATE TABLE usuario (
    id INT AUTO_INCREMENT PRIMARY KEY,
	codigo VARCHAR(10) UNIQUE NOT NULL,
    primer_nombre VARCHAR(50) NOT NULL,
    apellido VARCHAR(50) NOT NULL,
    correo_electronico VARCHAR(50) UNIQUE NOT NULL,
    contrasena VARCHAR(300) NOT NULL,
    pais VARCHAR(3) NULL,
    numero_preguntas INT NULL,
    numero_respuestas INT NULL,
    foto VARCHAR(100) NULL,
    id_rol INT NOT NULL,
    fecha_creacion TIMESTAMP,
    fecha_modificacion TIMESTAMP,
    eliminado BOOLEAN,
	CONSTRAINT fk_id_rol FOREIGN KEY (id_rol) REFERENCES rol(id)
);