package br.uem.commons.comunication;

import java.io.IOException;

public interface SenderReceiver {

	public void sendMessage(String format) throws IOException;

	public String getMessage() throws IOException;

	public boolean isOpen() throws InvalidComunicationStateException;
	
}
