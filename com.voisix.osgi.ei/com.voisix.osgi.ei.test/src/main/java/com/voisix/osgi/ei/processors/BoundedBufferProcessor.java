package com.voisix.osgi.ei.processors;

import java.util.concurrent.ArrayBlockingQueue;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;

public class BoundedBufferProcessor implements Processor {
	private final ArrayBlockingQueue<Object> buffer;
	
	public BoundedBufferProcessor(int capacity) {
		buffer = new ArrayBlockingQueue<Object>(capacity,true);
	}
	
	@Override
	public void process(Exchange exchange) throws Exception {
		final Message in = exchange.getIn();
		if (buffer.remainingCapacity()==0) {
			buffer.poll();
		}

		buffer.put(in.getBody());
		in.setBody(buffer);
	}
}
