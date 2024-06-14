package com.forum.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.forum.app.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}