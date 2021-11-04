package com.api.bankAccountApi.services;

import com.api.bankAccountApi.dtos.AccountDTO;
import com.api.bankAccountApi.exceptions.BusinessException;

public interface AccountService {

	public void debit(String iban, double amount) throws BusinessException;

	public void credit(String iban, double amount) throws BusinessException;

	public void transfer(String payerIban, String payeeIban, double amount) throws BusinessException;
	
	public AccountDTO getAccountByIban(String iban) throws BusinessException;
}
