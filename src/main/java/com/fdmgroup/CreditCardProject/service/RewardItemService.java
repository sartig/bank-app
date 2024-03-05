package com.fdmgroup.CreditCardProject.service;

import com.fdmgroup.CreditCardProject.repository.RewardItemRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RewardItemService {

	@Autowired
	RewardItemRepository rewardItemRepository;

}
