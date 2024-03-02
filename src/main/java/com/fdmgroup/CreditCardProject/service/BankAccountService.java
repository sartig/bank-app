package com.fdmgroup.CreditCardProject.service;

import com.fdmgroup.CreditCardProject.exception.BankAccountNotFoundException;
import com.fdmgroup.CreditCardProject.exception.InsufficientBalanceException;
import com.fdmgroup.CreditCardProject.model.BankAccount;
import com.fdmgroup.CreditCardProject.model.BankTransaction;
import com.fdmgroup.CreditCardProject.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fdmgroup.CreditCardProject.repository.BankAccountRepository;
import com.fdmgroup.CreditCardProject.repository.BankTransactionRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Random;

@Service
public class BankAccountService {

	@Autowired
	private BankAccountRepository bankAccountRepository;

	@Autowired
	private BankTransactionRepository bankTransactionRepository;

	public void createBankAccountForUser(User user) {

		String bankNumber;
		do {
			bankNumber = generateAccountNumber();
		} while (!isAccountNumberUnique(bankNumber));

		BankAccount bankAccount = new BankAccount(user, bankNumber, BigDecimal.ZERO);
		user.getBankAccounts().add(bankAccount);
		bankAccountRepository.save(bankAccount);
	}

	public long transferBetweenAccounts(String accountFromId, String accountToId, BigDecimal amount)
			throws BankAccountNotFoundException, InsufficientBalanceException {
		BankAccount bankAccountFrom = bankAccountRepository.findByAccountNumber(accountFromId)
				.orElseThrow(BankAccountNotFoundException::new);

		BankAccount bankAccountTo = bankAccountRepository.findByAccountNumber(accountToId)
				.orElseThrow(BankAccountNotFoundException::new);

		if (bankAccountFrom.getCurrentBalance().compareTo(amount) < 0) {
			throw new InsufficientBalanceException();
		}

		BankTransaction transaction = bankTransactionRepository
				.save(new BankTransaction(bankAccountFrom.getAccountId(), amount, bankAccountTo.getAccountId()));
		
		BigDecimal newFromBalance = bankAccountFrom.getCurrentBalance().subtract(amount);
		bankAccountFrom.setCurrentBalance(newFromBalance);
		bankAccountFrom.addTransactionHistory(transaction);
		BigDecimal newToBalance = bankAccountTo.getCurrentBalance().add(amount);
		bankAccountTo.setCurrentBalance(newToBalance);
		bankAccountTo.addTransactionHistory(transaction);
		bankAccountRepository.saveAll(List.of(bankAccountFrom, bankAccountTo));
		return transaction.getTransactionId();

	}

	public long depositToAccount(String accountId, BigDecimal amount) throws BankAccountNotFoundException {
		BankAccount bankAccount = bankAccountRepository.findByAccountNumber(accountId)
				.orElseThrow(BankAccountNotFoundException::new);

		BankTransaction transaction = bankTransactionRepository
				.save(new BankTransaction(amount, bankAccount.getAccountId()));

		BigDecimal currentBalance = bankAccount.getCurrentBalance();
		BigDecimal newBalance = amount.add(currentBalance);
		bankAccount.setCurrentBalance(newBalance);
		bankAccount.addTransactionHistory(transaction);
		bankAccountRepository.save(bankAccount);
		return transaction.getTransactionId();
	}
	
	public long withdrawFromAccount(String accountId, BigDecimal amount) throws BankAccountNotFoundException, InsufficientBalanceException {
		BankAccount bankAccount = bankAccountRepository.findByAccountNumber(accountId)
				.orElseThrow(BankAccountNotFoundException::new);

		// check if account has enough funds to withdraw
		if (bankAccount.getCurrentBalance().compareTo(amount) < 0) {
			throw new InsufficientBalanceException();
		}
		
		BankTransaction transaction = bankTransactionRepository
				.save(new BankTransaction(amount, bankAccount.getAccountId()));

		BigDecimal currentBalance = bankAccount.getCurrentBalance();
		BigDecimal newBalance = currentBalance.subtract(amount);
		bankAccount.setCurrentBalance(newBalance);
		bankAccount.addTransactionHistory(transaction);
		bankAccountRepository.save(bankAccount);
		return transaction.getTransactionId();
	}

	public String getUsernameOfAccountByAccountNumber(String accountNumber) throws BankAccountNotFoundException {
		if (!isAccountNumberValid(accountNumber)) {
			throw new BankAccountNotFoundException();
		}

		BankAccount bankAccount = bankAccountRepository.findByAccountNumber(accountNumber).get();
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

	public boolean isAccountNumberValid(String accountNumber) {
		return bankAccountRepository.findByAccountNumber(accountNumber).isPresent();
	}
}