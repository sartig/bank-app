package com.fdmgroup.CreditCardProject.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import com.fdmgroup.CreditCardProject.service.BankAccountService;

import jakarta.servlet.http.HttpSession;

@Controller
public class BankAccountController {
	
	@Autowired
	private BankAccountService bankAccountService;
	
	@GetMapping("/bankaccount")
	public String userBankAccount(HttpSession session,Model model) {
		String username = (String) session.getAttribute("current_user");
		List<Long> bankAccountIds = bankAccountService.getBankAccountIdByUsername(username);
		model.addAttribute("bankAccountIds", bankAccountIds);
		return "bankaccount";
	}
	
	@GetMapping("/bankaccount/{selectedBankAccountId}")
	public String displayBankAccountBalance(@PathVariable long selectedBankAccountId, Model model) {
		if (selectedBankAccountId != 0) {
			double bankAccountBalance = bankAccountService.getAccountBalanceByBankAccountId(selectedBankAccountId);
			model.addAttribute("bankAccountBalance", bankAccountBalance);	
		}else {
			model.addAttribute("bankAccountBalance", "N/A");
		}
		return "redirect:/bankaccount";
	}
}
