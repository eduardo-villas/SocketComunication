package br.uem.commons.comunication.exceptions;

public class InvalidMessageLength extends RuntimeException {
	private static final long serialVersionUID = 1L;
	private int messageLength;

	public InvalidMessageLength(String message, int messageLength) {
		super(message);
		this.messageLength = messageLength;
	}

	public int getMessageLength() {
		return messageLength;
	}

	@Override
	public String toString() {
		return String.format("InvalidMessageLength [message=%s] [messageLength=%s]", getMessage(), messageLength);
	}
	
	

}
