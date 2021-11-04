package com.api.bankAccountApi;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.api.bankAccountApi.entities.Account;
import com.api.bankAccountApi.exceptions.AccountNotFoundException;
import com.api.bankAccountApi.exceptions.BusinessException;
import com.api.bankAccountApi.exceptions.InvalidRessourceValuesException;
import com.api.bankAccountApi.repositories.AccountRepository;
import com.api.bankAccountApi.services.AccountService;
import com.api.bankAccountApi.services.AccountServiceImpl;

@ExtendWith(MockitoExtension.class)
public class AccountServiceTest {

	@Mock
	AccountRepository accountRepository;

	@Test
	public void itShouldThrowInvalidRessourceValuesExceptionsWhenDebitAccountServiceCalled()
			throws InvalidRessourceValuesException {
		AccountService accountService = new AccountServiceImpl(null);
		Assertions.assertThrows(InvalidRessourceValuesException.class, () -> {
			accountService.debit(null, 0);
		});
	}

	@Test
	public void itShouldDebitAccount() throws BusinessException {
		AccountService accountService = new AccountServiceImpl(accountRepository);
		Optional<Account> account = Optional.of(new Account("FRXXXXXXX", 200));
		Mockito.when(accountRepository.findById("FRXXXXXXX")).thenReturn(account);
		accountService.debit("FRXXXXXXX", 200);
	}

	@Test
	public void itShouldNotDebitInexsistantAccount() throws BusinessException {
		AccountService accountService = new AccountServiceImpl(accountRepository);
		Optional<Account> account = Optional.ofNullable(null);
		Mockito.when(accountRepository.findById("FRXXXXXXX")).thenReturn(account);
		Assertions.assertThrows(InvalidRessourceValuesException.class, () -> {
			accountService.debit("FRXXXXXXX", 200);
		});
	}

	@Test
	public void itShouldNotDebitWithInferiorAmount() throws BusinessException {
		AccountService accountService = new AccountServiceImpl(accountRepository);
		Optional<Account> account = Optional.of(new Account("FRXXXXXXX", 200));
		Mockito.when(accountRepository.findById("FRXXXXXXX")).thenReturn(account);
		Assertions.assertThrows(InvalidRessourceValuesException.class, () -> {
			accountService.debit("FRXXXXXXX", 400);
		});
	}

	@Test
	public void itShouldThrowInvalidRessourceValuesExceptionsWhenCreditAccountServiceCalled()
			throws InvalidRessourceValuesException {
		AccountService accountService = new AccountServiceImpl(null);
		Assertions.assertThrows(InvalidRessourceValuesException.class, () -> {
			accountService.credit(null, 0);
		});
	}

	@Test
	public void itShouldCreditAccount() throws BusinessException {
		AccountService accountService = new AccountServiceImpl(accountRepository);
		Optional<Account> account = Optional.of(new Account("FRXXXXXXX", 400));
		Mockito.when(accountRepository.findById("FRXXXXXXX")).thenReturn(account);
		accountService.credit("FRXXXXXXX", 200);
	}

	@Test
	public void itShouldNotCreditInexsistantAccount() throws BusinessException {
		AccountService accountService = new AccountServiceImpl(accountRepository);
		Optional<Account> account = Optional.ofNullable(null);
		Mockito.when(accountRepository.findById("FRXXXXXXX")).thenReturn(account);
		Assertions.assertThrows(AccountNotFoundException.class, () -> {
			accountService.credit("FRXXXXXXX", 200);
		});
	}

	@Test
	public void itShouldThrowInvalidRessourceValuesExceptionsWhenTransferAccountServiceCalledWithNullValue()
			throws InvalidRessourceValuesException {
		AccountService accountService = new AccountServiceImpl(null);
		Assertions.assertThrows(InvalidRessourceValuesException.class, () -> {
			accountService.transfer(null, null, 0);
		});
	}

	@Test
	public void itShouldThrowInvalidRessourceValuesExceptionsWhenTransferAccountServiceCalledWithSameIban()
			throws InvalidRessourceValuesException {
		AccountService accountService = new AccountServiceImpl(null);
		Assertions.assertThrows(InvalidRessourceValuesException.class, () -> {
			accountService.transfer("FRXXXXX", "FRXXXXX", 100);
		});
	}

	@Test
	public void itShouldThrowInvalidRessourceValuesExceptionsWhenTransferAccountServiceCalledWithNegativeAmount()
			throws InvalidRessourceValuesException {
		AccountService accountService = new AccountServiceImpl(null);
		Assertions.assertThrows(InvalidRessourceValuesException.class, () -> {
			accountService.transfer("FRXXXXX", "FRYYYYY", -100);
		});
	}

	@Test
	public void itShouldtransferAccount() throws BusinessException {
		AccountService accountService = new AccountServiceImpl(accountRepository);
		Optional<Account> account = Optional.of(new Account("FRXXXXXXX", 200));
		Mockito.when(accountRepository.findById("FRXXXXXXX")).thenReturn(account);
		accountService.debit("FRXXXXXXX", 200);
	}

}
