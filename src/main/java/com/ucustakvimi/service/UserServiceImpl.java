package com.ucustakvimi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.ucustakvimi.model.FlightUser;
import com.ucustakvimi.model.Role;
import com.ucustakvimi.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	public void save(FlightUser user) {
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		user.setRole(Role.USER);
		userRepository.save(user);
	}

	public FlightUser findByEmail(String email) {
		return userRepository.findByEmail(email);
	}
}
