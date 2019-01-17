package com.revolut;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.net.URI;
import java.util.List;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.util.HttpStatus;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.revolut.model.Account;
import com.revolut.model.Client;
import com.revolut.vo.Accounts;

public class MyResourceTest {

    private static final String ACCOUNTS_API = "accounts";
	private HttpServer server;
    private WebTarget target;

    @Before
    public void setUp() throws Exception {
        // start the server
        server = Main.startServer();
        // create the client
        javax.ws.rs.client.Client c = javax.ws.rs.client.ClientBuilder.newClient();

        // uncomment the following line if you want to enable
        // support for JSON in the client (you also have to uncomment
        // dependency on jersey-media-json module in pom.xml and Main.startServer())
        // --
        // c.configuration().enable(new org.glassfish.jersey.media.json.JsonJaxbFeature());

        target = c.target(Main.BASE_URI);
    }

    @After
    public void tearDown() throws Exception {
        server.shutdownNow();
    }
  
    @Test
    public void testCreateAccounts() {
    	Response resp = null;
    	for (Long i = 0L; i < 10; i++) {
    		//Create test
    		Account account = buildAccount(i);
    		resp = target.path(ACCOUNTS_API).request().post(Entity.entity(account, MediaType.APPLICATION_JSON));
    		assertEquals(HttpStatus.CREATED_201.getStatusCode(), resp.getStatusInfo().getStatusCode());
    		//Retrieve test
    		URI accountUri = resp.getLocation();
    		resp = target.path(accountUri.getPath()).request().get();
    		assertEquals(HttpStatus.OK_200.getStatusCode(), resp.getStatusInfo().getStatusCode());
    		Account accountRetrieved = resp.readEntity(Account.class);
    		System.out.println(accountRetrieved.toString());
    		assertEquals(accountRetrieved.getNumber(), account.getNumber());
		}    
    	
    	//Retrieve all test
    	resp = target.path(ACCOUNTS_API).request().get();
    	assertEquals(HttpStatus.OK_200.getStatusCode(), resp.getStatusInfo().getStatusCode());
    	Accounts accounts = resp.readEntity(Accounts.class);  
        List<Account> accountList = accounts.getAccountList();
		assertEquals(accountList.size(), 10);
		for (Account account : accountList) {
			//Credit test
			Number initBalance = account.getBalance();
			//Calculate 10% from account balance
			Number value = calculatePercentage(10D,initBalance);
			account.credit(value);
			resp = target.path(ACCOUNTS_API).path(account.getId().toString()).path("/credit").
					request().put(Entity.entity(value, MediaType.APPLICATION_JSON));
			assertEquals(HttpStatus.OK_200.getStatusCode(), resp.getStatusInfo().getStatusCode());
			Number newBalance = resp.readEntity(Number.class);
			assertEquals(account.getBalance(), newBalance);
		
			//Debt test
			initBalance = account.getBalance();
			//value = calculatePercentage(10D,initBalance);
			account.debt(value);
			resp = target.path(ACCOUNTS_API).path(account.getId().toString()).path("/debt").
					request().put(Entity.entity(value, MediaType.APPLICATION_JSON));
			assertEquals(HttpStatus.OK_200.getStatusCode(), resp.getStatusInfo().getStatusCode());
			newBalance = resp.readEntity(Number.class);
			assertEquals(account.getBalance(), newBalance);
		}
		
		//Delete test
		for (Account account : accountList) {
			System.out.println(account.toString());
        	resp = target.path(ACCOUNTS_API).path(account.getId().toString()).request().delete();
        	assertEquals(HttpStatus.ACCEPTED_202.getStatusCode(), resp.getStatusInfo().getStatusCode());
        	resp = target.path(ACCOUNTS_API).path(account.getId().toString()).request().delete();
        	assertEquals(HttpStatus.NO_CONTENT_204.getStatusCode(), resp.getStatusInfo().getStatusCode());
		}
    }
    
    private Number calculatePercentage(Number value, Number perc) {
        return (value.doubleValue() * perc.doubleValue()) / 100;
    }
    
    private Account buildAccount(Long index) {
    	Account account = new Account();
		//account.setId(index);
		account.setNumber("AN"+index);
		account.setBalance(new BigDecimal(index*100));
		Client client = new Client();
		client.setName("Client Name "+index);
		client.setAddress("Adress "+index);
		client.setId(index);
		account.setClient(client);
		return account;
    }
}