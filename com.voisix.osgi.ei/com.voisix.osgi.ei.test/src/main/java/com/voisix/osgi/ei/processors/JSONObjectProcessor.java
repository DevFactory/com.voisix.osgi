package com.voisix.osgi.ei.processors;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.codehaus.jettison.json.JSONObject;

public class JSONObjectProcessor implements Processor {

	@Override
	public void process(Exchange exchange) throws Exception {
		final Message in = exchange.getIn();		
		final JSONObject jsonObject = new JSONObject(in.getBody(String.class));
		in.setBody(jsonObject);			
	}

}
