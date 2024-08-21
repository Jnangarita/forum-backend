CREATE TABLE paises (
    id INT PRIMARY KEY,
    nombre_pais VARCHAR(50) NOT NULL,
    codigo_pais VARCHAR(3) NOT NULL
);

INSERT INTO paises (id, nombre_pais, codigo_pais) VALUES
	(1, 'Argentina', 'ARG'),
	(2, 'Bolivia', 'BOL'),
	(3, 'Chile', 'CHL'),
	(4, 'Colombia', 'COL'),
	(5, 'Costa Rica', 'CRI'),
	(6, 'Cuba', 'CUB'),
	(7, 'República Dominicana', 'DOM'),
	(8, 'Ecuador', 'ECU'),
	(9, 'El Salvador', 'SLV'),
	(10, 'Guinea Ecuatorial', 'GNQ'),
	(11, 'Guatemala', 'GTM'),
	(12, 'Honduras', 'HND'),
	(13, 'México', 'MEX'),
	(14, 'Nicaragua', 'NIC'),
	(15, 'Panamá', 'PAN'),
	(16, 'Paraguay', 'PRY'),
	(17, 'Perú', 'PER'),
	(18, 'España', 'ESP'),
	(19, 'Uruguay', 'URY'),
	(20, 'Venezuela', 'VEN'),
	(21, 'Puerto Rico', 'PRI');