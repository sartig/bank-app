package com.fdmgroup.CreditCardProject.controller;

import java.math.BigDecimal;

import com.fdmgroup.CreditCardProject.exception.SelfReferenceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fdmgroup.CreditCardProject.exception.BankAccountNotFoundException;
import com.fdmgroup.CreditCardProject.exception.InsufficientBalanceException;
import com.fdmgroup.CreditCardProject.model.AuthUser;
import com.fdmgroup.CreditCardProject.model.User;
import com.fdmgroup.CreditCardProject.service.BankAccountService;
import com.fdmgroup.CreditCardProject.service.BankTransactionService;
import com.fdmgroup.CreditCardProject.service.UserService;

@Controller
public class TransferController {

	@Autowired
	BankAccountService bankAccountService;

	@Autowired
	UserService userService;

	@Autowired
	BankTransactionService bankTransactionService;

	private final Logger log = LogManager.getLogger(TransferController.class);

	// temporary method to get user's 1st bank account
	// TODO: remove when bank account page is complete
	@GetMapping("/transfer")
	public String goToTransferPage(@AuthenticationPrincipal AuthUser principal, Model model) {
		User currentUser = userService.getUserByUsername(principal.getUsername());
		model.addAttribute("user", currentUser);
		String accNumber = userService.getUserByUsername(principal.getUsername()).getBankAccounts().get(0)
				.getAccountNumber();
		model.addAttribute("accountId", accNumber);
		return "transfer";
	}

	@PostMapping("/transfer")
	public String goToAccountTransfer(@AuthenticationPrincipal AuthUser principal, @RequestParam String accountId,
			Model model) {
		User currentUser = userService.getUserByUsername(principal.getUsername());
		model.addAttribute("user", currentUser);
		if (!bankAccountService.isAccountNumberValid(accountId)) {
			return "redirect:/dashboard";
		}
		try {
			if (bankAccountService.getUsernameOfAccountByAccountNumber(accountId).equals(currentUser.getUsername())) {
				model.addAttribute("accountId", accountId);
				return "transfer";
			}
		} catch (BankAccountNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "redirect:/dashboard";

	}

	@PostMapping("/transfer/confirm")
	public String handleTransferRequest(@AuthenticationPrincipal AuthUser principal, @RequestParam String accountId,
			@RequestParam String amount, @RequestParam String accountTo) {

		if(accountId.equals(accountTo)) {
			// trying to transfer to same account
			return "redirect:/dashboard";
		}

		log.info("Received transfer request from user '" + principal.getUsername() + "' for $" + amount
				+ " from account number '" + accountId + "' to account number '" + accountTo + "'");

		// make sure account belongs to logged in user
		try {
			if (!principal.getUsername().equals(bankAccountService.getUsernameOfAccountByAccountNumber(accountId))) {
				// account does not belong to user, return to dashboard
				log.error("User '" + principal.getUsername() + "' does now control account number '" + accountId + "'.");
				return "redirect:/dashboard";
			}
			if (!bankAccountService.isAccountNumberValid(accountTo)) {
				// destination account number doesn't exist, return to transfer with some
				// message
				// TODO: add error message and proper redirection
				log.error("Account number '" + accountTo + "' does not exist.");
				return "redirect:/dashboard";
			}

			BigDecimal transferAmount = new BigDecimal(amount);

			long transactionId;
			transactionId = bankAccountService.transferBetweenAccounts(accountId, accountTo, transferAmount);
			return "redirect:/transaction/receipt/" + transactionId;

		} catch (BankAccountNotFoundException e) {
			log.error("Account number '" + accountId + "' does not exist.");
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InsufficientBalanceException e) {
			// TODO Auto-generated catch block
			log.error("Account number '" + accountId + "' did not have enough funds for transfer.");
			e.printStackTrace();
		} catch (SelfReferenceException e) {
            // should never run
        }
        return "redirect:/transfer/" + accountId;

	}
}
