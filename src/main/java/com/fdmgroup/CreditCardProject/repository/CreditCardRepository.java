package com.fdmgroup.CreditCardProject.repository;

import com.fdmgroup.CreditCardProject.model.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fdmgroup.CreditCardProject.model.CreditCard;

@Repository
public interface CreditCardRepository extends JpaRepository<CreditCard, Long> {

    CreditCard findByAccountNumber(String accountNumber);
}
