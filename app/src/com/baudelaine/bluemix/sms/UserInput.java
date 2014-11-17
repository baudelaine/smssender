package com.baudelaine.bluemix.sms;

import java.util.List;

public class UserInput {
	
	String message;
	List<Contact> contacts;
	List<String> gsms;
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public List<Contact> getContacts() {
		return contacts;
	}
	public void setContacts(List<Contact> contacts) {
		this.contacts = contacts;
	}
	public List<String> getGsms() {
		return gsms;
	}
	public void setGsms(List<String> gsms) {
		this.gsms = gsms;
	}

}
