package com.api.bankAccountApi.exceptions;

import java.io.Serializable;

public class AccountNotFoundException extends BusinessException implements Serializable{

	private static final long serialVersionUID = 1L;

	public AccountNotFoundException() {
		super("Account Not Found");
		// TODO Auto-generated constructor stub
	}

}
