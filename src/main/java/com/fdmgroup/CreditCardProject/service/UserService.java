package com.fdmgroup.CreditCardProject.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fdmgroup.CreditCardProject.model.User;
import com.fdmgroup.CreditCardProject.repository.UserRepository;

import java.util.Optional;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepo;
	
	public void registerUser(User user) {
		// validation and logic here
		userRepo.save(user);
	}

    public boolean verifyUser(String username, String password) {
		Optional<User> user = userRepo.findByUsername(username);
		if(user.isEmpty()){
			return false;
		}else{
			return user.get().getPassword().equals(password);
		}
    }
}
