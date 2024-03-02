package com.fdmgroup.CreditCardProject.model;

import java.math.BigDecimal;
import java.util.List;

import jakarta.persistence.*;

@Entity
@Table(name = "`Credit Card`")
public class CreditCard {

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

	@Column(name = "accountNumber", unique = true)
	private String accountNumber;

	@Column(name = "currentBalance")
	private BigDecimal currentBalance;

	@Column(name = "monthlyDueDate")
	private byte monthlyDueDate;

	@Column(name = "spendingLimit")
	private BigDecimal spendingLimit;

	@OneToMany(mappedBy = "creditCard", cascade = CascadeType.ALL)
	private List<CreditCardTransaction> transactionHistory;

	@OneToMany(mappedBy = "creditCard", cascade = CascadeType.ALL)
	private List<BankTransaction> paymentHistory;

	@ManyToOne(cascade = CascadeType.ALL)
	private RewardsProfile rewardProfile;

	public CreditCard() {
		// Generate a random 16-digit account number
		super();
	}

	public byte getMonthlyDueDate() {
		return monthlyDueDate;
	}

	public void setMonthlyDueDate(byte monthlyDueDate) {
		this.monthlyDueDate = monthlyDueDate;
	}

	public BigDecimal getSpendingLimit() {
		return spendingLimit;
	}

	public void setSpendingLimit(BigDecimal spendingLimit) {
		this.spendingLimit = spendingLimit;
	}

	public List<CreditCardTransaction> getTransactionHistory() {
		return transactionHistory;
	}

	public void setTransactionHistory(List<CreditCardTransaction> transactionHistory) {
		this.transactionHistory = transactionHistory;
	}

	public List<BankTransaction> getPaymentHistory() {
		return paymentHistory;
	}

	public void setPaymentHistory(List<BankTransaction> paymentHistory) {
		this.paymentHistory = paymentHistory;
	}

	public RewardsProfile getRewardProfile() {
		return rewardProfile;
	}

	public void setRewardProfile(RewardsProfile rewardProfile) {
		this.rewardProfile = rewardProfile;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public BigDecimal getCurrentBalance() {
		return currentBalance;
	}

	public void setCurrentBalance(BigDecimal currentBalance) {
		this.currentBalance = currentBalance;
	}

	public long getAccountId() {
		return accountId;
	}


}
