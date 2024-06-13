package com.forum.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.forum.app.entity.Document;

public interface DocumentRepository extends JpaRepository<Document, Long> {
}