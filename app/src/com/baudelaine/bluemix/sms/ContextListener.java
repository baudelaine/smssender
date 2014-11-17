package com.baudelaine.bluemix.sms;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.Map.Entry;
import java.util.Properties;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * Application Lifecycle Listener implementation class ContextListener
 *
 */
@WebListener
public class ContextListener implements ServletContextListener {

	String accountSid = "";
	String authToken = "";
	String phones = "";    	
	SMSFactory fact = new SMSFactory();
	
    /**
     * Default constructor. 
     */
    public ContextListener() {
        // TODO Auto-generated constructor stub
    }

	/**
     * @see ServletContextListener#contextInitialized(ServletContextEvent)
     */
    public void contextInitialized(ServletContextEvent arg0) {
        // TODO Auto-generated method stub
    	
    	
    	//read properties file first
    	
		try {
			System.out.println("Getting Twilio account from /conf/twilio.properties...");
			ServletContext ctx = arg0.getServletContext();
			Properties props = new Properties();
			FileInputStream fis = new FileInputStream(ctx.getRealPath("/conf/twilio.properties"));
			props.load(fis);
			for(Entry<Object, Object> e: props.entrySet()){
				String key = (String) e.getKey();
				String value = (String) e.getValue();
				System.out.println(key + "=" + value);
			}
			
    		accountSid = props.getProperty("TWILIO_ACCOUNT_SID");
    		authToken = props.getProperty("TWILIO_AUTH_TOKEN");
    		phones = props.getProperty("TWILIO_PHONES");
    		if(check()){
    			setCheckedFact();
    		}
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.err.println("WARNING: not able to access to /conf/twilio.properties !!!");
			e.printStackTrace(System.err);
		}
    	
    	// then load system env if any 
    	
    	try{
			System.out.println("Getting Twilio account from system env...");
    		accountSid = System.getenv("TWILIO_ACCOUNT_SID");
    		authToken = System.getenv("TWILIO_AUTH_TOKEN");
    		phones = System.getenv("TWILIO_PHONES");
    		System.out.println("TWILIO_ACCOUNT_SID=" + accountSid);
    		System.out.println("TWILIO_AUTH_TOKEN=" + authToken);
    		System.out.println("TWILIO_PHONES=" + phones);
    		if(check()){
    			setCheckedFact();
    		}
    	}
    	catch(NullPointerException e){
			System.err.println("WARNING: not able to set all system env !!!");
    		e.printStackTrace(System.err);
    	}
	    	ServletContext ctx = arg0.getServletContext();
	    	ctx.setAttribute("SMSFACTORY", fact);
	    	System.out.println(fact);
    }
    
    
    public void setCheckedFact(){
		fact = new SMSFactory(accountSid, authToken);
		phones = phones.replaceAll("[\\s-\\.()]", "");
		if(phones.contains(",")){
			fact.setPhones(Arrays.asList(phones.split(",")));
		}
		else{
			fact.setPhones(Arrays.asList(phones));
		}
    }
    
    public boolean check(){
    	this.accountSid = this.accountSid.replaceAll("\"", "");
    	this.authToken = this.authToken.replaceAll("\"", "");
    	this.phones = this.phones.replaceAll("\"", "");
		if(accountSid.startsWith("AC") & 
				accountSid.length() == 34 & 
				authToken.length() == 32 & 
				phones.startsWith("+") &
				phones.length() > 11){
			return true;
			
		}
		return false;
    }

	/**
     * @see ServletContextListener#contextDestroyed(ServletContextEvent)
     */
    public void contextDestroyed(ServletContextEvent arg0) {
        // TODO Auto-generated method stub
    	ServletContext ctx = arg0.getServletContext();
    	ctx.removeAttribute("SMSFACTORY");
    }
	
}
