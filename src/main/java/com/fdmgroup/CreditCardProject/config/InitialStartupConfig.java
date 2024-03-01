package com.fdmgroup.CreditCardProject.config;

import com.fdmgroup.CreditCardProject.io.MccImporter;
import com.fdmgroup.CreditCardProject.model.MerchantCategoryCode;
import com.fdmgroup.CreditCardProject.model.RewardsProfile;
import com.fdmgroup.CreditCardProject.repository.MerchantCategoryCodeRepository;
import com.fdmgroup.CreditCardProject.repository.RewardsProfileRepository;
import jakarta.annotation.PostConstruct;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InitialStartupConfig {

	@Autowired
	private RewardsProfileRepository rewardsProfileRepository;

	@Autowired
	private MerchantCategoryCodeRepository merchantCategoryCodeRepository;

	private MccImporter mccImporter = new MccImporter();

	@PostConstruct
	public void init() {
		initializeRewardsProfiles();
		initializeMerchantCategoryCodes();
	}

	private void initializeRewardsProfiles() {
		initializeRewardsProfile(1L, 1.80);
		initializeRewardsProfile(2L, 4);
		initializeRewardsProfile(3L, 5);
	}

	private void initializeRewardsProfile(Long id, double conversionPercentage) {
		if (rewardsProfileRepository.findById(id).isEmpty()) {
			RewardsProfile rewardsProfile = new RewardsProfile(id);
			rewardsProfile.setConversionPercentage(conversionPercentage);
			rewardsProfileRepository.save(rewardsProfile);
		}
	}

	private void initializeMerchantCategoryCodes() {
		List<MerchantCategoryCode> existing = merchantCategoryCodeRepository.findAll();
		if (existing == null || existing.isEmpty()) {
			importMerchantCategoryCodes("src/main/resources/mcc_data.json");
		}
	}

	private void importMerchantCategoryCodes(String filePath) {
		List<MerchantCategoryCode> imported = mccImporter.loadMccCodes(filePath);
		merchantCategoryCodeRepository.saveAll(imported);
	}
}