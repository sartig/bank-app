package com.fdmgroup.CreditCardProject.config;

import com.fdmgroup.CreditCardProject.model.RewardsProfile;
import com.fdmgroup.CreditCardProject.repository.RewardsProfileRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InitialStartupConfig {

    @Autowired
    private RewardsProfileRepository rewardsProfileRepository;

    @PostConstruct
    public void init() {
        initializeRewardsProfiles();
    }

    private void initializeRewardsProfiles() {
        initializeRewardsProfile(1L,1.80);
        initializeRewardsProfile(2L,4);
        initializeRewardsProfile(3L,5);
    }

    private void initializeRewardsProfile(Long id, double conversionPercentage) {
        if (rewardsProfileRepository.findById(id).isEmpty()) {
            RewardsProfile rewardsProfile = new RewardsProfile(id);
            rewardsProfile.setConversionPercentage(conversionPercentage);
            rewardsProfileRepository.save(rewardsProfile);
        }
    }
}