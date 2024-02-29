package com.fdmgroup.CreditCardProject.model;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

/**
 * Represents a rewards profile associated with a credit card.
 * It contains information about the conversion percentage and
 * the list of valid merchant category codes for rewards.
 * @author Danny
 * @version 1.0
 * @since 2024-02-26
 */

@Entity
@Table(name = "`Reward Profile`")
public class RewardsProfile {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "rewardProfileId", unique = true)
	private Long rewardProfileId;


	@Column(name = "conversionPercentage")
	private double conversionPercentage;

    @OneToMany(cascade = CascadeType.ALL)
	private List<MerchantCategoryCode> validCategories;

	public double getConversionPercentage() {
		return conversionPercentage;
	}

	public void setConversionPercentage(double conversionPercentage) {
		this.conversionPercentage = conversionPercentage;
	}

	public List<MerchantCategoryCode> getValidCategories() {
		return validCategories;
	}

	public void setValidCategories(List<MerchantCategoryCode> validCategories) {
		this.validCategories = validCategories;
	}

	public RewardsProfile() {
	}

	public RewardsProfile(long rewardProfileId) {
		this.rewardProfileId = rewardProfileId;


	}

	public long getRewardProfileId() {
		return rewardProfileId;
	}


}
