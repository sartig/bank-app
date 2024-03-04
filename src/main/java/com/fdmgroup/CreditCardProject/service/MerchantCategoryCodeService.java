package com.fdmgroup.CreditCardProject.service;

import com.fdmgroup.CreditCardProject.model.MerchantCategoryCode;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fdmgroup.CreditCardProject.repository.MerchantCategoryCodeRepository;

import java.util.Optional;

@Service
public class MerchantCategoryCodeService {
	@Autowired
	private MerchantCategoryCodeRepository merchantCategoryCodeRepository;

	public String getCategoryByCategoryCode(long categoryCode) {
		Optional<MerchantCategoryCode> mccOptional = merchantCategoryCodeRepository.findByCategoryCode(categoryCode);
		return mccOptional.map(MerchantCategoryCode::getCategory).orElse(null);
	}

}