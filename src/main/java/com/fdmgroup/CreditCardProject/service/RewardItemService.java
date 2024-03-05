package com.fdmgroup.CreditCardProject.service;

import com.fdmgroup.CreditCardProject.exception.ItemNotFoundException;
import com.fdmgroup.CreditCardProject.model.RewardItem;
import com.fdmgroup.CreditCardProject.repository.RewardItemRepository;
import com.fdmgroup.CreditCardProject.repository.RewardTransactionRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RewardItemService {

	@Autowired
	RewardItemRepository rewardItemRepository;
	
	@Autowired
	RewardTransactionRepository rewardTransactionRepository;

	public List<RewardItem> getAllItems() {
		return rewardItemRepository.findAll();
	}

	public RewardItem getItemById(long id) throws ItemNotFoundException {
		return rewardItemRepository.findById(id).orElseThrow(ItemNotFoundException::new);
	}

}
