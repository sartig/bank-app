package com.fdmgroup.CreditCardProject.model;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

/**
 * Represents a credit card account with associated transaction history,
 * payment history, and rewards profile.
 * @author Danny
 * @version 1.0
 * @since 2024-02-26
 */
@Entity
@Table(name = "`Credit Card`")
public class CreditCard extends Account{
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
	@OneToOne(mappedBy = "creditCard", cascade = CascadeType.ALL)
	private RewardsProfile rewardProfile;
	
	/**
     * Default constructor for creating a CreditCard instance.
     */
	public CreditCard() {
		super();
	}

	/**
     * Get the monthly due date for payments.
     * @return The monthly due date.
     */
	public byte getMonthlyDueDate() {
		return monthlyDueDate;
	}

	/**
     * Set the monthly due date for payments.
     * @param monthlyDueDate The monthly due date to set.
     */
	public void setMonthlyDueDate(byte monthlyDueDate) {
		this.monthlyDueDate = monthlyDueDate;
	}

	/**
     * Get the spending limit for the credit card.
     * @return The spending limit.
     */
	public long getSpendingLimit() {
		return spendingLimit;
	}

	/**
     * Set the spending limit for the credit card.
     * @param spendingLimit The spending limit to set.
     */
	public void setSpendingLimit(long spendingLimit) {
		this.spendingLimit = spendingLimit;
	}

	/**
     * Get the transaction history of the credit card.
     * @return The transaction history.
     */
	public List<CreditCardTransaction> getTransactionHistory() {
		return transactionHistory;
	}

	/**
     * Set the transaction history of the credit card.
     * @param transactionHistory The transaction history to set.
     */
	public void setTransactionHistory(List<CreditCardTransaction> transactionHistory) {
		this.transactionHistory = transactionHistory;
	}

	/**
     * Get the payment history of the credit card.
     * @return The payment history.
     */
	public List<BankTransaction> getPaymentHistory() {
		return paymentHistory;
	}

	/**
     * Set the payment history of the credit card.
     * @param paymentHistory The payment history to set.
     */
	public void setPaymentHistory(List<BankTransaction> paymentHistory) {
		this.paymentHistory = paymentHistory;
	}

	/**
     * Get the rewards profile associated with the credit card.
     * @return The rewards profile.
     */
	public RewardsProfile getRewardProfile() {
		return rewardProfile;
	}

	/**
     * Set the rewards profile associated with the credit card.
     * @param rewardProfile The rewards profile to set.
     */
	public void setRewardProfile(RewardsProfile rewardProfile) {
		this.rewardProfile = rewardProfile;
	}

}
