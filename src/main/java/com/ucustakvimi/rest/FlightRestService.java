package com.ucustakvimi.rest;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.ucustakvimi.model.Flight;
import com.ucustakvimi.model.FlightSearch;
import com.ucustakvimi.repository.AirportRepository;
import com.ucustakvimi.repository.FlightSearchRepository;
import com.ucustakvimi.repository.UserRepository;
import com.ucustakvimi.service.FlightService;

@RestController
@RequestMapping("/rest/flight")
public class FlightRestService {

	@Autowired
	private AirportRepository monitorRepository;

	@Autowired
	private FlightSearchRepository flightSearchRepository;

	@Autowired
	private FlightService flightService;

	@Autowired
	private UserRepository userRepository;

	@RequestMapping(value = "/search/bydateandairway/{f}/{t}/{d}/{a}", method = RequestMethod.GET)
	public List<Flight> searchByDateAndAirway(
			@PathVariable("f") String from, @PathVariable("t") String to,
			@PathVariable("d") String date, @PathVariable("a") String airway,
			@AuthenticationPrincipal Principal principal) {

		/*
		 * CurrentUser userSpring = (CurrentUser) SecurityContextHolder
		 * .getContext().getAuthentication().getPrincipal();
		 * 
		 * String username = userSpring.getUsername(); // get logged in //
		 * username FlightUser user = userRepository.findByEmail(username);
		 */

		FlightSearch flightSearch = new FlightSearch();
		flightSearch.setFromCode(from);
		flightSearch.setToCode(to);
		flightSearch.setDate(date);

		flightService.addFlightSearch(flightSearch);

		return flightService.searchFlight(from, to, date, airway);

	}

	@ResponseStatus(value = HttpStatus.FORBIDDEN, reason = "Not found or no credentials")
	class FlightSearchException extends RuntimeException {

	}

}
