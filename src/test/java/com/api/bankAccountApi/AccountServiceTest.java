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
public class AccountServiceTest{
	
	@Mock
	AccountRepository accountRepository;

	@Test
	public void itShouldThrowInvalidRessourceValuesExceptionsWhenDebitAccountServiceCalled() throws InvalidRessourceValuesException {
		AccountService accountService = new AccountServiceImpl(null);
		Assertions.assertThrows(InvalidRessourceValuesException.class, ()->{accountService.debit(null, 0);} );
	}
	
	@Test
	public void itShouldDebitAccount() throws BusinessException {
		AccountService accountService = new AccountServiceImpl(accountRepository);
		Optional<Account> account = Optional.of(new Account("FRXXXXXXX",200));
		Mockito.when(accountRepository.findById("FRXXXXXXX")).thenReturn(account);
		accountService.debit("FRXXXXXXX", 200);
	}
	
	@Test
	public void itShouldNotDebitInexsistantAccount() throws BusinessException {
		AccountService accountService = new AccountServiceImpl(accountRepository);
		Optional<Account> account = Optional.ofNullable(null);
		Mockito.when(accountRepository.findById("FRXXXXXXX")).thenReturn(account);
		Assertions.assertThrows(AccountNotFoundException.class, ()->{accountService.debit("FRXXXXXXX", 200);} );

	}
}
