package com.fdmgroup.CreditCardProject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller

public class DashboardController {

    @GetMapping("/dashboard")
    public String goToIndex() {
        return "dashboard";
    }

}
