package com.fdmgroup.CreditCardProject.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fdmgroup.CreditCardProject.exception.BankAccountNotFoundException;
import com.fdmgroup.CreditCardProject.exception.InsufficientBalanceException;
import com.fdmgroup.CreditCardProject.model.AuthUser;
import com.fdmgroup.CreditCardProject.model.BankTransaction;
import com.fdmgroup.CreditCardProject.model.BankTransactionType;
import com.fdmgroup.CreditCardProject.model.User;
import com.fdmgroup.CreditCardProject.service.BankAccountService;
import com.fdmgroup.CreditCardProject.service.BankTransactionService;
import com.fdmgroup.CreditCardProject.service.UserService;

import java.math.BigDecimal;

class TransactionControllerTest {

    @Mock
    private AuthUser authUser;

    @Mock
    private UserService userService;

    @Mock
    private BankAccountService bankAccountService;
    
    @Mock
    private RedirectAttributes redirectAttributes;
    
    @Mock
    private Model model;
    
    @Mock
    private BankTransaction bankTransaction;
    
    @Mock
    private BankTransactionService bankTransactionService;
    
    @Mock
    private BankTransactionType bankTransactionType;

    @InjectMocks
    private TransactionController transactionController;

    @BeforeEach
    void setUp() throws Exception {
    	
        MockitoAnnotations.openMocks(this);
        User user = new User();
        user.setUsername("Ali");
        when(authUser.getUsername()).thenReturn("Ali");
        when(userService.getUserByUsername("Ali")).thenReturn(user);
    }

    @Test
    public void testGoToTransactionPage() throws BankAccountNotFoundException {
        
    	when(bankAccountService.isAccountNumberValid("413414311")).thenReturn(true);
        when(bankAccountService.getUsernameOfAccountByAccountNumber("413414311")).thenReturn("Ali");

        String result = transactionController.goToTransactionOrDashboardPage(authUser, "413414311", model);

        verify(model).addAttribute("user", userService.getUserByUsername("Ali"));
        verify(model).addAttribute("accountId", "413414311");

        assertEquals("transaction", result);
    }
    
    @Test
    public void testHandleTransactionRequest_deposit_success() throws BankAccountNotFoundException {
        String accountId = "accountId";
        String amount = "100.00";
        String action = "deposit";

        long transactionId = 123L;
        when(bankAccountService.depositToAccount(accountId, new BigDecimal(amount))).thenReturn(transactionId);
        when(authUser.getUsername()).thenReturn("Ali");
        when(bankAccountService.getUsernameOfAccountByAccountNumber(accountId)).thenReturn("Ali");

        String result = transactionController.handleTransactionRequest(authUser, accountId, amount, action, redirectAttributes);

        assertEquals("redirect:/transaction/receipt/123", result);
    }
    
    @Test
    public void testHandleTransactionRequest_withdrawal_success() throws BankAccountNotFoundException, InsufficientBalanceException {
        
    	when(authUser.getUsername()).thenReturn("Ali");

        when(bankAccountService.getUsernameOfAccountByAccountNumber("413414311")).thenReturn("Ali");

        long transactionId = 123L;
        when(bankAccountService.withdrawFromAccount("413414311", new BigDecimal("100.00"))).thenReturn(transactionId);

        String result = transactionController.handleTransactionRequest(authUser, "413414311", "100.00", "withdraw", redirectAttributes);

        assertEquals("redirect:/transaction/receipt/123", result);
    }
    
    @Test
    public void testHandleTransactionRequest_withdrawal_failure_insufficientBalance() throws BankAccountNotFoundException, InsufficientBalanceException {
        String accountId = "413414311";
        String amount = "123456.00";
        String action = "withdraw";

        when(authUser.getUsername()).thenReturn("Ali");
        when(bankAccountService.getUsernameOfAccountByAccountNumber(accountId)).thenReturn("Ali");

        doThrow(new InsufficientBalanceException()).when(bankAccountService).withdrawFromAccount(accountId, new BigDecimal(amount));

        String result = transactionController.handleTransactionRequest(authUser, accountId, amount, action, redirectAttributes);

        assertEquals("redirect:/transaction", result);
        verify(redirectAttributes).addAttribute("error", "insufficientFunds");
    }

    @Test
    public void testHandleTransactionRequest_accountNotFound() throws BankAccountNotFoundException {
        
    	when(authUser.getUsername()).thenReturn("Ali");
        when(bankAccountService.getUsernameOfAccountByAccountNumber("invalidAccountId")).thenReturn("otherUser");

        String result = transactionController.handleTransactionRequest(authUser, "invalidAccountId", "100.00", "withdraw", redirectAttributes);

        assertEquals("redirect:/dashboard", result);
    }
}
