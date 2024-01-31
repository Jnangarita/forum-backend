package com.forum.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.forum.app.entity.Topic;
import java.util.List;

public interface TopicRepository extends JpaRepository<Topic, Long> {
	List<Topic> findByDeletedFalse();
}