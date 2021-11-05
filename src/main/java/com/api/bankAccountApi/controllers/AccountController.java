package com.api.bankAccountApi.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.api.bankAccountApi.dtos.AccountDTO;
import com.api.bankAccountApi.dtos.AccountParameterDTO;
import com.api.bankAccountApi.exceptions.BusinessException;
import com.api.bankAccountApi.services.AccountService;

@RestController
public class AccountController {

	@Autowired
	AccountService accountService;

	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public ResponseEntity create(@RequestBody AccountParameterDTO operationDTO) throws BusinessException {

		accountService.create(operationDTO.getIban(), operationDTO.getAmount());

		return ResponseEntity.status(HttpStatus.CREATED).build();
	}
	
	@RequestMapping(value = "/withdraw", method = RequestMethod.POST)
	public ResponseEntity withdraw(@RequestBody AccountParameterDTO operationDTO) throws BusinessException {

		accountService.debit(operationDTO.getIban(), operationDTO.getAmount());

		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

	@RequestMapping(value = "/deposit", method = RequestMethod.POST)
	public ResponseEntity deposit(@RequestBody AccountParameterDTO operationDTO) throws BusinessException {

		accountService.credit(operationDTO.getIban(), operationDTO.getAmount());

		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

	@RequestMapping(value = "/transfer", method = RequestMethod.POST)
	public ResponseEntity transfer(@RequestParam String payerIban, @RequestParam String payeeIban,
			@RequestParam double amount) throws BusinessException {

		accountService.transfer(payerIban, payeeIban, amount);

		return ResponseEntity.status(HttpStatus.CREATED).build();
	}
	
	@RequestMapping(value = "/account/{iban}", method = RequestMethod.GET)
	public AccountDTO getAccount(@PathVariable String iban) throws BusinessException {

		AccountDTO accountDTO = accountService.getAccountByIban(iban);
		return accountDTO;
	}
}
