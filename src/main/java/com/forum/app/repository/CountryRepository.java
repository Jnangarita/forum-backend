package com.forum.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.forum.app.dto.IdValueDTO;
import com.forum.app.entity.Country;

public interface CountryRepository extends JpaRepository<Country, Long> {

	@Query("SELECT new com.forum.app.dto.IdValueDTO(c.id, c.countryName) FROM Country c")
	List<IdValueDTO> findCountry();
}