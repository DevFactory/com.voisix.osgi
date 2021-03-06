package com.voisix.osgi.ei.components.random;

import java.util.Random;

import org.apache.camel.Endpoint;
import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.impl.DefaultMessage;
import org.apache.camel.impl.DefaultProducer;

import com.voisix.osgi.ei.exceptions.ProducerException;

public class RandomIntegerProducer extends DefaultProducer {
	
	private final Integer range;
	private final Random random;

	public RandomIntegerProducer(Endpoint endpoint, Integer range) {
		super(endpoint);
		this.range = range;
		random = new Random(System.currentTimeMillis());
	}

	@Override
	public void process(Exchange exchange) {		
		final Message out = new DefaultMessage();
		out.setBody(random.nextInt(range), Integer.class);	
		exchange.setOut(out);
	}
}
