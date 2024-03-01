package com.fdmgroup.CreditCardProject.service;

import com.fdmgroup.CreditCardProject.model.BankAccount;
import com.fdmgroup.CreditCardProject.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fdmgroup.CreditCardProject.repository.BankAccountRepository;

import java.math.BigDecimal;
import java.util.Random;

@Service
public class BankAccountService {

	@Autowired
	private BankAccountRepository bankAccountRepository;

	public void createBankAccountForUser(User user) {

		String bankNumber;
		do {
			bankNumber = generateAccountNumber();
		} while (!isAccountNumberUnique(bankNumber));

		BankAccount bankAccount = new BankAccount(user, bankNumber, BigDecimal.ZERO);
		user.getBankAccounts().add(bankAccount);
		bankAccountRepository.save(bankAccount);
	}

	private boolean isAccountNumberUnique(String accountNumber) {
		return bankAccountRepository.findByAccountNumber(accountNumber).isEmpty();
	}

	private String generateAccountNumber() {
		Random random = new Random();
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < 16; i++) {
			sb.append(random.nextInt(10));
		}
		return sb.toString();
	}
}