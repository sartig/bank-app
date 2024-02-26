package com.fdmgroup.CreditCardProject.controller;


import com.fdmgroup.CreditCardProject.service.UserService;
import com.fdmgroup.CreditCardProject.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {
    @Autowired
    private UserService userService;
    
    @GetMapping("/")
    public String home() {
        return "index";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
    	System.out.println("RUNNING IN GETMAP");
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser( User user) {
        if(userService.registerUser(user)) {
            return "redirect:/login";
        } else {
            return "redirect:/register";
        }
    }

}
