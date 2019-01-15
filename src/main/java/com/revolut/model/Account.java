package com.revolut.model;

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
	public void debt() {
		// TODO Auto-generated method stub
		
	}
	public void credit() {
		// TODO Auto-generated method stub
		
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
