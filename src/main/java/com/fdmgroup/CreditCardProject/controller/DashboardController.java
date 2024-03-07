package com.fdmgroup.CreditCardProject.controller;

import com.fdmgroup.CreditCardProject.model.AuthUser;
import com.fdmgroup.CreditCardProject.model.RewardsProfile;
import com.fdmgroup.CreditCardProject.model.User;
import com.fdmgroup.CreditCardProject.repository.RewardsProfileRepository;
import com.fdmgroup.CreditCardProject.service.BankAccountService;
import com.fdmgroup.CreditCardProject.service.CreditCardService;
import com.fdmgroup.CreditCardProject.service.RewardsProfileService;
import com.fdmgroup.CreditCardProject.service.UserService;

import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
    
    @Autowired
    private CreditCardService creditCardService;
    
    @Autowired
    private RewardsProfileService rewardsProfileService;
    
    @GetMapping("/dashboard")
    public String goToDashboard(@AuthenticationPrincipal AuthUser principal, Model model) {
        User currentUser = userService.getUserByUsername(principal.getUsername());
        model.addAttribute("user", currentUser);
        List<RewardsProfile> rewardsProfiles = rewardsProfileService.findAll();
    	model.addAttribute("rewardsProfiles", rewardsProfiles);
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
    
    @PostMapping("/dashboard/createNewCreditCard")
    public String createNewCreditCardRequest(@AuthenticationPrincipal AuthUser principal, HttpServletRequest request,Model model) {
    	User user = userService.getUserByUsername(principal.getUsername());
    	model.addAttribute("user", user);
    	
    	String spendingLimit = request.getParameter("spendingLimit");
    	String rewardProfileId = request.getParameter("rewardProfileId");
    	
    	long spendingLimitValue = Long.parseLong(spendingLimit);
    	long rewardProfileIdValue = Long.parseLong(rewardProfileId);
    	
		// Create a new Credit Card for the user
		creditCardService.createCreditCardForUser(user, spendingLimitValue,0,rewardProfileIdValue);
    	return "redirect:/dashboard";
    }

}
