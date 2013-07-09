package com.voisix.osgi.ei.processors;

import java.util.AbstractCollection;
import java.util.Collection;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;

public class IntegerArrayListSumProcessor implements Processor {

	@Override
	public void process(Exchange exchange) throws ProcessorException {		
		final Message in = exchange.getIn();
		final Collection<?> list = in.getBody(AbstractCollection.class);
		Integer total = 0;
		for (Object value : list) {
			total = total + (Integer) value;
		}
		in.setBody(total);
	}
}
