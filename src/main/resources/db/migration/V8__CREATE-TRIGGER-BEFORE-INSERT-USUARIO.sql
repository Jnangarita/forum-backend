CREATE TRIGGER before_insert_usuario
BEFORE INSERT ON usuario
FOR EACH ROW
BEGIN
    DECLARE code INT;
    SET code = (SELECT AUTO_INCREMENT FROM information_schema.TABLES WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = 'usuario');
    SET NEW.codigo = CONCAT('F', LPAD(code, 9, '0'));
END;