package com.fdmgroup.CreditCardProject.controller;

import com.fdmgroup.CreditCardProject.model.AuthUser;
import com.fdmgroup.CreditCardProject.model.User;

import com.fdmgroup.CreditCardProject.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.fdmgroup.CreditCardProject.service.BankAccountService;

import java.util.List;

@Controller

public class DashboardController {

    @Autowired
    private UserService userService;

    @Autowired
    private BankAccountService bankAccountService;
  
  	@GetMapping("/dashboard")
  	public String goToDashboard(@AuthenticationPrincipal AuthUser principal,Model model) {
  		System.out.println(principal.getUsername()); //to get username of current user for future use
  		String username = principal.getUsername();
  		User user = userService.getUserByUsername(username);
  		List<String> bankAccountIds = bankAccountService.getBankAccountIdsByUsername(user.getUsername());
  		model.addAttribute("bankAccountIds", bankAccountIds);
  		return "dashboard";
  	}
}
