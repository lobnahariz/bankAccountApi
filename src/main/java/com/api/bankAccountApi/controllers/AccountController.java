package com.api.bankAccountApi.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.api.bankAccountApi.dtos.AccountDTO;
import com.api.bankAccountApi.dtos.OperationParameterDTO;
import com.api.bankAccountApi.exceptions.BusinessException;
import com.api.bankAccountApi.services.AccountService;

@RestController
public class AccountController {

	@Autowired
	AccountService operationService;

	@RequestMapping(value = "/withdraw", method = RequestMethod.POST)
	public ResponseEntity withdraw(@RequestBody OperationParameterDTO operationDTO) throws BusinessException {

		operationService.debit(operationDTO.getIban(), operationDTO.getAmount());

		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

	@RequestMapping(value = "/deposit", method = RequestMethod.POST)
	public ResponseEntity deposit(@RequestBody OperationParameterDTO operationDTO) throws BusinessException {

		operationService.credit(operationDTO.getIban(), operationDTO.getAmount());

		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

	@RequestMapping(value = "/transfer/{payerIban}/{payeeIban}/{amount}", method = RequestMethod.POST)
	public ResponseEntity transfer(@PathVariable String payerIban, @PathVariable String payeeIban,
			@PathVariable double amount) throws BusinessException {

		operationService.transfer(payerIban, payeeIban, amount);

		return ResponseEntity.status(HttpStatus.CREATED).build();
	}
	
	@RequestMapping(value = "/account/{iban}", method = RequestMethod.GET)
	public AccountDTO getAccount(@PathVariable String iban) throws BusinessException {

		AccountDTO accountDTO = operationService.getAccountByIban(iban);
		return accountDTO;
	}
}
