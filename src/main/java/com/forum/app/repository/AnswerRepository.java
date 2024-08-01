package com.forum.app.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.forum.app.entity.Answer;

public interface AnswerRepository extends JpaRepository<Answer, Long> {
	List<Answer> findByDeletedFalse();

    @Query(value = "SELECT"
            + " r.id,"
            + " r.respuesta,"
            + " r.id_usuario,"
            + " 'A' AS tipo,"
            + " r.estado_respuesta,"
            + " r.fecha_creacion "
            + "FROM respuesta r "
            + "WHERE r.eliminado = FALSE AND r.id_usuario = :id", 
            nativeQuery = true)
	List<Map<String, Object>> findPostedAnswersByUserId(@Param("id") Long id);
}