package com.voisix.osgi.ei.components.random;

import java.util.Map;

import org.apache.camel.Endpoint;
import org.apache.camel.impl.DefaultComponent;


public class RandomIntegerComponent extends  DefaultComponent {
	private final static String RANGE = "range";

	@Override
	protected Endpoint createEndpoint(String uri, String remaining, Map<String, Object> parameters) throws Exception {
		final Integer range = getAndRemoveParameter(parameters, RANGE, Integer.class, 10);	
		return new RandomIntegerEndpoint(uri, this, range);
	}
}
