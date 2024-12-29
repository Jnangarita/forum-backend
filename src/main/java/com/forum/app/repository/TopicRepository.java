package com.forum.app.repository;

import com.forum.app.entity.Topic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;

public interface TopicRepository extends JpaRepository<Topic, Long> {
	List<Topic> findByDeletedFalseAndUserId(Long id);

    @Query(value = "SELECT COUNT(*) FROM pregunta;", nativeQuery = true)
    Integer getNumberQuestion();

    @Query(value = "SELECT"
    		+ " pre.id,"
    		+ " doc.ruta_documento AS foto,"
    		+ " pre.titulo_pregunta,"
    		+ " CONCAT(usr.primer_nombre, ' ', usr.apellido) AS nombre_usuario "
    		+ "FROM pregunta pre "
    		+ "INNER JOIN usuario usr ON pre.id_usuario = usr.id "
    		+ "LEFT JOIN documento doc ON usr.id = doc.id_usuario "
    		+ "WHERE pre.eliminado = FALSE AND pre.votos >= 20;", nativeQuery = true)
    List<Map<String, Object>> getPopularQuestion();

    @Query(value = "SELECT"
    		+ " JSON_ARRAYAGG("
    		+ "  JSON_OBJECT("
    		+ "   'id', cat.id,"
    		+ "   'value', cat.nombre_categoria)) AS lista_categoria,"
    		+ " pre.fecha_creacion,"
    		+ " pre.no_me_gusta,"
    		+ " pre.id,"
    		+ " pre.me_gusta,"
    		+ " pre.fecha_modificacion,"
    		+ " doc.ruta_documento AS foto,"
    		+ " pre.pregunta,"
    		+ " pre.titulo_pregunta,"
    		+ " usr.reputacion,"
    		+ " pre.guardado,"
    		+ " usr.nombre_usuario,"
    		+ " pre.vistas "
    		+ "FROM pregunta pre "
    		+ "INNER JOIN usuario usr ON usr.id = pre.id_usuario "
    		+ "LEFT JOIN categoria cat ON cat.id = pre.id_categoria "
    		+ "LEFT JOIN documento doc ON doc.id_usuario = usr.id "
    		+ "WHERE pre.id = :id "
    		+ "GROUP BY"
    		+ " pre.fecha_creacion,"
    		+ " pre.no_me_gusta,"
    		+ " pre.id,"
    		+ " pre.me_gusta,"
    		+ " pre.fecha_modificacion,"
    		+ " doc.ruta_documento,"
    		+ " pre.pregunta,"
    		+ " pre.titulo_pregunta,"
    		+ " usr.reputacion,"
    		+ " pre.guardado,"
    		+ " usr.nombre_usuario,"
    		+ " pre.vistas;", nativeQuery = true)
    Map<String, Object> getInfoQuestion(@Param("id") Long id);

	@Query(value = "SELECT"
			+ " pre.id AS id_pregunta,"
			+ " pre.titulo_pregunta AS titulo_pregunta,"
			+ " pre.estado_pregunta AS estado_pregunta,"
			+ " pre.fecha_creacion AS fecha_creacion,"
			+ " CONCAT(usr.primer_nombre, ' ', usr.apellido) AS nombre_usuario,"
			+ " usr.id AS id_usuario,"
			+ " doc.ruta_documento AS foto,"
			+ " pre.vistas AS vistas,"
			+ " pre.votos AS votos,"
			+ " COUNT(DISTINCT res.id) AS respuesta,"
			+ " JSON_ARRAYAGG("
			+ "  JSON_OBJECT("
			+ "   'id', cat.id,"
			+ "   'value', cat.nombre_categoria"
			+ "  )"
			+ " ) AS lista_categoria "
			+ "FROM pregunta pre "
			+ "INNER JOIN usuario usr ON pre.id_usuario = usr.id "
			+ "LEFT JOIN respuesta res ON pre.id = res.id_pregunta AND res.eliminado = FALSE "
			+ "LEFT JOIN documento doc ON usr.id = doc.id_usuario "
			+ "LEFT JOIN categoria cat ON cat.id = pre.id_categoria "
			+ "WHERE pre.eliminado = FALSE "
			+ "AND (:categoryName IS NULL OR cat.nombre_categoria = :categoryName) "
			+ "GROUP BY"
			+ " pre.id,"
			+ " pre.titulo_pregunta,"
			+ " pre.estado_pregunta,"
			+ " pre.fecha_creacion,"
			+ " usr.primer_nombre,"
			+ " usr.apellido,"
			+ " usr.id,"
			+ " doc.ruta_documento,"
			+ " pre.vistas,"
			+ " pre.votos;", nativeQuery = true)
	List<Map<String, Object>> getTopicList(@Param("categoryName") String categoryName);
}