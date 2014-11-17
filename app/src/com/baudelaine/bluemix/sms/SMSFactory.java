package com.baudelaine.bluemix.sms;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Random;

import com.twilio.sdk.TwilioRestClient;
import com.twilio.sdk.TwilioRestException;
import com.twilio.sdk.resource.factory.SmsFactory;
import com.twilio.sdk.resource.instance.Sms;

public class SMSFactory{
	
	String accountSid;
	String authToken;
	List<String> phones;	
	TwilioRestClient client = null;
	SmsFactory fact = null;
	
	public SMSFactory(){
		this.accountSid = "Dummy";
		this.authToken = "Dummy";
		this.phones = new ArrayList<String>(Arrays.asList("+33611111111","+33622222222","+33633333333"));
	}

	public SMSFactory(String accountSid, String authToken) {
		// TODO Auto-generated constructor stub
		this.accountSid = accountSid;
		this.authToken = authToken;
		setSMSFactory();
	}
	
	private void setSMSFactory(){
		try{
			this.client = new TwilioRestClient(this.accountSid, this.authToken);
			this.fact = this.client.getAccount().getSmsFactory();
		}
		catch(IllegalArgumentException e){
		}
	}
	
	public SMS sendSMS(SMS sms){
		
		if(this.fact != null){
			try {
				sms.setFrom(this.getRandomPhone());
				Sms twilio = this.fact.create(sms.params);
				sms.setSid(twilio.getSid());
				sms.setBody(twilio.getBody());
				sms.setTo(twilio.getTo());
				sms.setWhen(twilio.getDateCreated());
				sms.setStatus(twilio.getStatus());
				System.out.println("twilio.getSid()=" + twilio.getSid());
			} catch (TwilioRestException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else{
			sms.setFrom(this.getRandomPhone());
			sms.setWhen(new Date());
			sms.setSid("Dummy");
			sms.setStatus("Dummy");
		}
		
		return sms;
	}
	
	public TwilioRestClient getClient() {
		return client;
	}

	public void setClient(TwilioRestClient client) {
		this.client = client;
	}

	public String getaccountSid() {
		return accountSid;
	}

	public void setaccountSid(String accountSid) {
		this.accountSid = accountSid;
	}

	public String getauthToken() {
		return authToken;
	}

	public void setauthToken(String authToken) {
		this.authToken = authToken;
	}

	public List<String> getPhones() {
		return phones;
	}
	
	public String getRandomPhone(){

		if(this.phones.size() > 1){
			Random r = new Random();
		
			return this.phones.get(r.nextInt(this.phones.size()));
		}
		return this.phones.get(0);
	}

	public void setPhones(List<String> phones) {
		this.phones = phones;
	}

	public String toString(){
		String result = "";
		result += "SMSFACTORY accountSid=" + this.accountSid;
		result += "\nSMSFACTORY authToken=" + this.authToken;
		result += "\nSMSFACTORY phones=" + this.phones;
		
		return result;
	}

}

