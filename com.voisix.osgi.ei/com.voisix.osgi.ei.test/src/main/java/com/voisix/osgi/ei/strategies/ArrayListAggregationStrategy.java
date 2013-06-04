package com.voisix.osgi.ei.strategies;

import java.util.ArrayList;

import org.apache.camel.Exchange;
import org.apache.camel.processor.aggregate.AggregationStrategy;

public class ArrayListAggregationStrategy implements AggregationStrategy {

	@Override
	public Exchange aggregate(Exchange oldExchange, Exchange newExchange) {
		final Object value = newExchange.getIn().getBody();
		
		
		if (oldExchange == null) {
			final ArrayList<Object> list = new ArrayList<Object>();
			list.add(value);
			newExchange.getIn().setBody(list);
			return newExchange;
		} else {
			@SuppressWarnings("unchecked")
			final ArrayList<Object> list = oldExchange.getIn().getBody(ArrayList.class);
			list.add(value);
			return oldExchange;
		}
	}
}
