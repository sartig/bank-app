package com.fdmgroup.CreditCardProject.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fdmgroup.CreditCardProject.repository.CreditCardTransactionRepository;

@Service
public class CreditCardTransactionService {
	
	@Autowired
	private CreditCardTransactionRepository creditCardTransactionRepo;
}
