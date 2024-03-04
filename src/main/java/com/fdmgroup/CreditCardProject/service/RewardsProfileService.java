package com.fdmgroup.CreditCardProject.service;

import com.fdmgroup.CreditCardProject.model.CreditCard;
import com.fdmgroup.CreditCardProject.model.CreditCardTransaction;
import com.fdmgroup.CreditCardProject.model.MerchantCategoryCode;
import com.fdmgroup.CreditCardProject.model.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fdmgroup.CreditCardProject.repository.RewardsProfileRepository;

@Service
public class RewardsProfileService {

	public int calculateRewardPoints(String Category, CreditCard selectedCreditCard, CreditCardTransaction transaction) {
		int rewardPoints = 0;
		switch (Category) {
			case "Food Services":
				if (selectedCreditCard.getRewardProfile().getRewardProfileId() == 1) {
					double conversionPercentage = selectedCreditCard.getRewardProfile().getConversionPercentage() / 100.0;
					rewardPoints = (int) (conversionPercentage * transaction.getAmount().doubleValue());
				}
				break;
			case "Airlines":
			case "Hospitality":
				if (selectedCreditCard.getRewardProfile().getRewardProfileId() == 2) {
					double conversionPercentage = selectedCreditCard.getRewardProfile().getConversionPercentage() / 100.0;
					rewardPoints = (int) (conversionPercentage * transaction.getAmount().doubleValue());
				}
				break;
			case "Online Shopping":
			case "Taxi":
				if (selectedCreditCard.getRewardProfile().getRewardProfileId() == 3) {
					double conversionPercentage = selectedCreditCard.getRewardProfile().getConversionPercentage() / 100.0;
					rewardPoints = (int) (conversionPercentage * transaction.getAmount().doubleValue());
				}
				break;
			default:
				break;
		}
		return -rewardPoints;
	}
}
