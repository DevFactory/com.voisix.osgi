package com.voisix.osgi.ei.processors;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.AbstractCollection;
import java.util.Collection;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;

public class IntegerArrayListAverageProcessor implements Processor {

	@Override
	public void process(Exchange exchange) throws Exception {		
		final Message in = exchange.getIn();
		final Collection<?> list = in.getBody(AbstractCollection.class);
		BigDecimal total = BigDecimal.ZERO;
		for (Object value : list) {
			total = total.add(BigDecimal.valueOf((Integer) value));
		}
		in.setBody(total.divide(new BigDecimal(list.size()), RoundingMode.HALF_UP));
	}
}
