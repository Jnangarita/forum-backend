package com.forum.app.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.forum.app.dto.request.IdValueInput;
import com.forum.app.service.LocationService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("${spring.data.rest.basePath}/v1/locations")
public class LocationController {

	private final LocationService locationService;

	public LocationController(LocationService locationService) {
		this.locationService = locationService;
	}

	@Operation(summary = "Get list of countries")
	@GetMapping("/countries")
	@ResponseStatus(HttpStatus.OK)
	@SecurityRequirement(name = "bearer-key")
	public ResponseEntity<List<IdValueInput>> getCountryList() {
		List<IdValueInput> countries = locationService.getCountries();
		return ResponseEntity.ok(countries);
	}

	@Operation(summary = "Get the list of cities in a country")
	@GetMapping("/{countryId}/cities")
	@ResponseStatus(HttpStatus.OK)
	@SecurityRequirement(name = "bearer-key")
	public ResponseEntity<List<IdValueInput>> getCityList(
			@Parameter(description = "Id of the country to search") @PathVariable Long countryId) {
		List<IdValueInput> cities = locationService.getCities(countryId);
		return ResponseEntity.ok(cities);
	}
}