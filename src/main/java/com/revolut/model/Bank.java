package com.revolut.model;

import java.util.HashMap;
import java.util.Map;

import javax.enterprise.context.ApplicationScoped;
@ApplicationScoped
public class Bank {
	private Map<Long, Account> accounts;

	public Bank() {
		super();
		this.accounts = new HashMap<Long, Account>();
	}

	public Map<Long, Account> getAccounts() {
		return accounts;
	}
	public void setAccounts(Map<Long, Account> accounts) {
		this.accounts = accounts;
	}
	
    public void addAccount(Account account) {
    	this.accounts.put(account.getId(),account);
    }
    
	public Account getAccount(Long accountId) {
		return this.accounts.get(accountId);
	}
	public boolean removeAccount(Long accountId) {
		return (this.accounts.remove(accountId)==null)?false:true;
	}
}