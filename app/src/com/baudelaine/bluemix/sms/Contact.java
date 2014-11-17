package com.baudelaine.bluemix.sms;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Contact {
	
	String nom;
	String prenom;
	String email;
	String gsm;
	
	public void setToAnonymous(){
		this.nom = "anonymous";
		this.prenom = "anonymous";
		this.email = "unknown";
	}
	
	public static List<Contact> setFromCsv(List<String> csv){
		List<Contact> result = new ArrayList<Contact>();
		if(csv.get(0).split(",").length == Arrays.asList(Contact.class.getDeclaredFields()).size()){
			System.out.println("On est bien Tintin !!!");
			for(String line: csv){
				String[] cols = line.split(",");
    			Contact contact = new Contact();
    			contact.setNom(cols[0]);
    			contact.setPrenom(cols[1]);
    			contact.setEmail(cols[2]);
    			contact.setGsm(cols[3]);
    			result.add(contact);
			}
	        if(result.size() > 1){result.remove(0);}
		}		
		
		return result;
	}	
	
	public static List<Contact> setFromCsv(String csv){
		List<Contact> result = new ArrayList<Contact>();
		String[] lines = csv.split("\n");
		if(lines[0].split(",").length == Arrays.asList(Contact.class.getDeclaredFields()).size()){
			System.out.println("On est bien Tintin !!!");
			for(String line: lines){
				String[] cols = line.split(",");
    			Contact contact = new Contact();
    			contact.setNom(cols[0]);
    			contact.setPrenom(cols[1]);
    			contact.setEmail(cols[2]);
    			contact.setGsm(cols[3]);
    			result.add(contact);
			}
	        if(result.size() > 1){result.remove(0);}
		}		
		
		return result;
	}
	
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public String getPrenom() {
		return prenom;
	}
	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getGsm() {
		return gsm;
	}
	public void setGsm(String gsm) {
		gsm = gsm.replaceAll(" ", "");
		this.gsm = gsm;
	}
	
	public String toString(){
		return this.nom + "," + this.prenom + "," + this.email + "," + this.gsm;
	}

}
