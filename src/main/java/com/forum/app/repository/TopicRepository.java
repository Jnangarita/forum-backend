package com.forum.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.forum.app.entity.Topic;
import java.util.List;
import java.util.Map;

public interface TopicRepository extends JpaRepository<Topic, Long> {
	List<Topic> findByDeletedFalse();

    @Query(value = "SELECT"
            + " p.id,"
            + " p.pregunta,"
            + " p.id_usuario,"
            + " 'Q' AS tipo,"
            + " p.estado_pregunta,"
            + " p.fecha_creacion "
            + "FROM pregunta p "
            + "WHERE p.eliminado = FALSE AND p.id_usuario = :id", 
            nativeQuery = true)
	List<Map<String, Object>> findPostedQuestionByUserId(@Param("id") Long id);

    @Query(value = "SELECT"
    		+ " pre.id AS id_pregunta,"
    		+ " pre.titulo_pregunta AS titulo_pregunta,"
    		+ " pre.estado_pregunta AS estado_pregunta,"
    		+ " pre.fecha_creacion AS fecha_creacion,"
    		+ " CONCAT(usr.primer_nombre, ' ', usr.apellido) AS nombre_usuario,"
    		+ " usr.id AS id_usuario,"
    		+ " usr.foto AS foto,"
    		+ " pre.vistas AS vistas,"
    		+ " pre.votos AS votos,"
    		+ " COUNT(DISTINCT res.id) AS respuestas,"
    		+ " (SELECT JSON_ARRAYAGG("
    		+ "  JSON_OBJECT("
    		+ "   'id', c.id,"
    		+ "   'value', c.nombre_categoria)) "
    		+ "FROM categoria c "
    		+ "INNER JOIN pregunta p ON p.id_categoria = c.id "
    		+ "WHERE p.id = pre.id "
    		+ "GROUP BY c.id) AS lista_categoria "
    		+ "FROM pregunta pre "
    		+ "INNER JOIN usuario usr ON pre.id_usuario = usr.id "
    		+ "LEFT JOIN respuesta res ON pre.id = res.id_pregunta AND res.eliminado = FALSE "
    		+ "WHERE pre.eliminado = FALSE "
    		+ "GROUP BY"
    		+ " pre.id,"
    		+ " pre.titulo_pregunta,"
    		+ " pre.estado_pregunta,"
    		+ " pre.fecha_creacion,"
    		+ " usr.primer_nombre,"
    		+ " usr.apellido,"
    		+ " usr.id,"
    		+ " usr.foto,"
    		+ " pre.vistas,"
    		+ " pre.votos;", nativeQuery = true)
    List<Map<String, Object>> getQuestionList();

    @Query(value = "SELECT COUNT(*) FROM pregunta;", nativeQuery = true)
    Integer getNumberQuestion();
}