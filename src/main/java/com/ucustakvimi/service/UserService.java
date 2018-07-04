package com.ucustakvimi.service;

import com.ucustakvimi.model.FlightUser;

public interface UserService {
	void save(FlightUser user);

	FlightUser findByEmail(String email);
}
