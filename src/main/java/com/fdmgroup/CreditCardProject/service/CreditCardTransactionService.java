package com.fdmgroup.CreditCardProject.service;

import com.fdmgroup.CreditCardProject.exception.InsufficientFundsException;
import com.fdmgroup.CreditCardProject.model.CreditCard;
import com.fdmgroup.CreditCardProject.model.CreditCardTransaction;
import com.fdmgroup.CreditCardProject.model.User;
import com.fdmgroup.CreditCardProject.repository.CreditCardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fdmgroup.CreditCardProject.repository.CreditCardTransactionRepository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class CreditCardTransactionService {
	
	@Autowired
	private CreditCardTransactionRepository creditCardTransactionRepository;
	@Autowired
	private CreditCardRepository creditCardRepository;


	@Transactional
	public void processTransaction(User user, CreditCard creditCard, CreditCardTransaction transaction) throws InsufficientFundsException {
		// Check if the user has sufficient funds
		if (creditCard.getCurrentBalance().compareTo(transaction.getAmount()) < 0) {
			throw new InsufficientFundsException("Insufficient funds");
		}

		// Update the user's balance
		BigDecimal newBalance = creditCard.getCurrentBalance().subtract(transaction.getAmount());
		creditCard.setCurrentBalance(newBalance);

		// Save the transaction
		transaction.setCreditCard(creditCard);
		creditCard.addTransactionHistory(transaction);
		creditCardRepository.save(creditCard);
	}

	public double getCurrencyConversionRate(String originalCurrencyCode) {
		// Placeholder method to retrieve the currency conversion rate

		// Implement your logic to fetch the conversion rate from a service or database

		return 1.0; // For demonstration, return 1.0 (no conversion)
	}

}
