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
	public void itShouldNotDebitAccountWhenIbanIsNull() throws InvalidRessourceValuesException {
		AccountService accountService = new AccountServiceImpl(null);
		Assertions.assertThrows(InvalidRessourceValuesException.class, () -> {
			accountService.debit(null, 0);
		});
	}

	@Test
	public void itShouldDebitAccountWhenValidAccountAndAmount() throws BusinessException {
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
	public void itShouldNotDebitWhenInexsistantAccount() throws BusinessException {
		AccountService accountService = new AccountServiceImpl(accountRepository);
		Optional<Account> account = Optional.ofNullable(null);
		Mockito.when(accountRepository.findById("FRXXXXXXX")).thenReturn(account);
		Assertions.assertThrows(InvalidRessourceValuesException.class, () -> {
			accountService.debit("FRXXXXXXX", 200);
		});
	}

	@Test
	public void itShouldNotDebitWhenTheAccountAmountLessThenTheRequestedAmount() throws BusinessException {
		AccountService accountService = new AccountServiceImpl(accountRepository);
		Optional<Account> account = Optional.of(new Account("FRXXXXXXX", 200));
		Mockito.when(accountRepository.findById("FRXXXXXXX")).thenReturn(account);
		Assertions.assertThrows(InvalidRessourceValuesException.class, () -> {
			accountService.debit("FRXXXXXXX", 400);
		});
	}

	@Test
	public void itShouldNotCreditAccountWhenIbanIsNull() throws InvalidRessourceValuesException {
		AccountService accountService = new AccountServiceImpl(null);
		Assertions.assertThrows(InvalidRessourceValuesException.class, () -> {
			accountService.credit(null, 0);
		});
	}

	@Test
	public void itShouldCreditAccountWhenValidAccountAndAmount() throws BusinessException {
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
	public void itShouldNotTransferWhenThePayerIbanIsSimilarToPayeeIban() throws InvalidRessourceValuesException {
		Optional<Account> payerAccount = Optional.of(new Account("FRXXXXX", 400));
		Optional<Account> payeeAccount = Optional.of(new Account("FRXXXXX", 200));
		AccountService accountService = new AccountServiceImpl(accountRepository);
		Mockito.when(accountRepository.findById("FRXXXXX")).thenReturn(payerAccount);
		Mockito.when(accountRepository.findById("FRXXXXX")).thenReturn(payeeAccount);

		Assertions.assertThrows(InvalidRessourceValuesException.class, () -> {
			accountService.transfer("FRXXXXX", "FRXXXXX", 100);
		});
	}

	@Test
	public void itShouldNotTransferWhenNegativeRequestAmount() throws InvalidRessourceValuesException {
		AccountService accountService = new AccountServiceImpl(null);
		Assertions.assertThrows(InvalidRessourceValuesException.class, () -> {
			accountService.transfer("FRXXXXX", "FRYYYYY", -100);
		});
	}

	@Test
	public void itShouldNotTransferAccountWhenAccountAmountLessThanRequestAmount() throws BusinessException {
		AccountService accountService = new AccountServiceImpl(accountRepository);
		Optional<Account> payerAccount = Optional.of(new Account("FRXXXXXXX", 400));
		Optional<Account> payeeAccount = Optional.of(new Account("FRYYYYYYY", 200));

		Assertions.assertThrows(InvalidRessourceValuesException.class, () -> {
			accountService.transfer(payerAccount.get().getIban(), payeeAccount.get().getIban(), 600);
		});
	}

	@Test
	public void itShouldtransferAccountWhenThePayerAndPayeeAndAmountAreValid() throws BusinessException {
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

	@Test
	public void itShouldNotGetAccountWithNullIban() throws BusinessException {
		AccountService accountService = new AccountServiceImpl(accountRepository);

		Account account = new Account(null, 400);
		Optional<Account> optionalAccount = Optional.of(account);

		Assertions.assertThrows(InvalidRessourceValuesException.class, () -> {
			accountService.getAccountByIban(optionalAccount.get().getIban());
		});
	}

	@Test
	public void itShouldNotGetAccountWithInvalidIban() throws BusinessException {
		AccountService accountService = new AccountServiceImpl(accountRepository);

		Account account = new Account("FRYYYYYYY", 400);
		Optional<Account> optionalAccount = Optional.of(account);

		Assertions.assertThrows(AccountNotFoundException.class, () -> {
			accountService.getAccountByIban(optionalAccount.get().getIban());
		});
	}

	@Test
	public void itShouldGetAccountWithValidIban() throws BusinessException {
		AccountService accountService = new AccountServiceImpl(accountRepository);

		List<Operation> operations = new ArrayList<>();
		operations.add(new Operation(100, new Date(), OperationType.CREDIT));
		Account account = new Account("FRXXXXXXX", 400);
		account.setOperations(operations);
		Optional<Account> optionalAccount = Optional.of(account);
		Mockito.when(accountRepository.findById("FRXXXXXXX")).thenReturn(optionalAccount);

		Assertions.assertEquals(accountService.getAccountByIban(optionalAccount.get().getIban()).getIban(),
				"FRXXXXXXX");
		Assertions.assertEquals(accountService.getAccountByIban(optionalAccount.get().getIban()).getAmount(), 400);
		Assertions.assertEquals(
				accountService.getAccountByIban(optionalAccount.get().getIban()).getOperationDTOs().size(), 1);
	};

	@Test
	public void itShouldNotCreateAccountWhenAmountIsNegative() throws InvalidRessourceValuesException {
		AccountService accountService = new AccountServiceImpl(null);
		Assertions.assertThrows(InvalidRessourceValuesException.class, () -> {
			accountService.create("FRXXXXXXX", -4);
		});
	}
}