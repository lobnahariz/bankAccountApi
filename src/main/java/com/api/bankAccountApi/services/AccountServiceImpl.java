package com.api.bankAccountApi.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.api.bankAccountApi.dtos.AccountDTO;
import com.api.bankAccountApi.dtos.OperationDTO;
import com.api.bankAccountApi.entities.Account;
import com.api.bankAccountApi.entities.Operation;
import com.api.bankAccountApi.entities.OperationType;
import com.api.bankAccountApi.exceptions.AccountNotFoundException;
import com.api.bankAccountApi.exceptions.BusinessException;
import com.api.bankAccountApi.exceptions.InvalidRessourceValuesException;
import com.api.bankAccountApi.repositories.AccountRepository;

@Service
public class AccountServiceImpl implements AccountService {

	AccountRepository accountRepository;

	public AccountServiceImpl(AccountRepository accountRepository) {
		this.accountRepository = accountRepository;
	}

	@Override
	public Account create(String iban, double amount) throws BusinessException {
		Account account = verifyAccount(iban, amount);
		if(account != null)
			throw new InvalidRessourceValuesException("Iban is already exist");
		return accountRepository.save(new Account(iban,amount));
	}
	
	@Override
	public void debit(String iban, double amount) throws BusinessException {
		Account account = verifyAccount(iban, amount);
		if (account == null || account.getAmount() < amount)
			throw new InvalidRessourceValuesException("Account doesn't exist or negative amount");
		final double accountAmount = account.getAmount();
		account.setAmount(accountAmount - amount);
		account.getOperations().add(new Operation(amount, new Date(), OperationType.DEBIT, account));
		accountRepository.save(account);
	}

	@Override
	public void credit(String iban, double amount) throws BusinessException {
		Account account = verifyAccount(iban, amount);
		if (account == null)
			throw new AccountNotFoundException();
		account.setAmount(account.getAmount() + amount);
		account.getOperations().add(new Operation(amount, new Date(), OperationType.CREDIT, account));
		accountRepository.save(account);
	}

	@Override
	@Transactional
	public void transfer(String payerIban, String payeeIban, double amount) throws BusinessException {
		verifyAccount(payerIban, amount);
		verifyAccount(payeeIban, amount);
		if (payeeIban.equals(payerIban))
			throw new InvalidRessourceValuesException("Verify your payerIban/payeeIban/amount");
		debit(payerIban, amount);
		credit(payeeIban, amount);
	}

	private Account verifyAccount(String iban, double amount) throws InvalidRessourceValuesException {
		if (iban == null || iban.isEmpty() || amount < 0)
			throw new InvalidRessourceValuesException("Verify your iban/amount");
		Optional<Account> account = accountRepository.findById(iban);
		if (account.isPresent())
			return account.get();
		else
			return null;
	}

	@Override
	public AccountDTO getAccountByIban(String iban) throws BusinessException {
		if (iban == null)
			throw new InvalidRessourceValuesException("verify your Iban");

		Optional<Account> account = accountRepository.findById(iban);
		if (!account.isPresent())
			throw new AccountNotFoundException();
		return toAccountDTO(account.get());
	}

	private AccountDTO toAccountDTO(Account account) {

		AccountDTO accountDTO = new AccountDTO();
		accountDTO.setIban(account.getIban());
		accountDTO.setAmount(account.getAmount());
		List<OperationDTO> operationDTO = new ArrayList<>();
		account.getOperations().forEach(operation -> operationDTO.add(new OperationDTO(operation.getId(),
				operation.getAmount(), operation.getDate().toString(), operation.getOperationType().toString())));
		accountDTO.setOperationDTOs(operationDTO);
		return accountDTO;
	}
}
