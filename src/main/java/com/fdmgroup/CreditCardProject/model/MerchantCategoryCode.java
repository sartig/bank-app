package com.fdmgroup.CreditCardProject.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "`Merchant Category Code`")
public class MerchantCategoryCode {
	@Id
	@Column(name = "categoryCode")
	private long categoryCode;

	@Column(name = "categoryName")
	private String categoryName;

	@Column(name = "category")
	private String category;

	public MerchantCategoryCode() {
		super();
	}

	public MerchantCategoryCode(long categoryCode, String categoryName, String category) {
		this.categoryCode = categoryCode;
		this.categoryName = categoryName;
		this.category = category;
	}

	public long getCategoryCode() {
		return categoryCode;
	}

	public void setCategoryCode(long categoryCode) {
		this.categoryCode = categoryCode;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}
}
