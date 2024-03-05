package com.fdmgroup.CreditCardProject.controller;
import com.fdmgroup.CreditCardProject.exception.BankAccountNotFoundException;
import com.fdmgroup.CreditCardProject.model.*;
import com.fdmgroup.CreditCardProject.service.BankAccountService;
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

import java.math.BigDecimal;
import java.util.List;

@Controller
public class CreditCardController {
    @Autowired
    private CreditCardService creditCardService;

    @Autowired
    private BankAccountService bankAccountService;

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

    // Temp Controller
    @PostMapping("/paybills")
    public String gotoPB(@AuthenticationPrincipal AuthUser principal, @RequestParam String creditCardNumber, Model model) throws BankAccountNotFoundException {
        User currentUser = userService.getUserByUsername(principal.getUsername());
        model.addAttribute("user", currentUser);
        CreditCard creditCard = creditCardService.getCardByNumber(creditCardNumber);



        // test data (TODO: remove after testing)
        BigDecimal amount = new BigDecimal(2000);
//        bankAccountService.payBills(userId, amount, creditCard);


        model.addAttribute("creditCard", creditCard);
        List<CreditCardTransaction> transactionHistory = creditCard.getTransactionHistoryDescending();
        model.addAttribute("transactionHistory", transactionHistory);

        return "paybills";
    }
}

