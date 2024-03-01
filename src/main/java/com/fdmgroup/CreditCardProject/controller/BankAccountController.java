package com.fdmgroup.CreditCardProject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fdmgroup.CreditCardProject.model.BankAccount;
import com.fdmgroup.CreditCardProject.repository.UserRepository;
import com.fdmgroup.CreditCardProject.service.BankAccountService;

@Controller
public class BankAccountController {

	@Autowired
	private BankAccountService bankAccountService;

	@Autowired
	private UserRepository userRepository;

	@PostMapping("/bankaccount")
	public String displayBankAccountDetails(@RequestParam String selectedBankAccountNumber, Model model) {
		String bankAccountNumber = selectedBankAccountNumber;
		if (bankAccountNumber.length() != 0) {
			BankAccount bankAccount = bankAccountService.getBankAccountByBankAccountNumber(selectedBankAccountNumber);
			model.addAttribute("bankAccountBalance", bankAccount.getCurrentBalance());
			model.addAttribute("bankAccountId", bankAccount.getBankAccountId());
		} else {
			model.addAttribute("bankAccountBalance", "N/A");
		}
		return "bankaccount";
	}
}
