package com.fdmgroup.CreditCardProject.controller;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import com.fdmgroup.CreditCardProject.controller.TransactionController;

class DepositControllerTest {

	@Mock
	TransactionController transactionController;
	
	@BeforeEach
	void setUp() throws Exception {
		transactionController = new TransactionController();
	}

}
