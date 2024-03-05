package com.fdmgroup.CreditCardProject.controller;
import com.fdmgroup.CreditCardProject.config.SecurityConfig;
import com.fdmgroup.CreditCardProject.exception.BankAccountNotFoundException;
import com.fdmgroup.CreditCardProject.model.*;
import com.fdmgroup.CreditCardProject.service.BankAccountService;
import com.fdmgroup.CreditCardProject.service.CreditCardService;
import com.fdmgroup.CreditCardProject.service.CreditCardTransactionService;
import com.fdmgroup.CreditCardProject.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.util.List;

@Controller
public class CreditCardController {
    @Autowired
    private CreditCardService creditCardService;


    Logger log = LoggerFactory.getLogger(CreditCardController.class);

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
    @GetMapping("/creditcard")
    public String returntoCredCard(@AuthenticationPrincipal AuthUser principal, @RequestParam String creditCardNumber, Model model) {
        User currentUser = userService.getUserByUsername(principal.getUsername());
        model.addAttribute("user", currentUser);
        model.addAttribute("creditCardNumber", creditCardNumber);
        CreditCard creditCard = creditCardService.getCardByNumber(creditCardNumber);
        model.addAttribute("creditCard", creditCard);
        List<CreditCardTransaction> transactionHistory = creditCard.getTransactionHistoryDescending();
        model.addAttribute("transactionHistory", transactionHistory);
        return "creditcard";
    }



    @PostMapping("/paybills")
    public String gotoPaybills(@AuthenticationPrincipal AuthUser principal, @RequestParam String creditCardNumber, Model model){
        User currentUser = userService.getUserByUsername(principal.getUsername());
        model.addAttribute("user", currentUser);
        CreditCard creditCard = creditCardService.getCardByNumber(creditCardNumber);
        model.addAttribute("creditCard", creditCard);
        List<BankAccount> bankAccounts = currentUser.getBankAccounts();
        model.addAttribute("bankAccounts", bankAccounts);
        return "paybills";
    }
    // Temp Controller
    @PostMapping("/paybills/confirm")
    public String gotoPB(@AuthenticationPrincipal AuthUser principal, Model model,@RequestParam("to") String creditCardNumber, @RequestParam("amountValue") String amountValue, @RequestParam("account") String account) throws BankAccountNotFoundException {

        User currentUser = userService.getUserByUsername(principal.getUsername());
        model.addAttribute("user", currentUser);
        model.addAttribute("creditCard", creditCardNumber);
        model.addAttribute("amountValue", amountValue);
        model.addAttribute("account", account);
        log.info("Amount Value: " + amountValue);
        log.info("Credit Card: " + creditCardNumber);
        log.info("Account: " + account);
        CreditCard creditCard = creditCardService.getCardByNumber(creditCardNumber);
        List<BankAccount> bankAccounts = currentUser.getBankAccounts();
        BankAccount selectedBankAccount = currentUser.getBankAccounts().stream()
                .filter(bankAccount -> bankAccount.getAccountNumber().equals(account))
                .findFirst()
                .orElseThrow(BankAccountNotFoundException::new);
        BigDecimal bgamount = new BigDecimal(amountValue);
        log.info("Selected Bank Account: " + selectedBankAccount.getAccountNumber());
//        System.out.println(selectedBankAccount.getAccountNumber());
//        System.out.println(bgamount);
//        System.out.println(creditCardNumber);
        bankAccountService.payBills(selectedBankAccount.getAccountNumber(),bgamount,creditCard);

        return "redirect:/dashboard";
    }

}

