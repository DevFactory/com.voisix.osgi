package com.voisix.osgi.ei.processors;

import java.math.BigDecimal;
import java.util.ArrayList;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;

public class IntegerArrayListAverageProcessor implements Processor {

	@Override
	public void process(Exchange exchange) throws Exception {		
		final Message in = exchange.getIn();
		final ArrayList<?> list = in.getBody(ArrayList.class);
		BigDecimal total = BigDecimal.ZERO;
		for (Object value : list) {
			total = total.add(BigDecimal.valueOf((Integer) value));
		}
		in.setBody(total.divide(new BigDecimal(list.size())));
	}
}
