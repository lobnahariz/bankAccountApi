package com.api.bankAccountApi.exceptions;

import java.io.Serializable;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class BusinessException extends Exception implements Serializable{

	private static final long serialVersionUID = 1L;
	private String message;

	public BusinessException(String message) {
		this.message = message;
	}

}
