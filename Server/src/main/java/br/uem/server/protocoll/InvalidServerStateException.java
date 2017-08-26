package br.uem.server.protocoll;

public class InvalidServerStateException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public InvalidServerStateException(String message) {
		super(message);
	}

}
