package com.forum.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.userdetails.UserDetails;

import com.forum.app.entity.User;
import java.util.List;
import java.util.Map;

public interface UserRepository extends JpaRepository<User, Long> {
	UserDetails findByEmail(String email);

	List<User> findByDeletedFalse();

	@Query(value = "SELECT"
			+ " u.id,"
			+ " d.ruta_documento AS foto,"
			+ " u.id_pais,"
			+ " CONCAT(u.primer_nombre, ' ', u.apellido) AS nombre_usuario,"
			+ " 0 AS reputacion,"//TODO agregar la columna reputación de la tabla usuario
			+ " JSON_ARRAYAGG("
			+ "  JSON_OBJECT("
			+ "   'id', c.id,"
			+ "   'value', c.nombre_categoria)) AS lista_categoria "
			+ "FROM usuario u "
			+ "LEFT JOIN documento d ON u.id = d.id_usuario "
			+ "LEFT JOIN ("
			+ " SELECT DISTINCT"
			+ "  p.id_usuario,"
			+ "  p.id_categoria,"
			+ "  c.id,"
			+ "  c.nombre_categoria"
			+ " FROM pregunta p"
			+ " INNER JOIN categoria c ON p.id_categoria = c.id"
			+ " WHERE p.eliminado = FALSE) c ON u.id = c.id_usuario "
			+ "WHERE u.eliminado = FALSE "
			+ "GROUP BY u.id, d.ruta_documento, u.id_pais, u.primer_nombre, u.apellido;", nativeQuery = true)
	List<Map<String, Object>> userInfoList();

	@Query(value = "SELECT * FROM usuario u WHERE correo_electronico = :email", nativeQuery = true)
	User getUserByEmail(@Param("email")String email);
}