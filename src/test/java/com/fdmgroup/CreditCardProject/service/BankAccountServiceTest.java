package com.fdmgroup.CreditCardProject.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.Random;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.fdmgroup.CreditCardProject.model.BankAccount;
import com.fdmgroup.CreditCardProject.repository.BankAccountRepository;

@SpringBootTest
public class BankAccountServiceTest {
	@MockBean
    private BankAccountRepository bankAccountRepo;

	@MockBean
	BankAccount bankAccountMock;
	
    @Autowired
    private BankAccountService bankAccountService;

    @Test
    public void testGetAccountBalanceByBankAccountId() {
    	Random random = new Random();
        String longTest = random.toString();
        double doubleTest = random.nextDouble();
        BigDecimal decimalTest = new BigDecimal(doubleTest);
    	
        // Act
        Mockito.when(bankAccountRepo.findByAccountNumber(longTest)).thenReturn(Optional.of(bankAccountMock));
        Mockito.when(bankAccountMock.getCurrentBalance()).thenReturn(decimalTest);
        BigDecimal result = bankAccountService.getAccountBalanceByBankAccountNumber(longTest);

        // Assess
        Mockito.verify(bankAccountRepo).findByAccountNumber(longTest);
        assertEquals(decimalTest, result);
    }

    @Test
    public void testGetAccountBalanceByBankAccountIdAccountNotFound() {
    	Random random = new Random();
        String longTest = random.toString();
        
        // Act
        Mockito.when(bankAccountRepo.findByAccountNumber(longTest)).thenReturn(Optional.empty());

        // Assert
        BigDecimal result = bankAccountService.getAccountBalanceByBankAccountNumber(longTest);
        BigDecimal test = new BigDecimal(0.0);
        
        // Assess
        Mockito.verify(bankAccountRepo).findByAccountNumber(longTest);
        assertEquals(test, result);
    }
}
