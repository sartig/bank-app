package com.fdmgroup.CreditCardProject.service;

import com.fdmgroup.CreditCardProject.model.BankAccount;
import com.fdmgroup.CreditCardProject.model.User;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fdmgroup.CreditCardProject.repository.BankAccountRepository;
import com.fdmgroup.CreditCardProject.repository.UserRepository;

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
	public double getAccountBalanceByBankAccountNumber(String bankAccountNumber) {
		Optional<BankAccount> bankAccount = bankAccountRepository.findByAccountNumber(bankAccountNumber);
		if (bankAccount.isPresent()) {
			log.info("BankAccountServiceSuccess: The current balance of {} was obtained from {}.",bankAccount.get().getCurrentBalance(),bankAccountNumber);
			return bankAccount.get().getCurrentBalance();
		}else {
			log.error("BankAccountServiceError: Could not obtain current balance of {} as it does not exist.",bankAccountNumber);
			return 0.0;
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

}

