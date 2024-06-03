package com.forum.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import com.forum.app.entity.User;
import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
	UserDetails findByEmail(String email);

	List<User> findByDeletedFalse();
}