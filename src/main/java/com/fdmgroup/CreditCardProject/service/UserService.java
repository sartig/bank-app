package com.fdmgroup.CreditCardProject.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.fdmgroup.CreditCardProject.model.User;
import com.fdmgroup.CreditCardProject.repository.UserRepository;

import java.util.Optional;

@Service
public class UserService {

	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private UserRepository userRepository;

	public boolean registerUser(String username, String password) {//throws EmailExistsException {
		if(userRepository.existsByUsername(username)) {
			return false;
		}
		User user = new User();
		user.setUsername(username);
		user.setPassword(passwordEncoder.encode(password));
		userRepository.save(user);
		return true;
	}

}
