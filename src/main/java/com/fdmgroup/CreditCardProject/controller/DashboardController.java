package com.fdmgroup.CreditCardProject.controller;

import com.fdmgroup.CreditCardProject.model.AuthUser;
import com.fdmgroup.CreditCardProject.model.User;
import com.fdmgroup.CreditCardProject.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller

public class DashboardController {

    @Autowired
    private UserService userService;

    @GetMapping("/dashboard")
    public String goToDashboard(@AuthenticationPrincipal AuthUser principal, Model model) {
        User currentUser = userService.getUser(principal.getUsername());
        model.addAttribute("user", currentUser);
        return "dashboard";
    }

}
