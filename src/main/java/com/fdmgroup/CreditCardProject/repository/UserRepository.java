package com.fdmgroup.CreditCardProject.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fdmgroup.CreditCardProject.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

}
