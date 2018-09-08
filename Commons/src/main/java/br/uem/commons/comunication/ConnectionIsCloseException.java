package br.uem.commons.comunication;

public class ConnectionIsCloseException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ConnectionIsCloseException(String string, Exception e) {
		super(string, e);
	}

	public ConnectionIsCloseException() {
	}

}
