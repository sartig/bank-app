package com.fdmgroup.CreditCardProject.controller;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fdmgroup.CreditCardProject.exception.BankAccountNotFoundException;
import com.fdmgroup.CreditCardProject.exception.BankTransactionNotFoundException;
import com.fdmgroup.CreditCardProject.model.AuthUser;
import com.fdmgroup.CreditCardProject.model.BankTransaction;
import com.fdmgroup.CreditCardProject.model.User;
import com.fdmgroup.CreditCardProject.service.BankAccountService;
import com.fdmgroup.CreditCardProject.service.BankTransactionService;
import com.fdmgroup.CreditCardProject.service.UserService;

@Controller
public class TransactionController {

	@Autowired
	BankAccountService bankAccountService;

	@Autowired
	UserService userService;

	@Autowired
	BankTransactionService bankTransactionService;

	// temporary method to get user's 1st bank account
	// TODO: remove when bank account page is complete
	@GetMapping("/transaction")
	public String goToDepositPage(@AuthenticationPrincipal AuthUser principal, Model model) {
		User currentUser = userService.getUserByUsername(principal.getUsername());
		model.addAttribute("user", currentUser);
		String accNumber = userService.getUserByUsername(principal.getUsername()).getBankAccounts().get(0)
				.getAccountNumber();
		model.addAttribute("accountId", accNumber);
		return "transaction";
	}

	@PostMapping("/transaction")
	public String goToAccountDeposit(@AuthenticationPrincipal AuthUser principal, @RequestParam String accountId,
			Model model) {
		User currentUser = userService.getUserByUsername(principal.getUsername());
		model.addAttribute("user", currentUser);
		if (!bankAccountService.isAccountNumberValid(accountId)) {
			return "redirect:/dashboard";
		}
		try {
			if (bankAccountService.getUsernameOfAccountByAccountNumber(accountId).equals(currentUser.getUsername())) {
				model.addAttribute("accountId", accountId);
				return "transaction";
			}
		} catch (BankAccountNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "redirect:/dashboard";

	}

	@PostMapping("/transaction/confirm")
	public String handleDepositRequest(@AuthenticationPrincipal AuthUser principal, @RequestParam String accountId,
			@RequestParam String amount, @RequestParam String action) {

		if (action.equals("withdraw")) {
			// TODO: implement withdrawal
			return "redirect:/transaction/" + accountId;
		}
		
		if (action.equals("deposit")) {
			return "redirect:/transaction/" + accountId;
		}

		// make sure account belongs to logged in user
		try {
			if (!principal.getUsername().equals(bankAccountService.getUsernameOfAccountByAccountNumber(accountId))) {
				// account does not belong to user, return to dashboard
				return "redirect:/dashboard";
			}

			BigDecimal depositAmount = new BigDecimal(amount);

			long transactionId;
			transactionId = bankAccountService.depositToAccount(accountId, depositAmount);
			return "redirect:/transaction/receipt/" + transactionId;

		} catch (BankAccountNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "redirect:/transaction/" + accountId;
		}

	}

	@GetMapping("/transaction/receipt/{transactionId}")
	public String goToDepositReceiptPage(@PathVariable String transactionId, Model model) {
		try {
			BankTransaction transaction = bankTransactionService.getTransactionById(transactionId);
			Date transactionTime = transaction.getDate();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			String formattedTimestamp = sdf.format(transactionTime);
			double depositAmount = transaction.getAmount().doubleValue();
			String transactionType = "Deposit";
			String source = "Cash";
			model.addAttribute("id", transactionId);
			model.addAttribute("amount", depositAmount);
			model.addAttribute("id", transactionId);
			model.addAttribute("source", source);
			model.addAttribute("time", formattedTimestamp);
			model.addAttribute("type", transactionType);
			return "depositReceipt";
		} catch (BankTransactionNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "redirect:/dashboard";
		}
	}
}
