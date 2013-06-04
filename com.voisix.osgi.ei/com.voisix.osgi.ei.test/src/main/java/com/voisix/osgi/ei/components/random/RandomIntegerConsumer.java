package com.voisix.osgi.ei.components.random;

import org.apache.camel.Endpoint;
import org.apache.camel.Processor;
import org.apache.camel.impl.DefaultConsumer;

public class RandomIntegerConsumer extends DefaultConsumer {

	public RandomIntegerConsumer(Endpoint endpoint, Processor processor) {
		super(endpoint, processor);		
	}

	
}
