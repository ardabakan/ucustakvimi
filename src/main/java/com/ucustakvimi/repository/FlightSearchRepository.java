package com.ucustakvimi.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ucustakvimi.model.FlightSearch;
import com.ucustakvimi.model.FlightUser;

public interface FlightSearchRepository extends
		JpaRepository<FlightSearch, Long> {

	Optional<FlightSearch> findById(Long id);

	List<FlightSearch> findByDate(String date);

	List<FlightSearch> findByUser(FlightUser user);

}