package com.api.bankAccountApi;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.api.bankAccountApi.entities.Account;
import com.api.bankAccountApi.entities.Operation;
import com.api.bankAccountApi.entities.OperationType;
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
		List<Operation> operations = new ArrayList<>();
		operations.add(new Operation(100, new Date(), OperationType.CREDIT));
		Account account = new Account("FRXXXXXXX", 200);
		account.setOperations(operations);
		Optional<Account> optionalAccount = Optional.of(account);
		Mockito.when(accountRepository.findById("FRXXXXXXX")).thenReturn(optionalAccount);
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
		List<Operation> operations = new ArrayList<>();
		operations.add(new Operation(100, new Date(), OperationType.CREDIT));
		Account account = new Account("FRXXXXXXX", 400);
		account.setOperations(operations);
		Optional<Account> optionalAccount = Optional.of(account);
		Mockito.when(accountRepository.findById("FRXXXXXXX")).thenReturn(optionalAccount);
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
	public void itShouldNotTransferAccountWithInferiorAmount() throws BusinessException {
		AccountService accountService = new AccountServiceImpl(accountRepository);
		Optional<Account> payerAccount = Optional.of(new Account("FRXXXXXXX", 400));
		Optional<Account> payeeAccount = Optional.of(new Account("FRYYYYYYY", 200));

		Assertions.assertThrows(InvalidRessourceValuesException.class, () -> {
			accountService.transfer(payerAccount.get().getIban(), payeeAccount.get().getIban(), 600);
		});
	}

	@Test
	public void itShouldtransferAccount() throws BusinessException {
		AccountService accountService = new AccountServiceImpl(accountRepository);

		List<Operation> payerOperations = new ArrayList<>();
		payerOperations.add(new Operation(100, new Date(), OperationType.DEBIT));
		Account payerAccount = new Account("FRXXXXXXX", 400);
		payerAccount.setOperations(payerOperations);
		Optional<Account> optionalPayerAccount = Optional.of(payerAccount);

		List<Operation> payeeOperations = new ArrayList<>();
		payeeOperations.add(new Operation(100, new Date(), OperationType.CREDIT));
		Account payeeAccount = new Account("FRYYYYYYY", 200);
		payeeAccount.setOperations(payeeOperations);
		Optional<Account> optionalPayeeAccount = Optional.of(payeeAccount);

		Mockito.when(accountRepository.findById("FRXXXXXXX")).thenReturn(optionalPayerAccount);
		Mockito.when(accountRepository.findById("FRYYYYYYY")).thenReturn(optionalPayeeAccount);

		accountService.transfer(optionalPayerAccount.get().getIban(), optionalPayeeAccount.get().getIban(), 200);
	}

}