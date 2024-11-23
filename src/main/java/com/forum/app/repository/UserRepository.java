package com.forum.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.userdetails.UserDetails;

import com.forum.app.entity.User;
import java.util.List;
import java.util.Map;

public interface UserRepository extends JpaRepository<User, Long> {
	UserDetails findByEmail(String email);

	List<User> findByDeletedFalse();

	@Query("SELECT u FROM User u")
	List<User> userInfoList();

	@Query(value = "SELECT * FROM usuario u WHERE correo_electronico = :email", nativeQuery = true)
	User getUserByEmail(@Param("email")String email);
}