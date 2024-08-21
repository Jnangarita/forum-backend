package com.forum.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.forum.app.dto.IdValueDTO;
import com.forum.app.entity.City;

public interface CityRepository extends JpaRepository<City, Long> {

	@Query("SELECT new com.forum.app.dto.IdValueDTO(c.id, c.cityName) FROM City c WHERE c.countryId = :id")
	List<IdValueDTO> findCity(@Param("id") Long id);
}