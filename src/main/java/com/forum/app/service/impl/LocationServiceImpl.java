package com.forum.app.service.impl;

import java.util.List;

import com.forum.app.entity.City;
import com.forum.app.entity.Country;
import org.springframework.stereotype.Service;

import com.forum.app.exception.OwnRuntimeException;
import com.forum.app.repository.CityRepository;
import com.forum.app.repository.CountryRepository;
import com.forum.app.service.LocationService;
import com.forum.app.utils.Utility;

@Service
public class LocationServiceImpl implements LocationService {

	private final Utility utility;
	private final CountryRepository countryRepository;
	private final CityRepository cityRepository;

	public LocationServiceImpl(Utility utility, CountryRepository countryRepository, CityRepository cityRepository) {
		this.utility = utility;
		this.countryRepository = countryRepository;
		this.cityRepository = cityRepository;
	}

	@Override
	public List<Country> getCountries() {
		try {
			return countryRepository.findCountry();
		} catch (Exception e) {
			throw new OwnRuntimeException(utility.getMessage("forum.message.error.getting.countries", null));
		}
	}

	@Override
	public List<City> getCities(Long id) {
		try {
			return cityRepository.findCity(id);
		} catch (Exception e) {
			throw new OwnRuntimeException(utility.getMessage("forum.message.error.getting.cities", null));
		}
	}
}