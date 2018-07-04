package com.ucustakvimi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.ucustakvimi.repository.AirportRepository;
import com.ucustakvimi.repository.FlightSearchRepository;
import com.ucustakvimi.repository.UserRepository;
import com.ucustakvimi.service.FlightService;

@Controller
public class FlightController {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private FlightService flightService;

	@Autowired
	private AirportRepository monitorRepository;

	@Autowired
	private FlightSearchRepository flightSearchRepository;

}
