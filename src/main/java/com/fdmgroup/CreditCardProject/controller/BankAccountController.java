package com.fdmgroup.CreditCardProject.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.fdmgroup.CreditCardProject.model.User;
import com.fdmgroup.CreditCardProject.repository.UserRepository;
import com.fdmgroup.CreditCardProject.service.BankAccountService;

import jakarta.servlet.http.HttpSession;

@Controller
public class BankAccountController {
	
	@Autowired
	private BankAccountService bankAccountService;
	
	@Autowired
	private UserRepository userRepository;
	
	@GetMapping("/bankaccount")
	public String userBankAccount(HttpSession session,Model model) {
		String username = (String) session.getAttribute("current_user");
		Optional<User> userOptional = userRepository.findByUsername(username);
		if (userOptional.isPresent()) {
			List<String> bankAccountIds = bankAccountService.getBankAccountIdsByUsername(userOptional.get().getUsername());
			model.addAttribute("bankAccountIds", bankAccountIds);	
		}
		
		return "bankaccount";
	}
	
	@GetMapping("/bankaccount/{selectedBankAccountNumber}")
	public String displayBankAccountBalance(@PathVariable String selectedBankAccountNumber, Model model) {
		String bankAccountNumber = selectedBankAccountNumber;
		if (bankAccountNumber.length() != 0) {
			BankAccount bankAccount = bankAccountService.get
			double bankAccountBalance = bankAccountService.getAccountBalanceByBankAccountNumber(bankAccountNumber);
			model.addAttribute("bankAccountBalance", bankAccountBalance);
			model.addAttribute("bankAccountId", bankAccountId);
		}else {
			model.addAttribute("bankAccountBalance", "N/A");
		}
		return "redirect:/bankaccount";
	}
}
