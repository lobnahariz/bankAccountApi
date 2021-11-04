package com.api.bankAccountApi.dtos;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

public class AccountDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	private String iban;
	private double amount;
	private List<OperationDTO> operationDTOs;

	public AccountDTO() {
		super();
		// TODO Auto-generated constructor stub
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

	@Override
	public int hashCode() {
		return Objects.hash(amount, iban, operationDTOs);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AccountDTO other = (AccountDTO) obj;
		return Double.doubleToLongBits(amount) == Double.doubleToLongBits(other.amount)
				&& Objects.equals(iban, other.iban) && Objects.equals(operationDTOs, other.operationDTOs);
	}

	@Override
	public String toString() {
		return "AccountDTO [iban=" + iban + ", amount=" + amount + ", operationDTOs=" + operationDTOs + "]";
	}

}
