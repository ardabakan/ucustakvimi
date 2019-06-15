package com.ucustakvimi.service;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.hibernate.service.spi.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.ucustakvimi.model.Flight;
import com.ucustakvimi.model.FlightSearch;
import com.ucustakvimi.model.FlightUser;
import com.ucustakvimi.repository.FlightSearchRepository;
import com.ucustakvimi.repository.UserRepository;

@Service
public class FlightServiceImpl implements FlightService {

	private static final Logger logger = LoggerFactory
			.getLogger(FlightServiceImpl.class);

	@Autowired
	private FlightSearchRepository flightSearchRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private Flight flight;

	@Override
	public List<Flight> searchFlight(String fromAirportCode,
			String toAirportCode, String flightDate, String flightAirway) {

		List<Flight> results = null;

		String searchLanguage = "tr";

		MethodType methodType;
		MethodHandle methodHandle;
		MethodHandles.Lookup lookup = MethodHandles.lookup();

		try {
			methodType = MethodType.methodType(ArrayList.class, String.class,
					String.class, String.class, String.class);
			methodHandle = lookup.findVirtual(Flight.class,
					"searchFlight" + flightAirway, methodType);

			results = (ArrayList) methodHandle.invokeExact(flight,
					fromAirportCode, toAirportCode, flightDate, searchLanguage);

		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error("Airway not found for search");
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (results == null) {

			return Collections.EMPTY_LIST;

		} else {

			return results;
		}
	}

	@Override
	public FlightSearch addFlightSearch(FlightSearch flightSearch) {

		try {

			return flightSearchRepository.save(flightSearch);

		} catch (DataIntegrityViolationException e) {
			throw new ServiceException(e.getMessage(), e);
		}

	}

	@Override
	public List<FlightSearch> listAll(FlightUser user) {

		FlightUser persistentUser = userRepository.findById(user.getId()).orElse(null);;

		List result = flightSearchRepository.findByUser(persistentUser);

		if (result == null) {

			return Collections.EMPTY_LIST;

		} else {

			return result;
		}

	}

}
