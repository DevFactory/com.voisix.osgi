package com.voisix.osgi.ei.exceptions;

public class ComponentException extends Exception {
	private static final long serialVersionUID = 1L;
	
	public ComponentException() {
		super();
	}

	public ComponentException(String message, Throwable cause) {
		super(message, cause);
	}

	public ComponentException(String message) {
		super(message);
	}

	public ComponentException(Throwable cause) {
		super(cause);
	}
}
