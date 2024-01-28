CREATE TABLE usuario (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre_usuario VARCHAR(50) UNIQUE,
    correo_electronico VARCHAR(50) UNIQUE,
    contrasena VARCHAR(300),
    fecha_creacion TIMESTAMP,
    eliminado BOOLEAN
);