package com.api.bankAccountApi.dtos;

import java.io.Serializable;
import java.util.Objects;

public class AccountParameterDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	private String iban;
	private double amount;

	
	public AccountParameterDTO(String iban, double amount) {
		super();
		this.iban = iban;
		this.amount = amount;
	}

	public String getIban() {
		return iban;
	}

	public void setIban(String iban) {
		this.iban = iban;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}
}
