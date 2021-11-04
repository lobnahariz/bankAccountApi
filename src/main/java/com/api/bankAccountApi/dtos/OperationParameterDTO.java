package com.api.bankAccountApi.dtos;

import java.io.Serializable;
import java.util.Objects;

public class OperationParameterDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	private String iban;
	private double amount;

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

	@Override
	public int hashCode() {
		return Objects.hash(amount, iban);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		OperationParameterDTO other = (OperationParameterDTO) obj;
		return Double.doubleToLongBits(amount) == Double.doubleToLongBits(other.amount)
				&& Objects.equals(iban, other.iban);
	}

	@Override
	public String toString() {
		return "OperationDTO [iban=" + iban + ", amount=" + amount + "]";
	}

}
