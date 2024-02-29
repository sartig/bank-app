package com.fdmgroup.CreditCardProject.controller;

import com.fdmgroup.CreditCardProject.model.AuthUser;
import com.fdmgroup.CreditCardProject.model.User;
import com.fdmgroup.CreditCardProject.repository.UserRepository;
import com.fdmgroup.CreditCardProject.service.BankAccountService;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller

public class DashboardController {

	@Autowired
	private BankAccountService bankAccountService;
	
	@Autowired
	private UserRepository userRepository;
	
    @GetMapping("/dashboard")
    public String goToDashboard(@AuthenticationPrincipal AuthUser principal,Model model) {
        System.out.println(principal.getUsername()); //to get username of current user for future use
        String username = principal.getUsername();
        Optional<User> userOptional = userRepository.findByUsername(username);
		if (userOptional.isPresent()) {
			List<String> bankAccountIds = bankAccountService.getBankAccountIdsByUsername(userOptional.get().getUsername());
			model.addAttribute("bankAccountIds", bankAccountIds);	
		}
        return "dashboard";
    }

}
