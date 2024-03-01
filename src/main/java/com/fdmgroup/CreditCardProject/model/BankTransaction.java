package com.fdmgroup.CreditCardProject.model;

import java.util.Date;

import jakarta.persistence.*;

/**
 * Represents a bank transaction, extending the base class Transaction. This
 * class is used to model transactions between bank accounts.
 *
 * @author Danny
 * @version 1.0
 * @since 2022-02-20
 */

@Entity
@Table(name = "`BankTransaction`")
public class BankTransaction {

	/**
	 * The unique identifier for the transaction.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "transactionId")
	private long transactionId;

	/**
	 * The identifier of the account from which the transaction is made.
	 */
	@Column(name = "accountFromId")
	private long accountFromId;

	/**
	 * The date of the transaction.
	 */
	@Column(name = "date")
	private Date date;

	/**
	 * The amount involved in the transaction.
	 */
	@Column(name = "amount")
	private double amount;

	/**
	 * The identifier of the account to which the transaction is made.
	 */
	@Column(name = "accountToId")
	private long AccountToId;

	/**
	 * The bank account associated with this transaction.
	 */
	@ManyToOne
	@JoinColumn(name = "bankAccount")
	private BankAccount bankAccount;

	/**
	 * The credit card associated with this bank transaction.
	 */
	@ManyToOne
	@JoinColumn(name = "creditCardId")
	private CreditCard creditCard;

	public BankAccount getBankAccount() {
		return bankAccount;
	}

	public void setBankAccount(BankAccount bankAccount) {
		this.bankAccount = bankAccount;
	}

	public CreditCard getCreditCard() {
		return creditCard;
	}

	public void setCreditCard(CreditCard creditCard) {
		this.creditCard = creditCard;
	}

	/**
	 * Default constructor for BankTransaction. Creates an instance with default
	 * values.
	 */
	public BankTransaction() {
		super();
	}

	/**
	 * Parameterized constructor for BankTransaction transfer.
	 *
	 * @param accountFromId The identifier of the account from which the transaction
	 *                      is made.
	 * @param date          The date of the transaction.
	 * @param amount        The amount involved in the transaction.
	 * @param accountToId   The identifier of the account to which the transaction
	 *                      is made.
	 */
	public BankTransaction(long accountFromId, double amount, long accountToId) {
		this();
		setAccountFromId(accountFromId);
		setDate(new Date());
		setAmount(amount);
		setAccountToId(accountToId);
	}
	
	/**
	 * Parameterized constructor for BankTransaction deposit.
	 *
	 * @param accountFromId The identifier of the account from which the transaction
	 *                      is made.
	 * @param date          The date of the transaction.
	 * @param amount        The amount involved in the transaction.
	 * @param accountToId   The identifier of the account to which the transaction
	 *                      is made.
	 */
	public BankTransaction(double amount, long accountToId) {
		this();
		setDate(new Date());
		setAmount(amount);
		setAccountToId(accountToId);
		setAccountFromId(-1);
	}
	
	/**
	 * Parameterized constructor for BankTransaction withdrawal.
	 *
	 * @param accountFromId The identifier of the account from which the transaction
	 *                      is made.
	 * @param date          The date of the transaction.
	 * @param amount        The amount involved in the transaction.
	 * @param accountToId   The identifier of the account to which the transaction
	 *                      is made.
	 */
	public BankTransaction(long accountFromId, double amount) {
		this();
		setAccountFromId(accountFromId);
		setDate(new Date());
		setAmount(amount);
		setAccountToId(-1);
	}
	
	

	/**
	 * Gets the identifier of the account to which the transaction is made.
	 * 
	 * @return The accountToId.
	 */
	public long getAccountToId() {
		return AccountToId;
	}

	/**
	 * Sets the identifier of the account to which the transaction is made.
	 * 
	 * @param accountToId The accountToId to set.
	 */
	public void setAccountToId(long accountToId) {
		AccountToId = accountToId;
	}

	public long getTransactionId() {
		return transactionId;
	}

	/**
	 * Gets the identifier of the account from which the transaction is made.
	 * 
	 * @return The accountFromId.
	 */
	public long getAccountFromId() {
		return accountFromId;
	}

	/**
	 * Sets the identifier of the account from which the transaction is made.
	 * 
	 * @param accountFromId The accountFromId to set.
	 */
	public void setAccountFromId(long accountFromId) {
		this.accountFromId = accountFromId;
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
	public double getAmount() {
		return amount;
	}

	/**
	 * Sets the amount involved in the transaction.
	 * 
	 * @param amount The amount to set.
	 */
	public void setAmount(double amount) {
		this.amount = amount;
	}
	
	public BankTransactionType getType() {
		// from + -1 for to = withdrawal
		// to = -1 for from = deposit
		// both = transfer
		// neither = invalid
		if (getAccountFromId() < 0) {
			if (getAccountToId() < 0) {
				return BankTransactionType.INVALID;
			}
			return BankTransactionType.WITHDRAWAL;
		}
		if (getAccountToId() < 0) {
			return BankTransactionType.DEPOSIT;
		}
		return BankTransactionType.INVALID;
	}

}
