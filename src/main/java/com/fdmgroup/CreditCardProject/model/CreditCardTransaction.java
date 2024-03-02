package com.fdmgroup.CreditCardProject.model;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import jakarta.persistence.*;


@Entity
@Table(name = "`Credit Card Transaction`")
public class CreditCardTransaction {


	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "transactionId")
	private long transactionId;

	@ManyToOne
	@JoinColumn(name = "creditCardId")
	private CreditCard creditCard;

	@Column(name = "date")
	private String date; // Use String for date input from HTML
	public java.sql.Date getSqlDate() {
		// Convert String date to java.sql.Date
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
		LocalDateTime dateTime = LocalDateTime.parse(date, formatter);
		return java.sql.Date.valueOf(dateTime.toLocalDate());
	}
	@Column(name = "amount")
	private BigDecimal amount;

	@Column(name = "storeInfo")
	private String storeInfo;

	@ManyToOne
	@JoinColumn(name = "merchantCategoryCodeId")
	private MerchantCategoryCode merchantCategoryCode;

	@Column(name = "originalCurrencyCode")
	private String originalCurrencyCode;

	@Column(name = "originalCurrencyAmount")
	private double originalCurrencyAmount;

	public CreditCardTransaction() {
		super();
	}

	public CreditCardTransaction(CreditCard creditCard, String date, BigDecimal amount, String storeInfo,
			MerchantCategoryCode merchantCategoryCode, String originalCurrencyCode, double originalCurrencyAmount) {
		super();
		setCreditCard(creditCard);
		setDate(date);
		setAmount(amount);
		setStoreInfo(storeInfo);
		setMerchantCategoryCode(merchantCategoryCode);
		setOriginalCurrencyCode(originalCurrencyCode);
		setOriginalCurrencyAmount(originalCurrencyAmount);
	}

	public CreditCard getCreditCard() {
		return creditCard;
	}

	public void setCreditCard(CreditCard creditCard) {
		this.creditCard = creditCard;
	}

	public String getStoreInfo() {
		return storeInfo;
	}

	public void setStoreInfo(String storeInfo) {
		this.storeInfo = storeInfo;
	}

	public MerchantCategoryCode getMerchantCategoryCode() {
		return merchantCategoryCode;
	}

	public void setMerchantCategoryCode(MerchantCategoryCode merchantCategoryCode) {
		this.merchantCategoryCode = merchantCategoryCode;
	}

	public String getOriginalCurrencyCode() {
		return originalCurrencyCode;
	}

	public void setOriginalCurrencyCode(String originalCurrencyCode) {
		this.originalCurrencyCode = originalCurrencyCode;
	}

	public double getOriginalCurrencyAmount() {
		return originalCurrencyAmount;
	}

	public void setOriginalCurrencyAmount(double originalCurrencyAmount) {
		this.originalCurrencyAmount = originalCurrencyAmount;
	}

	public long getTransactionId() {
		return transactionId;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

}
