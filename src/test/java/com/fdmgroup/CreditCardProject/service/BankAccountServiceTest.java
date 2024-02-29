package com.fdmgroup.CreditCardProject.service;

import com.fdmgroup.CreditCardProject.exception.BankAccountNotFoundException;
import com.fdmgroup.CreditCardProject.model.BankAccount;
import com.fdmgroup.CreditCardProject.model.BankTransaction;
import com.fdmgroup.CreditCardProject.model.User;
import com.fdmgroup.CreditCardProject.repository.BankAccountRepository;
import com.fdmgroup.CreditCardProject.repository.BankTransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BankAccountServiceTest {

	@Mock
	BankAccountRepository mockBankAccountRepo;

	@Mock
	BankTransactionRepository mockBankTransactionRepo;
	@Mock
	BankAccount mockBankAccount;
	@Mock
	User mockUser;
	@Mock
	BankTransaction mockBankTransaction;
	private BankAccountService bankAccountService;

	@BeforeEach
	void setUp() {
		bankAccountService = new BankAccountService();
		bankAccountService.setBankAccountRepository(mockBankAccountRepo);
		bankAccountService.setBankTransactionRepository(mockBankTransactionRepo);
	}

	@Test
	@DisplayName("Deposit with invalid account throws BankAccountNotFoundException")
	void testDepositToAccount_InvalidAccount() {
		when(mockBankAccountRepo.findByAccountNumber("1")).thenReturn(Optional.empty());
		assertThrows(BankAccountNotFoundException.class, () -> bankAccountService.depositToAccount("1", 5.5));
	}

	@Test
	@DisplayName("Deposit with valid account calls correct methods")
	void testDepositToAccount_HappyPath() throws BankAccountNotFoundException {
		when(mockBankAccountRepo.findByAccountNumber("1")).thenReturn(Optional.of(mockBankAccount));
		when(mockBankAccount.getAccountId()).thenReturn(1L);
		when(mockBankAccount.getCurrentBalance()).thenReturn(3.0);
		when(mockBankTransactionRepo.save(argThat(x -> x.getAmount() == 5.5 && x.getAccountToId() == 1)))
				.thenReturn(mockBankTransaction);

		long id = bankAccountService.depositToAccount("1", 5.5);
		verify(mockBankAccount).setCurrentBalance(8.5);
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
