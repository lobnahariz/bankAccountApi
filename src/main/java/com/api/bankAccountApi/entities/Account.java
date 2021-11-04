package com.api.bankAccountApi.entities;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Account implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	private String iban;

	private double amount;

	public Account() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Account(String iban, double amount) {
		// TODO Auto-generated constructor stub
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
		Account other = (Account) obj;
		return Double.doubleToLongBits(amount) == Double.doubleToLongBits(other.amount)
				&& Objects.equals(iban, other.iban);
	}

	@Override
	public String toString() {
		return "Account [iban=" + iban + ", amount=" + amount + "]";
	}

}
