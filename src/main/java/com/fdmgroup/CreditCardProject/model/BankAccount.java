package com.fdmgroup.CreditCardProject.model;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

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
public class BankAccount extends Account {
	
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
     * @param ownerId        The identifier of the owner of the bank account.
     * @param accountNumber  The account number of the bank account.
     * @param currentBalance The current balance of the bank account.
     */
	public BankAccount(long ownerId, String accountNumber, double currentBalance) {
		setOwnerId(ownerId);
		setAccountNumber(accountNumber);
		setCurrentBalance(currentBalance);
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
	
}
