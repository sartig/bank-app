package com.fdmgroup.CreditCardProject.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fdmgroup.CreditCardProject.model.BankAccount;
import com.fdmgroup.CreditCardProject.model.User;

@Repository
public interface BankAccountRepository extends JpaRepository<BankAccount, Long> {

	public List<BankAccount> findByUser(User user);
	
	public Optional<BankAccount> findByBankAccountId(long bankAccountId);
}
