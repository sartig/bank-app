package com.fdmgroup.CreditCardProject.controller;
import com.fdmgroup.CreditCardProject.config.SecurityConfig;
import com.fdmgroup.CreditCardProject.exception.BankAccountNotFoundException;
import com.fdmgroup.CreditCardProject.exception.BankTransactionNotFoundException;
import com.fdmgroup.CreditCardProject.exception.ExcessPaymentException;
import com.fdmgroup.CreditCardProject.exception.InsufficientBalanceException;
import com.fdmgroup.CreditCardProject.model.*;
import com.fdmgroup.CreditCardProject.service.*;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

    @Autowired
    private BankTransactionService bankTransactionService;

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
    public ModelAndView gotoPB(@AuthenticationPrincipal AuthUser principal, Model model,
                               HttpServletRequest request,
                               RedirectAttributes redirectAttributes) throws BankAccountNotFoundException, InsufficientBalanceException {

        String creditCardNumber = request.getParameter("creditCardNumber");
        String account = request.getParameter("account");
        String amountValue = request.getParameter("amountValue");

        User currentUser = userService.getUserByUsername(principal.getUsername());
        CreditCard creditCards = creditCardService.getCardByNumber(creditCardNumber);
        BankAccount selectedBankAccount = currentUser.getBankAccounts().stream()
                .filter(bankAccount -> bankAccount.getAccountNumber().equals(account))
                .findFirst()
                .orElseThrow(BankAccountNotFoundException::new);
        BigDecimal bgamount = new BigDecimal(amountValue);
        try{
            long transactionID = bankAccountService.payBills(selectedBankAccount.getAccountNumber(),bgamount,creditCards);
            return new ModelAndView("redirect:/paybills/paybills_receipt/" + transactionID);
        } catch (InsufficientBalanceException e) {
            e.printStackTrace();
            redirectAttributes.addAttribute("error", "insufficientFunds");
            request.setAttribute(View.RESPONSE_STATUS_ATTRIBUTE, HttpStatus.TEMPORARY_REDIRECT);
            model.addAttribute("creditCard", creditCards);
            return new ModelAndView("redirect:/paybills?error=insufficientFunds");
        } catch (ExcessPaymentException e) {
            e.printStackTrace();
            redirectAttributes.addAttribute("error", "excessiveFunds");
            request.setAttribute(View.RESPONSE_STATUS_ATTRIBUTE, HttpStatus.TEMPORARY_REDIRECT);
            model.addAttribute("creditCard", creditCards);
            return new ModelAndView("redirect:/paybills?error=excessiveFunds");
        }

    }
    @GetMapping("/paybills/paybills_receipt/{transactionId}")
    public String gotoPBReceipt(@AuthenticationPrincipal AuthUser principal, @PathVariable String transactionId, Model model) throws BankTransactionNotFoundException {
        User currentUser = userService.getUserByUsername(principal.getUsername());
        model.addAttribute("user", currentUser);
        BankTransaction transaction = bankTransactionService.getTransactionById(transactionId);
        model.addAttribute("transaction", transaction);
        return "paybills_receipt";
    }


}

