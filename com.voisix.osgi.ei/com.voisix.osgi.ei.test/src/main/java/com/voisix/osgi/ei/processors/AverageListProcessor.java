package com.voisix.osgi.ei.processors;

import java.math.BigDecimal;
import java.util.ArrayList;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;

public class AverageListProcessor implements Processor {

	@Override
	public void process(Exchange exchange) throws Exception {
		
		final Message in = exchange.getIn();
		@SuppressWarnings("unchecked")
		final ArrayList<Number> list = in.getBody(ArrayList.class);
		BigDecimal total = BigDecimal.ZERO;
		for (Number i : list) {
			if (i instanceof Integer) {
				total = total.add(BigDecimal.valueOf(i.intValue()));
			} else {
				if (i instanceof Double) {
					total = total.add(BigDecimal.valueOf(i.doubleValue()));
				} else {
					throw new IllegalArgumentException(i.getClass().getName() + " not supported");
				}
			}
		}
		in.setBody(total.divide(new BigDecimal(list.size())));
	}

}
