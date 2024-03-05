package com.fdmgroup.CreditCardProject.service;

import com.fdmgroup.CreditCardProject.model.*;
import org.springframework.stereotype.Service;


@Service
public class RewardsProfileService {

	public int calculateRewardPoints(MccCategory category, CreditCard selectedCreditCard, CreditCardTransaction transaction) {
		int rewardPoints = 0;
		if(selectedCreditCard.getRewardProfile().getValidCategories().contains(category)) {
			double conversionPercentage = selectedCreditCard.getRewardProfile().getConversionPercentage() / 100.0;
			rewardPoints = (int) (conversionPercentage * transaction.getAmount().doubleValue());
		}
		return rewardPoints;
	}
}
