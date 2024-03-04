package com.fdmgroup.CreditCardProject.service;

import com.fdmgroup.CreditCardProject.exception.BankAccountNotFoundException;
import com.fdmgroup.CreditCardProject.model.BankAccount;
import com.fdmgroup.CreditCardProject.model.BankTransaction;
import com.fdmgroup.CreditCardProject.model.User;
import com.fdmgroup.CreditCardProject.repository.BankAccountRepository;
import com.fdmgroup.CreditCardProject.repository.BankTransactionRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class BankAccountServiceTest {

	@MockBean
	private BankAccountRepository mockBankAccountRepo;

	@MockBean
	private BankTransactionRepository mockBankTransactionRepo;
	
	@MockBean
	private BankAccount mockBankAccount;
	
	@MockBean
	private User mockUser;
	
	@MockBean
	BankTransaction mockBankTransaction;

	@Autowired
	private BankAccountService bankAccountService;

@Test
    public void testGetAccountBalanceByBankAccountId() {
    	Random random = new Random();
        String longTest = random.toString();
        double doubleTest = random.nextDouble();
        BigDecimal decimalTest = new BigDecimal(doubleTest);
    	
        // Act
        Mockito.when(mockBankAccountRepo.findByAccountNumber(longTest)).thenReturn(Optional.of(mockBankAccount));
        Mockito.when(mockBankAccount.getCurrentBalance()).thenReturn(decimalTest);
        BigDecimal result = bankAccountService.getAccountBalanceByBankAccountNumber(longTest);

        // Assess
        Mockito.verify(mockBankAccountRepo).findByAccountNumber(longTest);
        assertEquals(decimalTest, result);
    }

    @Test
    public void testGetAccountBalanceByBankAccountIdAccountNotFound() {
    	Random random = new Random();
        String longTest = random.toString();
        
        // Act
        Mockito.when(mockBankAccountRepo.findByAccountNumber(longTest)).thenReturn(Optional.empty());

        // Assert
        BigDecimal result = bankAccountService.getAccountBalanceByBankAccountNumber(longTest);
        BigDecimal test = new BigDecimal(0.0);
        
        // Assess
        Mockito.verify(mockBankAccountRepo).findByAccountNumber(longTest);
        assertEquals(test, result);
    }

	@Test
	@DisplayName("Deposit with invalid account throws BankAccountNotFoundException")
	void testDepositToAccount_InvalidAccount() {
		when(mockBankAccountRepo.findByAccountNumber("1")).thenReturn(Optional.empty());
		assertThrows(BankAccountNotFoundException.class,
				() -> bankAccountService.depositToAccount("1", BigDecimal.valueOf(5.5)));
	}

	@Test
	@DisplayName("Deposit with valid account calls correct methods")
	void testDepositToAccount_HappyPath() throws BankAccountNotFoundException {
		when(mockBankAccountRepo.findByAccountNumber("1")).thenReturn(Optional.of(mockBankAccount));
		when(mockBankAccount.getAccountId()).thenReturn(1L);
		when(mockBankAccount.getCurrentBalance()).thenReturn(BigDecimal.valueOf(3.0));
		when(mockBankTransactionRepo
				.save(argThat(x -> x.getAmount().compareTo(new BigDecimal("5.5")) == 0 && x.getAccountToId() == 1)))
				.thenReturn(mockBankTransaction);

		long id = bankAccountService.depositToAccount("1", BigDecimal.valueOf(5.5));
		verify(mockBankAccount).setCurrentBalance(argThat(x -> x.doubleValue() == 8.5));
		verify(mockBankAccount).addTransactionHistory(mockBankTransaction);
		verify(mockBankAccountRepo).save(mockBankAccount);
		assertEquals(id, mockBankTransaction.getTransactionId());
	}

	@Test
	@DisplayName("Get username from account number throws error for invalid account number")
	void testGetOwnerUsername_FailsForInvalidAccountNumber() {
		when(mockBankAccountRepo.findByAccountNumber("1")).thenReturn(Optional.empty());
		assertThrows(BankAccountNotFoundException.class,
				() -> bankAccountService.getUsernameOfAccountByAccountNumber("1"));

	}

	@Test
	@DisplayName("Get username from account number")
	void testGetOwnerUsername_HappyPath() throws BankAccountNotFoundException {
		when(mockBankAccountRepo.findByAccountNumber("1")).thenReturn(Optional.of(mockBankAccount));
		when(mockBankAccount.getUser()).thenReturn(mockUser);
		when(mockUser.getUsername()).thenReturn("person");
		assertEquals(bankAccountService.getUsernameOfAccountByAccountNumber("1"), "person");

	}
}
