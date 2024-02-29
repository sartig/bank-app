package com.fdmgroup.CreditCardProject.model;

import java.util.List;

import jakarta.persistence.*;

/**
 * Represents a bank account entity, extending the base class Account.
 * This class is used to model bank accounts and their transaction history.
 *
 * @author Danny
 * @version 1.0
 * @since 2022-02-20
 */

@Entity
@Table(name = "`BankAccount`")
public class BankAccount {

	/**
     * The unique identifier for the account.
     */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "bankAccountId")
	private long accountId;
	
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
     * The transaction history associated with this bank account.
     */
	@OneToMany(mappedBy = "bankAccount",cascade = CascadeType.ALL)
	private List<BankTransaction> transactionHistory;
	
	/**
     * The user associated with this bank account.
     */
	@ManyToOne
    @JoinColumn(name = "userId")
    private User user;
	
	/**
     * Default constructor for BankAccount.
     * Creates an instance with default values.
     */
	public BankAccount() {
		super();
	}

	/**
     * Parameterized constructor for BankAccount.
     * @param accountNumber  The account number of the bank account.
     * @param currentBalance The current balance of the bank account.
     */
	public BankAccount(User user, String accountNumber, double currentBalance) {
		setUser(user);
		setAccountNumber(accountNumber);
		setCurrentBalance(currentBalance);
	}
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	/**
     * Gets the transaction history associated with this bank account.
     * @return The list of bank transactions in the transaction history.
     */
	public List<BankTransaction> getTransactionHistory() {
		return transactionHistory;
	}

	/**
     * Sets the transaction history associated with this bank account.
     * @param transactionHistory The list of bank transactions to set as the transaction history.
     */
	public void setTransactionHistory(List<BankTransaction> transactionHistory) {
		this.transactionHistory = transactionHistory;
	}
	
	public void addTransactionHistory(BankTransaction transaction) {
		getTransactionHistory().add(transaction);
	}

	/**
     * Gets the account number associated with this account.
     * @return The accountNumber.
     */
	public String getAccountNumber() {
		return accountNumber;
	}

	/**
     * Sets the account number associated with this account.
     * @param accountNumber The accountNumber to set.
     */
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	/**
     * Gets the current balance of the account.
     * @return The currentBalance.
     */
	public double getCurrentBalance() {
		return currentBalance;
	}

	/**
     * Sets the current balance of the account.
     * @param currentBalance The currentBalance to set.
     */
	public void setCurrentBalance(double currentBalance) {
		this.currentBalance = currentBalance;
	}

	/**
     * Gets the unique identifier for the account.
     * @return The accountId.
     */
	public long getAccountId() {
		return accountId;
	}


}
