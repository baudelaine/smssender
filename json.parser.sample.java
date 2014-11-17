package test;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

public class MainJsonArray {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		String fileName0 = "/root/workspace/antuar/TRANSACT/files/T1/retrieveCustomers.resp.json";
		
		try {

			InputStream in = new BufferedInputStream(new FileInputStream(fileName0));
			BufferedReader reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
			StringBuffer sb = new StringBuffer();
			String line = "";
			while((line = reader.readLine()) != null){
				sb.append(line);
			}
			String json0 = sb.toString();

			String customerName = "IBM0001";
			String objectName = "customerId";
			ObjectMapper mapper = new ObjectMapper();
			Map<String, Object> result = new HashMap<String, Object>();
			
			if(json0.startsWith("{")){
				json0 = "[" + json0 + "]";
			}
			
			List<Map<String, Object>> input = mapper.readValue(json0, new TypeReference<List<Map<String, Object>>>(){});
	
			Iterator<Map<String, Object>> iCustomers = input.iterator();
			
			
			while(iCustomers.hasNext()){
				Map<String, Object> customer = iCustomers.next();
				for(Map.Entry<String, Object> e: customer.entrySet()){
					try{
						System.out.println(e.getKey() + " -> " + "(" + e.getValue().getClass().getSimpleName() + ") " + e.getValue());
						if(e.getKey() == "firstName" ){
							if(((String) e.getValue()).equalsIgnoreCase(customerName)){
								result.put("customerId", customer.get("customerId"));
							}
						}
					}
					catch(NullPointerException npe){
						System.out.println(e.getKey() + " -> null");
					}
				}
			}
			
			System.out.println("result=" + mapper.writeValueAsString(result));
			
		}
		catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

}
