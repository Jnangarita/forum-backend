package com.forum.app.service;

import java.util.List;

import com.forum.app.entity.City;
import com.forum.app.entity.Country;

public interface LocationService {
	List<Country> getCountries();

	List<City> getCities(Long id);
}