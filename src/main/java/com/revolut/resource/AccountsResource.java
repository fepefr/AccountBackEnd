package com.revolut.resource;

import java.net.URI;
import java.util.ArrayList;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.revolut.model.Account;
import com.revolut.model.Bank;
import com.revolut.vo.Accounts;

@Singleton
@Path("v1/accounts")
public class AccountsResource{
	@Inject
	private Bank bank;

	
	@GET
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Account get(@PathParam("id") Long id) {
		Account account = bank.getAccount(id);
	    return account;
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Accounts getAll() {
		Accounts accountsWrapper= new Accounts();
		accountsWrapper.setAccountList(new ArrayList<Account>(bank.getAccounts().values()));
	    return accountsWrapper;
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	 public Response add(Account account) {
        bank.addAccount(account);
        URI uri = URI.create("/accounts/" + account.getNumber());
        return Response.created(uri).build();
    }

	@Path("{id}")
	@DELETE
	public Response remove(@PathParam("id") Long id) {
		boolean isRemoved = bank.removeAccount(id);
		Response resp = Response.status(202).build();
		if(!isRemoved)
			resp = Response.noContent().build();
		return resp;
	}
	
    @PUT
    @Path("{id}/debit")
    @Produces(MediaType.APPLICATION_JSON)
    public Response debt(@PathParam("id") Long id, Number value) {
    	Account account = bank.getAccount(id);
    	Number balance = account.debt(value);
    	return Response.ok().entity(balance).build();
    }
    
    @PUT
    @Path("{id}/credit")
    @Produces(MediaType.APPLICATION_JSON)
    public Response credit(@PathParam("id") Long id, Number value) {
    	Account account = bank.getAccount(id);
    	Number balance = account.credit(value);
    	return Response.ok().entity(balance).build();
    }
}