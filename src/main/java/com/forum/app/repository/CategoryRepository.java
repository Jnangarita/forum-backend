package com.forum.app.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.forum.app.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
	List<Category> findByDeletedFalse();

    @Query(value = "SELECT"
    		+ " c.id,"
    		+ " c.nombre_categoria,"
    		+ " c.descripcion,"
    		+ " COUNT(p.id) AS numero_preguntas,"
    		+ " COALESCE(MAX(p.fecha_creacion), c.fecha_creacion) AS fecha "
    		+ "FROM categoria c "
    		+ "LEFT JOIN pregunta p ON c.id = p.id_categoria AND p.eliminado = FALSE "
    		+ "GROUP BY c.id, c.nombre_categoria, c.descripcion, c.fecha_creacion;",
            nativeQuery = true)
	List<Map<String, Object>> findCategoryByDeletedFalse();
}