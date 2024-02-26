package com.fdmgroup.CreditCardProject.model;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

/**
 * Represents a user in the system.
 * This class contains information about the user, including their username, password, and associated bank accounts.
 * @author Danny
 * @version 1.0
 * @since 2022-02-20
 */

@Entity
@Table(name = "`User`")
public class User {
	/**
     * The unique identifier for the user.
     */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "userId")
	private long userId;
	
	/**
     * The username of the user.
     */
	@Column(name = "username")
	private String username;
	
	/**
     * The password of the user.
     */
	@Column(name = "password")
	private String password;
	
	/**
     * The list of bank accounts associated with this user.
     */
	@OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
	@Column(name = "bankAccount")
	private List<BankAccount> bankAccounts;
	
	/**
     * Default constructor for User.
     * Creates an instance with default values.
     */
	public User() {
		super();
	}
	
	/**
     * Parameterized constructor for User.
     * @param username The username of the user.
     * @param password The password of the user.
     */
	public User(String username, String password) {
		setUsername(username);
		setPassword(password);
	}

	 /**
     * Gets the username of the user.
     * @return The username.
     */
	public String getUsername() {
		return username;
	}

	 /**
     * Sets the username of the user.
     * @param username The username to set.
     */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
     * Gets the password of the user.
     * @return The password.
     */
	public String getPassword() {
		return password;
	}

	/**
     * Sets the password of the user.
     * @param password The password to set.
     */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
     * Gets the list of bank accounts associated with this user.
     * @return The list of bank accounts.
     */
	public List<BankAccount> getBankAccounts() {
		return bankAccounts;
	}

	/**
     * Sets the list of bank accounts associated with this user.
     * @param bankAccounts The list of bank accounts to set.
     */
	public void setBankAccounts(List<BankAccount> bankAccounts) {
		this.bankAccounts = bankAccounts;
	}

	/**
     * Gets the unique identifier for the user.
     * @return The userId.
     */
	public long getUserId() {
		return userId;
	}

}
