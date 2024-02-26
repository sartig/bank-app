package com.fdmgroup.CreditCardProject.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

/**
 * Represents a Merchant Category Code (MCC) entity.
 * This class contains information about a specific MCC, including its unique identifier, category code, and category name.
 * @author Danny
 * @version 1.0
 * @since 2022-02-20
 */

@Entity
@Table(name = "`Merchant Category Code`")
public class MerchantCategoryCode {
	/**
     * The unique identifier for the merchant category code.
     */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "merchantCategoryCodeId")
	private long merchantCategoryCodeId;
	
	/**
     * The category code of the Merchant Category Code.
     */
	@Column(name = "categoryCode")
	private short categoryCode;
	
	/**
     * The category name of the Merchant Category Code.
     */
	@Column(name = "categoryName")
	private String categoryName;
	
	/**
     * The reward profile associated with the merchant category code.
     */
    @ManyToOne
    @JoinColumn(name = "rewardsProfileId")
    private RewardsProfile rewardsProfile;
	
	/**
     * Default constructor for MerchantCategoryCode.
     * Creates an instance with default values.
     */
	public MerchantCategoryCode() {
		super();
	}
	
	/**
     * Parameterized constructor for MerchantCategoryCode.
     * @param categoryCode  The category code of the Merchant Category Code.
     * @param categoryName  The category name of the Merchant Category Code.
     */
	public MerchantCategoryCode(short categoryCode, String categoryName) {
		setCategoryCode(categoryCode);
		setCategoryName(categoryName);
	}

	/**
     * Gets the category code of the Merchant Category Code.
     * @return The category code.
     */
	public short getCategoryCode() {
		return categoryCode;
	}

	 /**
     * Sets the category code of the Merchant Category Code.
     * @param categoryCode The category code to set.
     */
	public void setCategoryCode(short categoryCode) {
		this.categoryCode = categoryCode;
	}

	/**
     * Gets the category name of the Merchant Category Code.
     * @return The category name.
     */
	public String getCategoryName() {
		return categoryName;
	}

	/**
     * Sets the category name of the Merchant Category Code.
     * @param categoryName The category name to set.
     */
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	/**
     * Gets the unique identifier for the Merchant Category Code.
     * @return The merchantCategoryCodeId.
     */
	public long getMerchantCategoryCodeId() {
		return merchantCategoryCodeId;
	}

}
