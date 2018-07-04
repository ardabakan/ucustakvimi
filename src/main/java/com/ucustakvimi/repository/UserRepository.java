package com.ucustakvimi.repository;

import org.springframework.data.repository.CrudRepository;

import com.ucustakvimi.model.FlightUser;

public interface UserRepository extends CrudRepository<FlightUser, Long> {
	FlightUser findByEmail(String email);
}
