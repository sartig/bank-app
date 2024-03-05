package com.fdmgroup.CreditCardProject.controller;

import com.fdmgroup.CreditCardProject.model.AuthUser;
import com.fdmgroup.CreditCardProject.model.User;
import com.fdmgroup.CreditCardProject.service.BankAccountService;
import com.fdmgroup.CreditCardProject.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller

public class DashboardController {

    @Autowired
    private UserService userService;
    
    @Autowired
    private BankAccountService bankAccountService;
    

    @GetMapping("/dashboard")
    public String goToDashboard(@AuthenticationPrincipal AuthUser principal, Model model) {
        User currentUser = userService.getUserByUsername(principal.getUsername());
        model.addAttribute("user", currentUser);
        return "dashboard";
    }
    
    @PostMapping("/dashboard/createNewBankAccount")
    public String createNewBankAccountRequest(@AuthenticationPrincipal AuthUser principal, Model model) {
    	User user = userService.getUserByUsername(principal.getUsername());
    	model.addAttribute("user", user);
		// Create a new BankAccount for the user
		bankAccountService.createBankAccountForUser(user);
    	return "redirect:/dashboard";
    }

}
