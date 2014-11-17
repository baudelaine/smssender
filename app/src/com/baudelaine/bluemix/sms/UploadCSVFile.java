package com.baudelaine.bluemix.sms;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.codehaus.jackson.map.ObjectMapper;

/**
 * Servlet implementation class UploadCSVFile
 */
@WebServlet("/UploadCSVFile")
public class UploadCSVFile extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UploadCSVFile() {
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
		List<String> lines = new ArrayList<String>();
		try {
			List<FileItem> items = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(request);
			for (FileItem item : items) {
				if (!item.isFormField()) {
					// item is the file (and not a field), read it in and add to List
					Scanner scanner = new Scanner(new InputStreamReader(item.getInputStream(), "UTF-8"));
					
					while (scanner.hasNextLine()) {
						String line = scanner.nextLine().trim();
						if (line.length() > 0) {
							lines.add(line);
						}
					}
					scanner.close();
					
					
					break;
				}
			}
			
		} catch (Exception e) {
			request.setAttribute("msg", e.getMessage());
			e.printStackTrace(System.err);
		}
		
		if(lines.size() > 0){
			System.out.println("lines.size()=" + lines.size());
			System.out.println("lines=" + lines.toString());
			
			//write lines to /files/contacts.csv
			ServletContext ctx = getServletContext();
			
			
			GestFic gf = new GestFic();
			gf.openOutput(ctx.getRealPath("/files/sample.contacts.csv"));
			gf.writeList(lines);
			gf.closeOutput();
			
			List<Contact> contacts = Contact.setFromCsv(lines);
			System.out.println("contacts=" + contacts);
			
	        ObjectMapper mapper = new ObjectMapper();
	        mapper.writeValue(response.getOutputStream(), contacts);					
		}
	}

}
