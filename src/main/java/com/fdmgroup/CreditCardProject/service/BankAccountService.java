package com.fdmgroup.CreditCardProject.service;

import com.fdmgroup.CreditCardProject.exception.BankAccountNotFoundException;
import com.fdmgroup.CreditCardProject.model.BankAccount;
import com.fdmgroup.CreditCardProject.model.BankTransaction;
import com.fdmgroup.CreditCardProject.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fdmgroup.CreditCardProject.repository.BankAccountRepository;
import com.fdmgroup.CreditCardProject.repository.BankTransactionRepository;

import java.math.BigDecimal;
import java.util.Random;

@Service
public class BankAccountService {

	@Autowired
	private BankAccountRepository bankAccountRepository;

	@Autowired
	private BankTransactionRepository bankTransactionRepository;

	public void createBankAccountForUser(User user) {
		BankAccount bankAccount = new BankAccount();

		String bankNumber;
		do {
			bankNumber = generateAccountNumber();
		} while (!isAccountNumberUnique(bankNumber));

		bankAccount.setAccountNumber(bankNumber);
		bankAccount.setUser(user);
		user.getBankAccounts().add(bankAccount);
		bankAccountRepository.save(bankAccount);
	}

	public long depositToAccount(String accountId, double amount) throws BankAccountNotFoundException {
		BankAccount bankAccount = bankAccountRepository.findByAccountNumber(accountId)
				.orElseThrow(() -> new BankAccountNotFoundException());

		BankTransaction transaction = bankTransactionRepository
				.save(new BankTransaction(amount, bankAccount.getAccountId()));

		double currentBalance = bankAccount.getCurrentBalance();
		double newBalance = BigDecimal.valueOf(amount).add(BigDecimal.valueOf(currentBalance)).doubleValue();
		bankAccount.setCurrentBalance(newBalance);
		bankAccount.addTransactionHistory(transaction);
		bankAccountRepository.save(bankAccount);
		return transaction.getTransactionId();
	}

	public String getUsernameOfAccountByAccountNumber(String accountNumber) throws BankAccountNotFoundException {
		BankAccount bankAccount = bankAccountRepository.findByAccountNumber(accountNumber)
				.orElseThrow(() -> new BankAccountNotFoundException());
		return bankAccount.getUser().getUsername();
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