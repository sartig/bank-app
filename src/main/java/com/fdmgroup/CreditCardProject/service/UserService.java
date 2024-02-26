package com.fdmgroup.CreditCardProject.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fdmgroup.CreditCardProject.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepo;
}
