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
}