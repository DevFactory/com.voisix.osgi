package com.voisix.osgi.ei.components.random;

import org.apache.camel.Component;
import org.apache.camel.Consumer;
import org.apache.camel.Processor;
import org.apache.camel.Producer;
import org.apache.camel.impl.DefaultEndpoint;


public class RandomIntegerEndpoint extends DefaultEndpoint {
		
	private final Integer range;	
	
	public RandomIntegerEndpoint(String uri, Component component, Integer range) {
		super(uri, component);
		this.range = range;
	}

	@Override
	public Producer createProducer() throws Exception {		
		return new RandomIntegerProducer(this, range);
	}

	@Override
	public Consumer createConsumer(Processor processor) throws Exception {
		return new RandomIntegerConsumer(this, processor);
	}

	@Override
	public boolean isSingleton() {
		return false;
	}

}
