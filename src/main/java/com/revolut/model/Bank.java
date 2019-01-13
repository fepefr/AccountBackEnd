package com.revolut.model;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
@ApplicationScoped
public class Bank {
	private List<Client> clients;
	private List<Account> accounts;

	public List<Client> getClients() {
		return clients;
	}
	public void setClients(List<Client> clients) {
		this.clients = clients;
	}
	public List<Account> getAccounts() {
		return accounts;
	}
	public void setAccounts(List<Account> accounts) {
		this.accounts = accounts;
	}
	
    public void addAccount(Account account) {
    	if(this.accounts == null) accounts = new ArrayList<Account>();
    	this.accounts.add(account);
    }
    
    public void addClient(Client client) {
    	if(this.clients == null) clients = new ArrayList<Client>();
    	this.clients.add(client);
    }
	public Account searchAccount(Long accountNum) {
		return null;
	}
	public void removeAccount(Long accountNum) {
		// TODO Auto-generated method stub
	}
}