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
			+ " u.codigo,"
			+ " u.pais,"
			+ " u.ciudad,"
			+ " u.correo_electronico,"
			+ " u.id,"
			+ " u.numero_preguntas,"
			+ " u.numero_respuestas,"
			+ " u.foto,"
			+ " u.eliminado,"
			+ " u.primer_nombre,"
			+ " u.apellido,"
			+ " CONCAT(u.primer_nombre, ' ', u.apellido) AS nombre_usuario,"
			+ " r.id AS id_rol,"
			+ " r.nombre_rol "
			+ "FROM usuario u "
			+ "INNER JOIN rol r ON u.id_rol = r.id "
			+ "WHERE u.id = :id", nativeQuery = true)
	Map<String, Object> findUserInformationById(@Param("id") Long id);

	@Query(value = "SELECT"
			+ " u.id,"
			+ " u.foto,"
			+ " u.pais,"
			+ " CONCAT(u.primer_nombre, ' ', u.apellido) AS nombre_usuario,"
			+ " 0 AS reputacion,"
			+ " JSON_ARRAYAGG("
			+ "  JSON_OBJECT("
			+ "   'id', c.id,"
			+ "   'value', c.nombre_categoria)) AS categorias "
			+ "FROM usuario u "
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
			+ "GROUP BY u.id, u.foto, u.pais, u.primer_nombre, u.apellido;", nativeQuery = true)
	List<Map<String, Object>> userInfoList();
}