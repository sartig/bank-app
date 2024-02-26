package com.fdmgroup.CreditCardProject.service;

import com.fdmgroup.CreditCardProject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fdmgroup.CreditCardProject.model.User;

import java.util.Optional;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	public boolean registerUser(User user) {
		Optional<User> userOptional = userRepository.findByUsername(user.getUsername());

		if(userOptional.isEmpty()) {
			userRepository.save(user);
			return true;
		} else {
			return false;
		}
	}
}
