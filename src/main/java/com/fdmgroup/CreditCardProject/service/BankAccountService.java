package com.fdmgroup.CreditCardProject.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fdmgroup.CreditCardProject.model.BankAccount;
import com.fdmgroup.CreditCardProject.repository.BankAccountRepository;

/**
 * Service class responsible for handling operations related to bank accounts.
 * @author Danny
 * @version 1.0
 * @since 2024-02-27
 */
@Service
public class BankAccountService {

	@Autowired
	private BankAccountRepository bankAccountRepo;
	
	private static final Logger log = LogManager.getLogger(BankAccountService.class);
	
	/**
     * Retrieves the current balance of a bank account by its unique identifier.
     * @param bankAccountId The unique identifier of the bank account.
     * @return The current balance of the specified bank account, or 0.0 if the account does not exist.
     */
	public double getAccountBalanceByBankAccountId(long bankAccountId) {
		Optional<BankAccount> bankAccount = bankAccountRepo.findByBankAccountId(bankAccountId);
		if (bankAccount.isPresent()) {
			log.info("BankAccountServiceSuccess: The current balance of {} was obtained from {}.",bankAccount.get().getCurrentBalance(),bankAccountId);
			return bankAccount.get().getCurrentBalance();
		}else {
			log.error("BankAccountServiceError: Could not obtain current balance of {} as it does not exist.",bankAccountId);
			return 0.0;
		}
	}
	
	/**
	 * Retrieves a list of bank account IDs associated with the given username.
	 * @param username The username for which to fetch bank account IDs.
	 * @return A list of bank account IDs associated with the specified username.
	 */
	public List<Long> getBankAccountIdByUsername(String username){
		List<Long> bankAccountIds = new ArrayList<>();
		for (BankAccount b:bankAccountRepo.findByUsername(username)) {
			bankAccountIds.add(b.getAccountId());
		}
		return bankAccountIds;
	}
	
}
