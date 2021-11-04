package com.api.bankAccountApi.exceptions;

import java.io.Serializable;

public class InvalidRessourceValuesException extends BusinessException implements Serializable{

	private static final long serialVersionUID = 1L;

	public InvalidRessourceValuesException(String message) {
		super(message);
	}
}