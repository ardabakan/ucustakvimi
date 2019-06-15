package com.ucustakvimi.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ucustakvimi.model.Flight;
import com.ucustakvimi.model.FlightSearch;
import com.ucustakvimi.model.FlightUser;

@Service
public interface FlightService {

	List<Flight> searchFlight(String from, String to, String date, String airway);

	public FlightSearch addFlightSearch(FlightSearch flightSearch);

	List<FlightSearch> listAll(FlightUser user);
}
