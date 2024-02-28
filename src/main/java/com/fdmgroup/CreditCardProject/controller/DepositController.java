package com.fdmgroup.CreditCardProject.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class DepositController {

	/**
	 * Goes to the Deposit page
	 * 
	 * @return name of the deposit page
	 */
	@GetMapping("/deposit/{accountId}")
	public String goToDepositPage(@PathVariable String accountId, Model model) {

		// verify user is owner of account
		model.addAttribute("accountId", accountId);
		return "deposit";
	}

	@PostMapping("/deposit/{accountId}")
	public String handleDepositRequest(@PathVariable String accountId, @RequestParam String amount,
			@RequestParam String actions, Model model) {
		if (actions.equals("withdraw")) {
			// handle withdraw later
			return "redirect:/deposit/" + accountId;
		}

		// do and validate transaction, get transaction id

		long transactionId = 12345678909515l;
		// actions (Deposit or Withdraw)
		return "redirect:/deposit/receipt/" + transactionId;
	}

	@GetMapping("/deposit/receipt/{transactionId}")
	public String goToDepositReceiptPage(@PathVariable String transactionId, Model model) {
		model.addAttribute("id", transactionId);
		Date transactionTime = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		String formattedTimestamp = sdf.format(transactionTime);
		String source = "Cash";
		String transactionType = "Deposit";
		long depositAmount = 123;
		// deposit validation and handling
		model.addAttribute("amount", depositAmount);
		model.addAttribute("id", transactionId);
		model.addAttribute("source", source);
		model.addAttribute("time", formattedTimestamp);
		model.addAttribute("type", transactionType);
		// amount
		return "depositReceipt";
	}
}
