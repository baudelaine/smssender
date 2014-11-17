package com.baudelaine.bluemix.sms;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;

/**
 * Servlet implementation class GetContacts
 */
@WebServlet("/GetEventContacts")
public class GetEventContacts extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetEventContacts() {
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
		ServletContext ctx = getServletContext();
		InputStream is = ctx.getResourceAsStream("/files/event.contacts.csv");
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        String csv = "";
        List<Contact> contacts = new ArrayList<Contact>();
        if(br != null){
        	StringBuffer sb = new StringBuffer();
        	String line = "";
        	while((line = br.readLine()) != null){
        		sb.append(line + "\n");
        	}
        	csv = sb.toString();
        	contacts =  Contact.setFromCsv(csv);
        }
    	System.out.println("contacts=" + contacts);
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(response.getOutputStream(), contacts);
	}		
		
}
