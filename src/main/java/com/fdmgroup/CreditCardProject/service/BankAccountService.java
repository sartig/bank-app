package com.fdmgroup.CreditCardProject.service;

import com.fdmgroup.CreditCardProject.exception.BankAccountNotFoundException;
import com.fdmgroup.CreditCardProject.exception.InsufficientBalanceException;
import com.fdmgroup.CreditCardProject.model.BankAccount;
import com.fdmgroup.CreditCardProject.model.BankTransaction;
import com.fdmgroup.CreditCardProject.model.User;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fdmgroup.CreditCardProject.repository.BankAccountRepository;
import com.fdmgroup.CreditCardProject.repository.BankTransactionRepository;
import com.fdmgroup.CreditCardProject.repository.UserRepository;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class BankAccountService {

	@Autowired
	private BankAccountRepository bankAccountRepository;
	
	@Autowired
	private UserRepository userRepository;

	private static final Logger log = LogManager.getLogger(BankAccountService.class);
	
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
	
	/**
     * Retrieves the current balance of a bank account by its unique identifier.
     * @param bankAccountId The unique identifier of the bank account.
     * @return The current balance of the specified bank account, or 0.0 if the account does not exist.
     */
	public BigDecimal getAccountBalanceByBankAccountNumber(String bankAccountNumber) {
		Optional<BankAccount> bankAccount = bankAccountRepository.findByAccountNumber(bankAccountNumber);
		if (bankAccount.isPresent()) {
			log.info("BankAccountServiceSuccess: The current balance of {} was obtained from {}.",bankAccount.get().getCurrentBalance(),bankAccountNumber);
			return bankAccount.get().getCurrentBalance();
		}else {
			log.error("BankAccountServiceError: Could not obtain current balance of {} as it does not exist.",bankAccountNumber);
			return BigDecimal.ZERO;
		}
	}

	/**
	 * Retrieves a list of bank account IDs associated with the given username.
	 * @param username The username for which to fetch bank account IDs.
	 * @return A list of bank account IDs associated with the specified username.
	 */
	public List<String> getBankAccountIdsByUsername(String username){
		List<String> bankAccountIds = new ArrayList<>();
		Optional<User> userOptional = userRepository.findByUsername(username);
		if(userOptional.isPresent()) {
			for (BankAccount b:userOptional.get().getBankAccounts()) {
				bankAccountIds.add(b.getAccountNumber());
			}
		}
		return bankAccountIds;
	}
	
	/**
	 * Retrieves a bank account associated with the given bank account number.
	 * @param bankaccountNumber The bank account number for which to fetch bank account.
	 * @return A bank account associated with the specified bank account number.
	 */
	public BankAccount getBankAccountByBankAccountNumber(String bankAccountNumber){
		return bankAccountRepository.findByAccountNumber(bankAccountNumber).get();
	}

	public boolean isAccountNumberValid(String accountNumber) {
		return bankAccountRepository.findByAccountNumber(accountNumber).isPresent();
	}
}
