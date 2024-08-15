CREATE TABLE ciudades (
    id INT PRIMARY KEY,
    nombre_ciudad VARCHAR(100) NOT NULL,
    pais_id INT,
    FOREIGN KEY (pais_id) REFERENCES paises(id)
);

INSERT INTO ciudades (id, nombre_ciudad, pais_id) VALUES
	-- Argentina
	(1, 'Buenos Aires', 1),
	(2, 'Córdoba', 1),
	(3, 'Rosario', 1),
	(4, 'Mendoza', 1),
	(5, 'La Plata', 1),
	
	-- Bolivia
	(6, 'La Paz', 2),
	(7, 'Santa Cruz de la Sierra', 2),
	(8, 'Cochabamba', 2),
	(9, 'Sucre', 2),
	(10, 'Oruro', 2),
	
	-- Chile
	(11, 'Santiago', 3),
	(12, 'Valparaíso', 3),
	(13, 'Concepción', 3),
	(14, 'La Serena', 3),
	(15, 'Antofagasta', 3),
	
	-- Colombia
	(16, 'Bogotá', 4),
	(17, 'Medellín', 4),
	(18, 'Cali', 4),
	(19, 'Barranquilla', 4),
	(20, 'Cartagena', 4),
	
	-- Costa Rica
	(21, 'San José', 5),
	(22, 'Alajuela', 5),
	(23, 'Cartago', 5),
	(24, 'Heredia', 5),
	(25, 'Liberia', 5),
	
	-- Cuba
	(26, 'La Habana', 6),
	(27, 'Santiago de Cuba', 6),
	(28, 'Holguín', 6),
	(29, 'Camagüey', 6),
	(30, 'Santa Clara', 6),
	
	-- República Dominicana
	(31, 'Santo Domingo', 7),
	(32, 'Santiago de los Caballeros', 7),
	(33, 'La Romana', 7),
	(34, 'San Pedro de Macorís', 7),
	(35, 'Puerto Plata', 7),
	
	-- Ecuador
	(36, 'Quito', 8),
	(37, 'Guayaquil', 8),
	(38, 'Cuenca', 8),
	(39, 'Santo Domingo', 8),
	(40, 'Machala', 8),
	
	-- El Salvador
	(41, 'San Salvador', 9),
	(42, 'Santa Ana', 9),
	(43, 'San Miguel', 9),
	(44, 'Mejicanos', 9),
	(45, 'Apopa', 9),
	
	-- Guinea Ecuatorial
	(46, 'Malabo', 10),
	(47, 'Bata', 10),
	(48, 'Ebebiyin', 10),
	(49, 'Aconibe', 10),
	(50, 'Luba', 10),
	
	-- Guatemala
	(51, 'Ciudad de Guatemala', 11),
	(52, 'Mixco', 11),
	(53, 'Villa Nueva', 11),
	(54, 'Quetzaltenango', 11),
	(55, 'Escuintla', 11),
	
	-- Honduras
	(56, 'Tegucigalpa', 12),
	(57, 'San Pedro Sula', 12),
	(58, 'Choloma', 12),
	(59, 'La Ceiba', 12),
	(60, 'El Progreso', 12),
	
	-- México
	(61, 'Ciudad de México', 13),
	(62, 'Guadalajara', 13),
	(63, 'Monterrey', 13),
	(64, 'Puebla', 13),
	(65, 'Tijuana', 13),
	
	-- Nicaragua
	(66, 'Managua', 14),
	(67, 'León', 14),
	(68, 'Masaya', 14),
	(69, 'Chinandega', 14),
	(70, 'Matagalpa', 14),
	
	-- Panamá
	(71, 'Ciudad de Panamá', 15),
	(72, 'San Miguelito', 15),
	(73, 'Colón', 15),
	(74, 'David', 15),
	(75, 'La Chorrera', 15),
	
	-- Paraguay
	(76, 'Asunción', 16),
	(77, 'Ciudad del Este', 16),
	(78, 'San Lorenzo', 16),
	(79, 'Lambaré', 16),
	(80, 'Fernando de la Mora', 16),
	
	-- Perú
	(81, 'Lima', 17),
	(82, 'Arequipa', 17),
	(83, 'Trujillo', 17),
	(84, 'Chiclayo', 17),
	(85, 'Piura', 17),
	
	-- España
	(86, 'Madrid', 18),
	(87, 'Barcelona', 18),
	(88, 'Valencia', 18),
	(89, 'Sevilla', 18),
	(90, 'Zaragoza', 18),
	
	-- Uruguay
	(91, 'Montevideo', 19),
	(92, 'Salto', 19),
	(93, 'Paysandú', 19),
	(94, 'Las Piedras', 19),
	(95, 'Rivera', 19),
	
	-- Venezuela
	(96, 'Caracas', 20),
	(97, 'Maracaibo', 20),
	(98, 'Valencia', 20),
	(99, 'Barquisimeto', 20),
	(100, 'Maracay', 20),
	
	-- Puerto Rico
	(101, 'San Juan', 21),
	(102, 'Bayamón', 21),
	(103, 'Carolina', 21),
	(104, 'Ponce', 21),
	(105, 'Caguas', 21);