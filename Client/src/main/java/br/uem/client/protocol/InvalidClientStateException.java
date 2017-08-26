package br.uem.client.protocol;

public class InvalidClientStateException extends Exception {

	private static final long serialVersionUID = 1L;

	public InvalidClientStateException(String message) {
		super(message);
	}

}
