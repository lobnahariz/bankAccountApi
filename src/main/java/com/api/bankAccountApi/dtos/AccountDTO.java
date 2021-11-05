package com.api.bankAccountApi.dtos;

import java.io.Serializable;
import java.util.List;

public class AccountDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	private String iban;
	private double amount;
	private List<OperationDTO> operationDTOs;

	public AccountDTO() {
		super();
	}

	public AccountDTO(String iban, double amount, List<OperationDTO> operationDTOs) {
		super();
		this.iban = iban;
		this.amount = amount;
		this.operationDTOs = operationDTOs;
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

	public List<OperationDTO> getOperationDTOs() {
		return operationDTOs;
	}

	public void setOperationDTOs(List<OperationDTO> operationDTOs) {
		this.operationDTOs = operationDTOs;
	}
}
