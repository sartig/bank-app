package com.fdmgroup.CreditCardProject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DepositController {

	/**
	 * Goes to the Deposit page
	 * 
	 * @return name of the deposit page
	 */
	@GetMapping("/deposit")
	public String goToDepositPage() {
		return "deposit";
	}
}
