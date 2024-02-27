package com.fdmgroup.CreditCardProject.controller;

import com.fdmgroup.CreditCardProject.model.AuthUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fdmgroup.CreditCardProject.model.User;
import com.fdmgroup.CreditCardProject.service.UserService;

@Controller
public class RegistrationController {

	@Autowired
	private UserService userService;

	@GetMapping("/register")
	public String showRegistrationForm(Model model) {
		model.addAttribute("user", new User());
		return "register";
	}

	@PostMapping("/register")
	public String registerUser(@RequestParam String username, @RequestParam String password, RedirectAttributes redirectAttributes) {

		if (userService.registerUser(username, password)) {
			redirectAttributes.addFlashAttribute("successMessage","Registration for " + username + " successful.");
			redirectAttributes.addFlashAttribute("successMessage2","Please Proceed to Login.");

			return "redirect:/index";
		} else {
			redirectAttributes.addFlashAttribute("unsuccessfulMessage", "Registration Error! Possible Username is already in used! Please try again.");
			return "redirect:/register";
		}
	}


}