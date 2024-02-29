package com.fdmgroup.CreditCardProject.model;

import java.util.List;
import java.util.Random;

import jakarta.persistence.*;

/**
 * Represents a credit card account with associated transaction history, payment
 * history, and rewards profile.
 * 
 * @author Danny
 * @version 1.0
 * @since 2024-02-26
 */
@Entity
@Table(name = "`Credit Card`")
public class CreditCard {

	/**
	 * The unique identifier for the account.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "creditCardId")
	private long accountId;

	@ManyToOne
	@JoinColumn(name = "userId")
	private User user;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}


	/**
	 * The account number associated with this account.
	 */
	@Column(name = "accountNumber", unique = true)
	private String accountNumber;

	/**
	 * The current balance of the account.
	 */
	@Column(name = "currentBalance")
	private double currentBalance;

	/**
	 * The due date for monthly payments.
	 */
	@Column(name = "monthlyDueDate")
	private byte monthlyDueDate;

	/**
	 * The spending limit for the credit card.
	 */
	@Column(name = "spendingLimit")
	private long spendingLimit;

	/**
	 * The transaction history of the credit card.
	 */
	@OneToMany(mappedBy = "creditCard", cascade = CascadeType.ALL)
	private List<CreditCardTransaction> transactionHistory;

	/**
	 * The payment history of the credit card.
	 */
	@OneToMany(mappedBy = "creditCard", cascade = CascadeType.ALL)
	private List<BankTransaction> paymentHistory;

	/**
	 * The rewards profile associated with the credit card.
	 */
	@ManyToOne(cascade = CascadeType.ALL)
	private RewardsProfile rewardProfile;

	/**
	 * Default constructor for creating a CreditCard instance.
	 */

	public CreditCard() {
		// Generate a random 16-digit account number
		super();
	}

	/**
	 * Get the monthly due date for payments.
	 * 
	 * @return The monthly due date.
	 */
	public byte getMonthlyDueDate() {
		return monthlyDueDate;
	}

	/**
	 * Set the monthly due date for payments.
	 * 
	 * @param monthlyDueDate The monthly due date to set.
	 */
	public void setMonthlyDueDate(byte monthlyDueDate) {
		this.monthlyDueDate = monthlyDueDate;
	}

	/**
	 * Get the spending limit for the credit card.
	 * 
	 * @return The spending limit.
	 */
	public long getSpendingLimit() {
		return spendingLimit;
	}

	/**
	 * Set the spending limit for the credit card.
	 * 
	 * @param spendingLimit The spending limit to set.
	 */
	public void setSpendingLimit(long spendingLimit) {
		this.spendingLimit = spendingLimit;
	}

	/**
	 * Get the transaction history of the credit card.
	 * 
	 * @return The transaction history.
	 */
	public List<CreditCardTransaction> getTransactionHistory() {
		return transactionHistory;
	}

	/**
	 * Set the transaction history of the credit card.
	 * 
	 * @param transactionHistory The transaction history to set.
	 */
	public void setTransactionHistory(List<CreditCardTransaction> transactionHistory) {
		this.transactionHistory = transactionHistory;
	}

	/**
	 * Get the payment history of the credit card.
	 * 
	 * @return The payment history.
	 */
	public List<BankTransaction> getPaymentHistory() {
		return paymentHistory;
	}

	/**
	 * Set the payment history of the credit card.
	 * 
	 * @param paymentHistory The payment history to set.
	 */
	public void setPaymentHistory(List<BankTransaction> paymentHistory) {
		this.paymentHistory = paymentHistory;
	}

	/**
	 * Get the rewards profile associated with the credit card.
	 * 
	 * @return The rewards profile.
	 */
	public RewardsProfile getRewardProfile() {
		return rewardProfile;
	}

	/**
	 * Set the rewards profile associated with the credit card.
	 * 
	 * @param rewardProfile The rewards profile to set.
	 */
	public void setRewardProfile(RewardsProfile rewardProfile) {
		this.rewardProfile = rewardProfile;
	}



	/**
	 * Gets the account number associated with this account.
	 * 
	 * @return The accountNumber.
	 */
	public String getAccountNumber() {
		return accountNumber;
	}

	/**
	 * Sets the account number associated with this account.
	 * 
	 * @param accountNumber The accountNumber to set.
	 */
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	/**
	 * Gets the current balance of the account.
	 * 
	 * @return The currentBalance.
	 */
	public double getCurrentBalance() {
		return currentBalance;
	}

	/**
	 * Sets the current balance of the account.
	 * 
	 * @param currentBalance The currentBalance to set.
	 */
	public void setCurrentBalance(double currentBalance) {
		this.currentBalance = currentBalance;
	}

	/**
	 * Gets the unique identifier for the account.
	 * 
	 * @return The accountId.
	 */
	public long getAccountId() {
		return accountId;
	}


}
