package com.voisix.osgi.ei.processors;

import java.util.ArrayList;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;

public class IntegerArrayListSumProcessor implements Processor {

	@Override
	public void process(Exchange exchange) throws Exception {		
		final Message in = exchange.getIn();
		final ArrayList<?> list = in.getBody(ArrayList.class);
		Integer total = 0;
		for (Object value : list) {
			total = total + (Integer) value;
		}
		in.setBody(total);
	}

}
