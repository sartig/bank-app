package com.fdmgroup.CreditCardProject.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fdmgroup.CreditCardProject.model.BankAccount;

@Repository
public interface BankAccountRepository extends JpaRepository<BankAccount, Long> {

	public List<BankAccount> findByUsername(String username);
	
	public Optional<BankAccount> findByBankAccountId(long bankAccountId);
}
