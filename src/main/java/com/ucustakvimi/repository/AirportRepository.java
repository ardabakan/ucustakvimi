package com.ucustakvimi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ucustakvimi.model.Airport;

public interface AirportRepository extends JpaRepository<Airport, Long> {

	Airport findById(Integer airportId);
	
	Airport findByCode(String code);

	List<Airport> findAllByOrderByIdDesc();

	List<Airport> findAllByOrderByIdAsc();

}