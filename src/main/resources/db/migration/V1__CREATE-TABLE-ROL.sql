CREATE TABLE rol (
	id INT AUTO_INCREMENT PRIMARY KEY,
    nombre_rol VARCHAR(20) UNIQUE NOT NULL
);

INSERT INTO rol (nombre_rol) VALUES
    ('Administrador'),
    ('Desarrollador'),
    ('Usuario');