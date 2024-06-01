package com.forum.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import com.forum.app.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
	UserDetails findByEmail(String email);
}