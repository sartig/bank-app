package com.fdmgroup.CreditCardProject.model;

import java.math.BigDecimal;
import java.sql.Date;

import jakarta.persistence.*;

/**
 * Represents a Credit Card Transaction entity. This class extends the
 * Transaction class and contains additional information such as store
 * information, merchant category code, original currency code, and original
 * currency amount.
 * 
 * @author Danny
 * @version 1.0
 * @since 2022-02-20
 */

@Entity
@Table(name = "`Credit Card Transaction`")
public class CreditCardTransaction {

	/**
	 * The unique identifier for the transaction.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "transactionId")
	private long transactionId;


	/**
	 * The credit card associated with this transaction.
	 */
	@ManyToOne
	@JoinColumn(name = "creditCardId")
	private CreditCard creditCard;

	/**
	 * The date of the transaction.
	 */
	@Column(name = "date")
	private Date date;

	/**
	 * The amount involved in the transaction.
	 */
	@Column(name = "amount")
	private BigDecimal amount;

	/**
	 * The store information related to the credit card transaction.
	 */
	@Column(name = "storeInfo")
	private String storeInfo;

	/**
<<<<<<< HEAD
     * The merchant category code associated with the credit card transaction.
     */
	@OneToOne
    @JoinColumn(name = "merchantCategoryCodeId")
	private MerchantCategoryCode merchantCategoryCode;

	/**
	 * The original currency code of the credit card transaction.
	 */
	@Column(name = "originalCurrencyCode")
	private String originalCurrencyCode;

	/**
	 * The original currency amount of the credit card transaction.
	 */
	@Column(name = "originalCurrencyAmount")
	private double originalCurrencyAmount;

	/**
	 * Default constructor for CreditCardTransaction. Creates an instance with
	 * default values.
	 */
	public CreditCardTransaction() {
		super();
	}

	/**
	 * Parameterized constructor for CreditCardTransaction.
	 *
	 * @param creditCard          The credit card associated with the transaction.
	 * @param date                   The date of the transaction.
	 * @param amount                 The amount of the transaction.
	 * @param storeInfo              The store information related to the
	 *                               transaction.
	 * @param merchantCategoryCode   The merchant category code associated with the
	 *                               transaction.
	 * @param originalCurrencyCode   The original currency code of the transaction.
	 * @param originalCurrencyAmount The original currency amount of the
	 *                               transaction.
	 */
	public CreditCardTransaction(CreditCard creditCard, Date date, BigDecimal amount, String storeInfo,
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

	/**
	 * Gets the store information of the credit card transaction.
	 * 
	 * @return The storeInfo.
	 */
	public String getStoreInfo() {
		return storeInfo;
	}

	/**
	 * Sets the store information of the credit card transaction.
	 * 
	 * @param storeInfo The store information to set.
	 */
	public void setStoreInfo(String storeInfo) {
		this.storeInfo = storeInfo;
	}

	/**
	 * Gets the merchant category code associated with the credit card transaction.
	 * 
	 * @return The merchantCategoryCode.
	 */
	public MerchantCategoryCode getMerchantCategoryCode() {
		return merchantCategoryCode;
	}

	/**
	 * Sets the merchant category code associated with the credit card transaction.
	 * 
	 * @param merchantCategoryCode The merchant category code to set.
	 */
	public void setMerchantCategoryCode(MerchantCategoryCode merchantCategoryCode) {
		this.merchantCategoryCode = merchantCategoryCode;
	}

	/**
	 * Gets the original currency code of the credit card transaction.
	 * 
	 * @return The originalCurrencyCode.
	 */
	public String getOriginalCurrencyCode() {
		return originalCurrencyCode;
	}

	/**
	 * Sets the original currency code of the credit card transaction.
	 * 
	 * @param originalCurrencyCode The original currency code to set.
	 */
	public void setOriginalCurrencyCode(String originalCurrencyCode) {
		this.originalCurrencyCode = originalCurrencyCode;
	}

	/**
	 * Gets the original currency amount of the credit card transaction.
	 * 
	 * @return The originalCurrencyAmount.
	 */
	public double getOriginalCurrencyAmount() {
		return originalCurrencyAmount;
	}

	/**
	 * Sets the original currency amount of the credit card transaction.
	 * 
	 * @param originalCurrencyAmount The original currency amount to set.
	 */
	public void setOriginalCurrencyAmount(double originalCurrencyAmount) {
		this.originalCurrencyAmount = originalCurrencyAmount;
	}

	public long getTransactionId() {
		return transactionId;
	}

	/**
	 * Gets the date of the transaction.
	 * 
	 * @return The date.
	 */
	public Date getDate() {
		return date;
	}

	/**
	 * Sets the date of the transaction.
	 * 
	 * @param date The date to set.
	 */
	public void setDate(Date date) {
		this.date = date;
	}

	/**
	 * Gets the amount involved in the transaction.
	 * 
	 * @return The amount.
	 */
	public BigDecimal getAmount() {
		return amount;
	}

	/**
	 * Sets the amount involved in the transaction.
	 * 
	 * @param amount The amount to set.
	 */
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

}
