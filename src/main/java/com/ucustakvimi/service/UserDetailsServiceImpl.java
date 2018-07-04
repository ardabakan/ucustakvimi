package com.ucustakvimi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ucustakvimi.model.CurrentUser;
import com.ucustakvimi.model.FlightUser;
import com.ucustakvimi.repository.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	@Autowired
	private UserRepository userRepository;

	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String email)
			throws UsernameNotFoundException {

		FlightUser user = userRepository.findByEmail(email);

		return new CurrentUser(user);
	}
}
