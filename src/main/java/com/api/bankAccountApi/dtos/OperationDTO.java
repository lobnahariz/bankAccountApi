package com.api.bankAccountApi.dtos;

import java.io.Serializable;
import java.util.Objects;

public class OperationDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	private long id;
	private double amount;
	private String date;
	private String operationType;

	public OperationDTO(long id, double amount, String date, String operationType) {
		super();
		this.id = id;
		this.amount = amount;
		this.date = date;
		this.operationType = operationType;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getOperationType() {
		return operationType;
	}

	public void setOperationType(String operationType) {
		this.operationType = operationType;
	}

	@Override
	public int hashCode() {
		return Objects.hash(amount, date, id, operationType);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		OperationDTO other = (OperationDTO) obj;
		return Double.doubleToLongBits(amount) == Double.doubleToLongBits(other.amount)
				&& Objects.equals(date, other.date) && id == other.id
				&& Objects.equals(operationType, other.operationType);
	}

	@Override
	public String toString() {
		return "OperationDTO [id=" + id + ", amount=" + amount + ", date=" + date + ", operationType=" + operationType
				+ "]";
	}
	
	
}
