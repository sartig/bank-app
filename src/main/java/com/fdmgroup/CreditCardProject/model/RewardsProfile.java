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
	/**
     * The unique identifier for the rewards profile.
     */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "rewardProfileId")
	private long rewardProfileId;
	
	/**
     * The conversion percentage used to calculate rewards.
     */
	@Column(name = "conversionPercentage")
	private double conversionPercentage;
	
    /**
     * Default constructor for creating a RewardsProfile instance.
     */
	public RewardsProfile() {
		super();
	}

	/**
     * Get the conversion percentage for rewards.
     * @return The conversion percentage.
     */
	public double getConversionPercentage() {
		return conversionPercentage;
	}

	/**
     * Set the conversion percentage for rewards.
     * @param conversionPercentage The conversion percentage to set.
     */
	public void setConversionPercentage(double conversionPercentage) {
		this.conversionPercentage = conversionPercentage;
	}


	/**
     * Get the unique identifier for the rewards profile.
     * @return The reward profile identifier.
     */
	public long getRewardProfileId() {
		return rewardProfileId;
	}
	
}
