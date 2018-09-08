package br.uem.commons.comunication;

public class InvalidComunicationStateException extends Exception {

	private static final long serialVersionUID = 1L;

	public InvalidComunicationStateException(String message) {
		super(message);
	}
	
	public InvalidComunicationStateException(String message, Exception e) {
		super(message, e);
	}

}
