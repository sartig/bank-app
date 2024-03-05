package com.fdmgroup.CreditCardProject.service;

import com.fdmgroup.CreditCardProject.exception.InsufficientFundsException;
import com.fdmgroup.CreditCardProject.model.CreditCard;
import com.fdmgroup.CreditCardProject.model.CreditCardTransaction;
import com.fdmgroup.CreditCardProject.model.RewardsProfile;
import com.fdmgroup.CreditCardProject.model.User;
import com.fdmgroup.CreditCardProject.repository.CreditCardTransactionRepository;
import com.fdmgroup.CreditCardProject.repository.RewardsProfileRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fdmgroup.CreditCardProject.repository.CreditCardRepository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Random;

@Service
public class CreditCardService {

	@Autowired
	private CreditCardRepository creditCardRepository;
	@Autowired
	private RewardsProfileRepository rewardsProfileRepository;
	@Autowired
	private CreditCardTransactionRepository creditCardTransactionRepository;

	public void createCreditCardForUser(User user,long setSpendingLimit, long setCurrentBalance,long setRewardProfileId) throws EntityNotFoundException {
		CreditCard creditCard = new CreditCard();

		String creditNumber;
		do {
			creditNumber = generateAccountNumber();
		} while (!isAccountNumberUnique(creditNumber));

		creditCard.setAccountNumber(creditNumber);
		creditCard.setUser(user);
		creditCard.setMonthlyDueDate((byte) 1);
		creditCard.setSpendingLimit(BigDecimal.valueOf(setSpendingLimit));
		creditCard.setCurrentBalance(BigDecimal.valueOf(setCurrentBalance));
		user.setRewardsPoints(0);

		// Set the reward profile for the credit card
		RewardsProfile rewardsProfile = rewardsProfileRepository.findById(setRewardProfileId)
				.orElseThrow(() -> new EntityNotFoundException("RewardsProfile not found"));
		creditCard.setRewardProfile(rewardsProfile);
		user.getCreditCards().add(creditCard);
		creditCardRepository.save(creditCard);
	}

	private boolean isAccountNumberUnique(String accountNumber) {
		return creditCardRepository.findByAccountNumber(accountNumber).isEmpty();
	}

	private String generateAccountNumber() {
		Random random = new Random();
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < 16; i++) {
			sb.append(random.nextInt(10));
		}
		return sb.toString();
	}
	
	public CreditCard getCardByNumber(String number) {
		return creditCardRepository.findByAccountNumber(number).orElseThrow(EntityNotFoundException::new);
	}


}