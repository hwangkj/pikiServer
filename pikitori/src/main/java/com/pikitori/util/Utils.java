package com.pikitori.util;

import javax.ws.rs.core.MediaType;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;
import com.sun.jersey.core.util.MultivaluedMapImpl;

public class Utils {
	
	 private ClientResponse sendSimpleMessage(String recipient, String title, String verifyMailBody) {
	      Client client = Client.create();
	      client.addFilter(new HTTPBasicAuthFilter("api", "key-bd2be556998dd57c98ccb680d224eed1"));
	      WebResource webResource = client.resource("https://api.mailgun.net/v3/noreply.pikitori.com/messages");
	      MultivaluedMapImpl formData = new MultivaluedMapImpl();
	      formData.add("from", "Pikitori <noreply@noreply.pikitori.com>");
	      formData.add("to", recipient);
	      formData.add("subject", title);
	      formData.add("html", verifyMailBody);
	      return webResource.type(MediaType.APPLICATION_FORM_URLENCODED).post(ClientResponse.class, formData);
	   }

}
