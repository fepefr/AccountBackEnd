package com.revolut.resource;

import java.net.URI;
import java.util.List;

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
	@Path("{accountNum}")
	@Produces(MediaType.APPLICATION_JSON)
	public Account search(@PathParam("accountNum") Long accountNum) {
		Account account = bank.searchAccount(accountNum);
	    return account;
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Accounts searchAll() {
		Accounts accounts = new Accounts();
		accounts.setAccountList(bank.getAccounts());
	    return accounts;
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	 public Response add(Account account) {
        bank.addAccount(account);
        URI uri = URI.create("/accounts/" + account.getNumber());
        return Response.created(uri).build();
    }

	@Path("{accountNum}")
	@DELETE
	public Response remove(@PathParam("accountNum") Long accountNum) {
		bank.removeAccount(accountNum);
		return Response.ok().build();
	}
	
    @PUT
    @Path("{accountNum}/debit")
    @Produces(MediaType.APPLICATION_JSON)
    public String debt(@PathParam("accountNum") Long accountNum, Number value) {
    	Account account = bank.searchAccount(accountNum);
    	account.debt();
        return "Got it!";
    }
    
    @PUT
    @Path("{accountNum}/debt")
    @Produces(MediaType.APPLICATION_JSON)
    public String credit(@PathParam("accountNum") Long accountNum, Number value) {
    	Account account = bank.searchAccount(accountNum);
    	account.credit();
        return "Got it!";
    }

    /**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "text/plain" media type.
     *
     * @return String that will be returned as a text/plain response.
     */
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getIt() {
        return "Got it!";
    }
}