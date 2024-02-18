package com.forum.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.forum.app.entity.Answer;

public interface AnswerRepository extends JpaRepository<Answer, Long> {
}