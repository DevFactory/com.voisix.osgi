package com.voisix.osgi.ei.processors;

import java.util.Calendar;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;

public class ValueChangedProcessor implements Processor {
	
	private Object oldBody;
	private long lastChangeTimestamp;

	@Override
	public void process(Exchange exchange) {
		final long currentTimestamp = Calendar.getInstance().getTimeInMillis();
		final Message in = exchange.getIn();
		final Object body = in.getBody();
		
		if (oldBody==null) {
			oldBody = body;
			lastChangeTimestamp = currentTimestamp;
		}
		if (body.equals(oldBody)) {
			exchange.getIn().setBody(false, Boolean.class);
			in.setHeader("timeSinceLastChange", currentTimestamp - lastChangeTimestamp);
		} else {				
			in.setBody(true, Boolean.class);
			in.setHeader("changedTimestamp", Calendar.getInstance().getTimeInMillis());
			in.setHeader("timeSinceLastChange", 0);
			lastChangeTimestamp = currentTimestamp;			
		}
		oldBody = body;		
	}
}
