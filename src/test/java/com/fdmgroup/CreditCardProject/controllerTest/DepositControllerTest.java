package com.fdmgroup.CreditCardProject.controllerTest;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import com.fdmgroup.CreditCardProject.controller.DepositController;

class DepositControllerTest {

	@Mock
	DepositController depositController;
	
	@BeforeEach
	void setUp() throws Exception {
		depositController = new DepositController();
	}

//	@Test
//	void testGoToDepositPage_returnsCorrectString() {
//		String result = depositController.goToDepositPage();
//		assertEquals("deposit", result);
//	}
//
//	@Test
//	void testGoToDepositPage_doesNotReturnIncorrectString() {
//		String result = depositController.goToDepositPage();
//		assertNotEquals("dashboard", result);
//	}

}
