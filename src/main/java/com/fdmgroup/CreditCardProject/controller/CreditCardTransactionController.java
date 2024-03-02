package com.fdmgroup.CreditCardProject.controller;

import com.fdmgroup.CreditCardProject.exception.InsufficientFundsException;
import com.fdmgroup.CreditCardProject.model.AuthUser;
import com.fdmgroup.CreditCardProject.model.CreditCard;
import com.fdmgroup.CreditCardProject.model.CreditCardTransaction;
import com.fdmgroup.CreditCardProject.model.User;
import com.fdmgroup.CreditCardProject.service.CreditCardService;
import com.fdmgroup.CreditCardProject.service.CreditCardTransactionService;
import com.fdmgroup.CreditCardProject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.util.ArrayList;

@Controller
public class CreditCardTransactionController {


    @Autowired
    private UserService userService;
    @Autowired
    private CreditCardTransactionService creditCardTransactionService;

    @GetMapping("/creditcard-add")
    public String showCreditCardForm(Model model) {
        // Get the authenticated user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        AuthUser principal = (AuthUser) authentication.getPrincipal();
        User currentUser = userService.getUserByUsername(principal.getUsername());

        // Ensure that the user has credit cards
        if (currentUser.getCreditCards() == null) {
            currentUser.setCreditCards(new ArrayList<>());
        }

        model.addAttribute("user", currentUser);
        model.addAttribute("transaction", new CreditCardTransaction());
        return "creditcard-add";
    }

    @PostMapping("/creditcard-add")
    public String processCreditCardForm(@ModelAttribute("transaction") CreditCardTransaction transaction,
                                        @RequestParam("creditCard") String creditCardAccountNumber,
                                        RedirectAttributes redirectAttributes) {
        // Validate the transaction
        if (transaction.getOriginalCurrencyAmount() <= 0) {
            // Amount must be greater than zero
            return "redirect:/creditcard-add?error=invalid_amount";
        }

        // Get the authenticated user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        AuthUser principal = (AuthUser) authentication.getPrincipal();
        User currentUser = userService.getUserByUsername(principal.getUsername());

        // Find the selected credit card from the user's list of credit cards
        CreditCard selectedCreditCard = currentUser.getCreditCards().stream()
                .filter(card -> card.getAccountNumber().equals(creditCardAccountNumber))
                .findFirst()
                .orElse(null);

        if (selectedCreditCard == null) {
            // Credit card not found
            return "redirect:/creditcard-add?error=credit_card_not_found";
        }

        // Placeholder method for currency conversion rate
        double conversionRate = creditCardTransactionService.getCurrencyConversionRate(transaction.getOriginalCurrencyCode());

        // Calculate the amount based on the original currency amount and conversion rate
        BigDecimal amount = BigDecimal.valueOf(transaction.getOriginalCurrencyAmount() * conversionRate);
        transaction.setAmount(amount);

        // Process the transaction
        try {
            creditCardTransactionService.processTransaction(currentUser, selectedCreditCard, transaction);
            redirectAttributes.addFlashAttribute("message", "Amount added successfully: " + transaction.getAmount() + transaction.getOriginalCurrencyCode());
        } catch (InsufficientFundsException e) {
            // Handle insufficient funds
            return "redirect:/creditcard-add?error=insufficient_funds";
        }

        return "redirect:/creditcard-add";
    }

}
