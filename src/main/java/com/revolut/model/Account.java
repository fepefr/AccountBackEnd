package com.revolut.model;

import java.math.BigDecimal;

public class Account {
	private Long id;
	private String number;
	private Number balance;
	private Client client;
	
	public Number getBalance() {
		return balance;
	}
	public void setBalance(Number balance) {
		this.balance = balance;
	}
	public Client getClient() {
		return client;
	}
	public void setClient(Client client) {
		this.client = client;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String num) {
		this.number = num;
	}
	public Number debt(Number value) {
		this.balance = new BigDecimal(balance.toString()).subtract( new BigDecimal(value.toString()));
		return this.balance;
	}
	public Number credit(Number value) {
		this.balance = new BigDecimal(balance.toString()).add( new BigDecimal(value.toString()));
		return this.balance;		
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	@Override
	public String toString() {
		return "Account [id ="+ id + "num=" + number + ", balance=" + balance + ", client=" + client + "]";
	}
}
