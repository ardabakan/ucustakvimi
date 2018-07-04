package com.ucustakvimi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ucustakvimi.model.FlightSearch;
import com.ucustakvimi.model.FlightUser;

public interface FlightSearchRepository extends
		JpaRepository<FlightSearch, Long> {

	List<FlightSearch> findById(Long id);

	List<FlightSearch> findByDate(String date);

	List<FlightSearch> findByUser(FlightUser user);

}