package com.api.bankAccountApi.services;

import java.util.Optional;

import org.springframework.stereotype.Service;
import com.api.bankAccountApi.entities.Account;
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
	public void debit(String iban, double amount) throws BusinessException {
		// TODO Auto-generated method stub
		Account account = verifyAccount(iban, amount);
		if (account != null && account.getAmount() >= amount) {
			final double accountAmount = account.getAmount();
			account.setAmount(accountAmount - amount);
			accountRepository.save(account);
		} else {
			throw new AccountNotFoundException();
		}
	}

	@Override
	public void credit(String iban, double amount) throws BusinessException {
		// TODO Auto-generated method stub
		Account account = verifyAccount(iban, amount);
		if (account != null) {
			final double accountAmount = account.getAmount();
			account.setAmount(accountAmount + amount);
			accountRepository.save(account);
		} else {
			throw new AccountNotFoundException();
		}
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
}
