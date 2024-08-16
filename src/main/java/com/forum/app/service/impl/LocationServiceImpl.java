package com.forum.app.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.forum.app.dto.IdValueDTO;
import com.forum.app.exception.OwnRuntimeException;
import com.forum.app.repository.LocationRepository;
import com.forum.app.service.LocationService;
import com.forum.app.utils.Utility;

@Service
public class LocationServiceImpl implements LocationService {

	private final Utility utility;
	private final LocationRepository locationRepository;

	public LocationServiceImpl(Utility utility, LocationRepository locationRepository) {
		this.utility = utility;
		this.locationRepository = locationRepository;
	}

	@Override
	public List<IdValueDTO> getCountries() {
		try {
			return locationRepository.findCountry();
		} catch (Exception e) {
			throw new OwnRuntimeException(utility.getMessage("forum.message.error.getting.countries", null));
		}
	}
}