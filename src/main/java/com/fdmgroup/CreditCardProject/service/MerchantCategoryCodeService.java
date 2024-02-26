package com.fdmgroup.CreditCardProject.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fdmgroup.CreditCardProject.repository.MerchantCategoryCodeRepository;

@Service
public class MerchantCategoryCodeService {
	@Autowired
	private MerchantCategoryCodeRepository merchantCategoryCodeRepo;
}
