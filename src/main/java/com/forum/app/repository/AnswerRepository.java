package com.forum.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.forum.app.entity.Answer;

public interface AnswerRepository extends JpaRepository<Answer, Long> {
	List<Answer> findByDeletedFalse();

	List<Answer> findByDeletedFalseAndUserId(Long id);
}