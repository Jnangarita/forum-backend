package com.forum.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.forum.app.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
	List<Category> findByDeletedFalse();
}