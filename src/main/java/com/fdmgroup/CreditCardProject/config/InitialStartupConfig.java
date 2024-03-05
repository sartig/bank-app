package com.fdmgroup.CreditCardProject.config;

import com.fdmgroup.CreditCardProject.io.MccImporter;
import com.fdmgroup.CreditCardProject.model.MccCategory;
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
		initializeRewardsProfile(1L, 7, "Basic", List.of(MccCategory.MISC_STORES,MccCategory.TRANSPORTATION));
		// travel card
		initializeRewardsProfile(2L, 10, "Travel Pro", List.of(MccCategory.AIRLINES, MccCategory.CAR_RENT, MccCategory.HOTELS));
		// shopping card
		initializeRewardsProfile(3L, 15, "Star Shopper", List.of(MccCategory.CLOTHING, MccCategory.RETAIL_OUTLETS));
	}

	private void initializeRewardsProfile(Long id, double conversionPercentage, String name,
			List<MccCategory> categories) {
		if (rewardsProfileRepository.findById(id).isEmpty()) {
			RewardsProfile rewardsProfile = new RewardsProfile(id, name);
			rewardsProfile.setConversionPercentage(conversionPercentage);
			if (categories != null) {
				rewardsProfile.setValidCategories(categories);
			}
			rewardsProfileRepository.save(rewardsProfile);
		}
	}

	private void initializeMerchantCategoryCodes() {
		List<MerchantCategoryCode> existing = merchantCategoryCodeRepository.findAll();
		if (existing.isEmpty()) {
			importMerchantCategoryCodes("src/main/resources/mcc_data.json");
		}
	}

	private void importMerchantCategoryCodes(String filePath) {
		List<MerchantCategoryCode> imported = mccImporter.loadMccCodes(filePath);
		merchantCategoryCodeRepository.saveAll(imported);
	}
}