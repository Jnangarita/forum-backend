package com.forum.app.service;

import java.util.List;

import com.forum.app.dto.request.IdValueInput;

public interface LocationService {
	List<IdValueInput> getCountries();

	List<IdValueInput> getCities(Long id);
}