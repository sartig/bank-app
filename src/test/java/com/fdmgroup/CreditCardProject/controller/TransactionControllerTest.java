package com.fdmgroup.CreditCardProject.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

import com.fdmgroup.CreditCardProject.exception.BankAccountNotFoundException;
import com.fdmgroup.CreditCardProject.model.AuthUser;
import com.fdmgroup.CreditCardProject.model.User;
import com.fdmgroup.CreditCardProject.service.BankAccountService;
import com.fdmgroup.CreditCardProject.service.UserService;

class TransactionControllerTest {

    @Mock
    private AuthUser authUser;

    @Mock
    private UserService userService;

    @Mock
    private BankAccountService bankAccountService;

    @Mock
    private Model model;

    @InjectMocks
    private TransactionController transactionController;

    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);

        // Mock user data
        User user = new User();
        user.setUsername("Ali");

        // Mock authUser behavior
        when(authUser.getUsername()).thenReturn("Ali");

        // Mock userService behavior
        when(userService.getUserByUsername("Ali")).thenReturn(user);
    }

    @Test
    public void testGoToAccountDeposit() throws BankAccountNotFoundException {
        
    	when(bankAccountService.isAccountNumberValid("413414311")).thenReturn(true);
        when(bankAccountService.getUsernameOfAccountByAccountNumber("413414311")).thenReturn("Ali");

        String result = transactionController.goToAccountDeposit(authUser, "413414311", model);

        // Verify that the correct attributes are added to the model
        verify(model).addAttribute("user", userService.getUserByUsername("Ali"));
        verify(model).addAttribute("accountId", "413414311");

        // Verify that the correct view name is returned
        assertEquals("transaction", result);
    }
}
