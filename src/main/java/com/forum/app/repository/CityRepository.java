package com.forum.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.forum.app.entity.City;

public interface CityRepository extends JpaRepository<City, Long> {

	@Query("SELECT c FROM City c WHERE c.country.id = :id")
	List<City> findCity(@Param("id") Long id);
}