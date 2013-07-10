package com.voisix.osgi.ei.processors;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

public class JSONObjectProcessor implements Processor {

	@Override
	public void process(Exchange exchange) throws ProcessorException {
		final Message in = exchange.getIn();		
		try {
			final JSONObject jsonObject = new JSONObject(in.getBody(String.class));
			in.setBody(jsonObject);
		} catch (JSONException e) {
			throw new ProcessorException(e);
		}
	}
}
