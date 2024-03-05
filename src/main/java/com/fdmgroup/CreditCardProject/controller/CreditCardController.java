package com.fdmgroup.CreditCardProject.controller;
import com.fdmgroup.CreditCardProject.model.AuthUser;
import com.fdmgroup.CreditCardProject.model.CreditCard;
import com.fdmgroup.CreditCardProject.model.CreditCardTransaction;
import com.fdmgroup.CreditCardProject.model.User;
import com.fdmgroup.CreditCardProject.service.CreditCardService;
import com.fdmgroup.CreditCardProject.service.CreditCardTransactionService;
import com.fdmgroup.CreditCardProject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class CreditCardController {
    @Autowired
    private CreditCardService creditCardService;

    @Autowired
    private UserService userService;
    @Autowired
    private CreditCardTransactionService creditCardTransactionService;

    @PostMapping("/creditcard")
    public String gotoCreditCard(@AuthenticationPrincipal AuthUser principal, @RequestParam String creditCardNumber, Model model) {
        User currentUser = userService.getUserByUsername(principal.getUsername());
        model.addAttribute("user", currentUser);
        CreditCard creditCard = creditCardService.getCardByNumber(creditCardNumber);
        model.addAttribute("creditCard", creditCard);
        List<CreditCardTransaction> transactionHistory = creditCard.getTransactionHistoryDescending();
        model.addAttribute("transactionHistory", transactionHistory);
        return "creditcard";
    }
    
    @GetMapping("/cardsinfo")
    public String goToCardinfo(@AuthenticationPrincipal AuthUser principal, Model model) {
    	User currentUser = userService.getUserByUsername(principal.getUsername());
    	model.addAttribute("user", currentUser);
    	return "cardsinfo";
    }

}
