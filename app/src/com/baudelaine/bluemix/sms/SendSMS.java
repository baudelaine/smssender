package com.baudelaine.bluemix.sms;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;



/**
 * Servlet implementation class SendSMS
 */
@WebServlet("/SendSMS")
public class SendSMS extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	List<SMS> smss = new LinkedList<SMS>();
	public static final String ACCOUNT_SID = "Dummy"; 
	public static final String AUTH_TOKEN = "Dummy";
	DateFormat df = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.FULL);
    
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SendSMS() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
        BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));
        String json = "";
        if(br != null){
            json = br.readLine();
        }
        
        System.out.println(json);
 
        ObjectMapper mapper = new ObjectMapper();

        UserInput ui = mapper.readValue(json, UserInput.class);
        
        String message = ui.getMessage();
        List<String> gsms = ui.getGsms();
        List<Contact> contacts = ui.getContacts();
        
        System.out.println("message=" + message);
        System.out.println("gsms=" + gsms);
        System.out.println("contacts=" + contacts);
        
        List<SMS> smss = new ArrayList<SMS>();
        
        ServletContext ctx = getServletContext();
        SMSFactory fact = (SMSFactory) ctx.getAttribute("SMSFACTORY");
        
        if(contacts.size() > 0){
        	for(Contact contact: contacts){
        		SMS sms = new SMS();
        		String body = message.replaceAll("\\$\\{nom\\}", contact.getNom());
        		body = body.replaceAll("\\$\\{prenom\\}", contact.getPrenom());
        		sms.setBody(body);
        		sms.setTo(contact.getGsm());
        		smss.add(sms);
        	}
        }
        else{
	        if(gsms.size() > 0){
	        	for(String gsm: gsms){
	        		if(gsm.length() > 0){
		        		SMS sms = new SMS();
		        		String body = message.replaceAll("\\$\\{nom\\}", "anonymous");
		        		body = body.replaceAll("\\$\\{prenom\\}", "");
		        		sms.setBody(body);
		        		sms.setTo(gsm);
		        		smss.add(sms);
	        		}
	        	}
	        }
        }
        
      	System.out.println(fact);
    	System.out.println(fact.client);
    	System.out.println(fact.fact);        

        if(smss.size() > 0){
        	for(SMS sms: smss){
        		fact.sendSMS(sms);
        	}
        }
 
        System.out.println("smss=" + smss);
        
 		
        mapper.writeValue(response.getOutputStream(), smss);
        
		
	}

}
