package com.fdmgroup.CreditCardProject.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fdmgroup.CreditCardProject.repository.BankTransactionRepository;

@Service
public class BankTransactionService {
	
	@Autowired
	private BankTransactionRepository bankTransactionRepo;
}
