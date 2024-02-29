package com.fdmgroup.CreditCardProject.controller;

import com.fdmgroup.CreditCardProject.model.AuthUser;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller

public class DashboardController {

    @GetMapping("/dashboard")
    public String goToDashboard(@AuthenticationPrincipal AuthUser principal) {
        System.out.println(principal.getUsername()); //to get username of current user for future use
        return "dashboard";
    }

}
