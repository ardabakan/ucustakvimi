package com.ucustakvimi.rest;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ucustakvimi.model.Airport;
import com.ucustakvimi.repository.AirportRepository;
import com.ucustakvimi.repository.UserRepository;

@RestController
@RequestMapping("/rest/airports")
public class AirportRestService {

	@Autowired
	private AirportRepository airportRepository;

	@Autowired
	private UserRepository userRepository;

	@RequestMapping(value = "/listairports", method = { RequestMethod.GET })
	public HashMap<String, List<Airport>> allAirports(
			@RequestParam String query,
			@AuthenticationPrincipal Principal principal) {

		List<Airport> result = airportRepository
				.findByNameIgnoreCaseContaining(query);

		HashMap<String, List<Airport>> returnMap = new HashMap<>();
		returnMap.put("suggestions", result);
		return returnMap;

	}

	@RequestMapping(value = "/listairportsasc", method = { RequestMethod.GET })
	public HashMap<String, List<Airport>> allAvailableAirportsAsc(
			@AuthenticationPrincipal Principal principal) {

		List<Airport> result = airportRepository.findAllByOrderByIdAsc();

		HashMap<String, List<Airport>> returnMap = new HashMap<>();
		returnMap.put("suggestions", result);

		return returnMap;

	}

	@RequestMapping(value = "/airportdetail/{id}", method = {
			RequestMethod.GET })
	public Airport AirportDetail(@PathVariable("id") Long id,
			@AuthenticationPrincipal Principal principal) {

		Airport tempAirport = airportRepository.findById(id).orElse(null);

		return tempAirport;

	}

}
