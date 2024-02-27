package com.fdmgroup.CreditCardProject.controller;


import com.fdmgroup.CreditCardProject.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }

    @PostMapping("/login")
    public String verifyUser(@RequestParam String username, @RequestParam String password, HttpSession session) {
        if (userService.verifyUser(username, password)) {
            session.setAttribute("current_user", username);
            return "index";
        }
        return "login";
    }

}
