package com.api.bankAccountApi.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Operation implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue
	private long id;
	private double amount;
	private Date date;
	private OperationType operationType;
	@ManyToOne
	@JoinColumn(name = "ACCT_IBAN")
	private Account account;

	public Operation() {
		super();
	}

	public Operation(double amount, Date date, OperationType operationType) {
		super();
		this.amount = amount;
		this.date = date;
		this.operationType = operationType;
	}


	public Operation(double amount, Date date, OperationType operationType, Account account) {
		super();
		this.amount = amount;
		this.date = date;
		this.operationType = operationType;
		this.account = account;
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

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public OperationType getOperationType() {
		return operationType;
	}

	public void setOperationType(OperationType operationType) {
		this.operationType = operationType;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

}
