package com.fdmgroup.CreditCardProject.service;

import com.fdmgroup.CreditCardProject.exception.BankTransactionNotFoundException;
import com.fdmgroup.CreditCardProject.model.BankTransaction;
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
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BankTransactionServiceTest {

	@Mock
	BankTransactionRepository mockBankTransactionRepo;

	@Mock
	BankTransaction mockBankTransaction;
	private BankTransactionService bankTransactionService;

	@BeforeEach
	void setUp() {
		bankTransactionService = new BankTransactionService();
		bankTransactionService.setBankTransactionRepository(mockBankTransactionRepo);
	}

	@Test
	@DisplayName("Get transaction by id throws error if transaction not found")
	void testGetById_InvalidId() {
		when(mockBankTransactionRepo.findById(1L)).thenReturn(Optional.empty());
		assertThrows(BankTransactionNotFoundException.class, () -> bankTransactionService.getTransactionById("1"));
	}

	@Test
	@DisplayName("Get transaction by id returns transaction")
	void testGetById_ValidId() throws BankTransactionNotFoundException {
		when(mockBankTransactionRepo.findById(1L)).thenReturn(Optional.of(mockBankTransaction));
		assertEquals(mockBankTransaction, bankTransactionService.getTransactionById("1"));
	}
}
