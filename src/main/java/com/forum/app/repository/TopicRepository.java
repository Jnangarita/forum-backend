package com.forum.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.forum.app.entity.Topic;

public interface TopicRepository extends JpaRepository<Topic, Integer> {
}