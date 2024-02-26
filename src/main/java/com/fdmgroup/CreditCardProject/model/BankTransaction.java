package com.fdmgroup.CreditCardProject.model;

import java.sql.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;


/**
 * Represents a bank transaction, extending the base class Transaction.
 * This class is used to model transactions between bank accounts.
 *
 * @author Danny
 * @version 1.0
 * @since 2022-02-20
 */

@Entity
@Table(name = "`BankTransaction`")
public class BankTransaction extends Transaction{
	
	/**
     * The identifier of the account to which the transaction is made.
     */
	@Column(name = "accountToId")
	private long AccountToId;
	
	/**
     * The bank account associated with this transaction.
     */
	@ManyToOne
	@JoinColumn(name = "accountId")
	private BankAccount bankAccount;
	
	/**
     * Default constructor for BankTransaction.
     * Creates an instance with default values.
     */
	public BankTransaction() {
		super();
	}
	
	/**
     * Parameterized constructor for BankTransaction.
     *
     * @param accountFromId The identifier of the account from which the transaction is made.
     * @param date          The date of the transaction.
     * @param amount        The amount involved in the transaction.
     * @param accountToId   The identifier of the account to which the transaction is made.
     */
	public BankTransaction(long accountFromId, Date date, double amount, long accountToId) {
		setAccountFromId(accountFromId);
		setDate(date);
		setAmount(amount);
		setAccountToId(accountToId);
	}
	
	 /**
     * Gets the identifier of the account to which the transaction is made.
     * @return The accountToId.
     */
	public long getAccountToId() {
		return AccountToId;
	}

	/**
     * Sets the identifier of the account to which the transaction is made.
     * @param accountToId The accountToId to set.
     */
	public void setAccountToId(long accountToId) {
		AccountToId = accountToId;
	}

}
