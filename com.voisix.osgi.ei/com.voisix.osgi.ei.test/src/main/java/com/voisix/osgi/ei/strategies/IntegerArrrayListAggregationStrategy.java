package com.voisix.osgi.ei.strategies;

import org.apache.camel.Exchange;
import org.apache.camel.processor.aggregate.AbstractListAggregationStrategy;

public class IntegerArrrayListAggregationStrategy extends AbstractListAggregationStrategy<Integer> {

	@Override
	public Integer getValue(Exchange exchange) {
		return exchange.getIn().getBody(Integer.class);
	}

}
