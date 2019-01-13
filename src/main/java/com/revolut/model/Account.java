package com.revolut.model;

public class Account {
	private String num;
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
	public String getNum() {
		return num;
	}
	public void setNum(String num) {
		this.num = num;
	}
	public void debt() {
		// TODO Auto-generated method stub
		
	}
	public void credit() {
		// TODO Auto-generated method stub
		
	}
}
