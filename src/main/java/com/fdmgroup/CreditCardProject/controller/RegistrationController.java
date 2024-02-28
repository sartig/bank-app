package com.fdmgroup.CreditCardProject.controller;

import com.fdmgroup.CreditCardProject.model.BankAccount;
import com.fdmgroup.CreditCardProject.model.CreditCard;
import com.fdmgroup.CreditCardProject.model.RewardsProfile;
import com.fdmgroup.CreditCardProject.repository.BankAccountRepository;
import com.fdmgroup.CreditCardProject.repository.CreditCardRepository;
import com.fdmgroup.CreditCardProject.repository.RewardsProfileRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fdmgroup.CreditCardProject.model.User;
import com.fdmgroup.CreditCardProject.service.UserService;

@Controller
public class RegistrationController {

	@Autowired
	private UserService userService;
	@Autowired
	private BankAccountRepository bankAccountRepository;
	@Autowired
	private CreditCardRepository creditCardRepository;
	@Autowired
	private RewardsProfileRepository rewardsProfileRepository;

	@GetMapping("/register")
	public String showRegistrationForm(Model model) {
		model.addAttribute("user", new User());
		return "register";
	}

	@PostMapping("/register")
	public String registerUser(@RequestParam String username, @RequestParam String password, @RequestParam String confirmPassword, RedirectAttributes redirectAttributes) {

		if (!password.equals(confirmPassword)) {
			redirectAttributes.addFlashAttribute("unsuccessfulMessage", "Passwords do not match. Please try again.");
			return "redirect:/register";
		}

		if (userService.registerUser(username, password)) {
			User user = userService.getUserByUsername(username);

			// Create a new BankAccount for the user
			BankAccount bankAccount = new BankAccount();
			bankAccount.setUser(user);
			bankAccountRepository.save(bankAccount);

			RewardsProfile rewardsProfile = new RewardsProfile();
			rewardsProfile.setRewardProfileId(1);
			rewardsProfileRepository.save(rewardsProfile);

// Retrieve the managed RewardsProfile instance from the database
			RewardsProfile managedRewardsProfile = rewardsProfileRepository.findById(1L)
					.orElseThrow(() -> new EntityNotFoundException("RewardsProfile not found"));

// Create a new CreditCardAccount for the user
			CreditCard creditCard = new CreditCard();
			creditCard.setUser(user);
			creditCard.setMonthlyDueDate((byte) 1);
			creditCard.setSpendingLimit(500);
			creditCard.setRewardProfile(managedRewardsProfile); // Use the managed instance
			creditCardRepository.save(creditCard);

			redirectAttributes.addFlashAttribute("successMessage", "Registration for " + username + " successful.");
			redirectAttributes.addFlashAttribute("successMessage2", "Please Proceed to Login.");

			return "redirect:/index";
		} else {
			redirectAttributes.addFlashAttribute("unsuccessfulMessage", "Registration Error! Possible Username is already in use! Please try again.");
			return "redirect:/register";
		}
	}


}