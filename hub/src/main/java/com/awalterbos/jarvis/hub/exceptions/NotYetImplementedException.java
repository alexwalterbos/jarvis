package com.awalterbos.jarvis.hub.exceptions;

public class NotYetImplementedException extends RuntimeException {

	public NotYetImplementedException() {
		this("Not yet implemented.");
	}

	public NotYetImplementedException(String message) {
		super(message);
	}
}
