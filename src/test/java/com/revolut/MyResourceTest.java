package com.revolut;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.net.URI;

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
    		Account account = buildAccount(i);
    		resp = target.path(ACCOUNTS_API).request().post(Entity.entity(account, MediaType.APPLICATION_JSON));
    		assertEquals(HttpStatus.CREATED_201.getStatusCode(), resp.getStatusInfo().getStatusCode());
    		URI accountUri = resp.getLocation();
    		resp = target.path(accountUri.getPath()).request().get();
    		assertEquals(HttpStatus.OK_200.getStatusCode(), resp.getStatusInfo().getStatusCode());
    		Account accountRetrieved = resp.readEntity(Account.class);
    		assertEquals(accountRetrieved.getNumber(), account.getNumber());
		}    	
    	resp = target.path(ACCOUNTS_API).request().get();
    	assertEquals(HttpStatus.OK_200.getStatusCode(), resp.getStatusInfo().getStatusCode());
    	Accounts accounts = resp.readEntity(Accounts.class);  
        assertEquals(accounts.getAccountList().size(), 10);
        
        
        //resp = target.path(ACCOUNTS_API).request().get();
    	//assertEquals(HttpStatus.OK_200.getStatusCode(), resp.getStatusInfo().getStatusCode());
    //	Accounts accounts = resp.readEntity(Accounts.class);  
      //  assertEquals(accounts.getAccountList().size(), 10);
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
    
/*    @Test
    public void testCreateAccounts() {
    	Response resp = null;
    	for (Long i = 0L; i < 10; i++) {
    		Account account = new Account();
    		//usar AtomicLong
    		account.setId(i);
    		account.setNumber("AN"+i);
    		account.setBalance(new BigDecimal(i*100));
    		Client client = new Client();
    		client.setName("Client Name "+i);
    		client.setAddress("Adress "+i);
    		client.setId(i);
			account.setClient(client);
    		resp = target.path(ACCOUNTS_API).request().post(Entity.entity(account, MediaType.APPLICATION_JSON));
    		assertEquals(HttpStatus.CREATED_201.getStatusCode(), resp.getStatusInfo().getStatusCode());			
		}    	
        resp = target.path(ACCOUNTS_API).request().get();
        Accounts accounts = resp.readEntity(Accounts.class);  
        for (Account account : accounts.getAccountList()) {
        	System.out.println(account.toString());
		}
        assertEquals(accounts.getAccountList().size(), 10);
    }*/
    

    

    
	/*
	 * @Test public void testContent() { Response resp =
	 * target.path("v1/accounts").request().get(); List readEntity =
	 * resp.readEntity(List.class);
	 * assertEquals(HttpStatus.NO_CONTENT_204.getStatusCode(), resp.getStatus()); }
	 */
    
}
