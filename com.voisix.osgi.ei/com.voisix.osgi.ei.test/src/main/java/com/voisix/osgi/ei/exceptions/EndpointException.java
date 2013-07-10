package com.voisix.osgi.ei.exceptions;

public class EndpointException extends Exception {
	private static final long serialVersionUID = 1L;
	
	public EndpointException() {
		super();
	}

	public EndpointException(String message, Throwable cause) {
		super(message, cause);
	}

	public EndpointException(String message) {
		super(message);
	}

	public EndpointException(Throwable cause) {
		super(cause);
	}
}
