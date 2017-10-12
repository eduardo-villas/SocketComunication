package br.uem.server;

import java.io.IOException;
import java.net.BindException;

import br.uem.commons.comunication.InvalidComunicationStateException;

public interface ServerInterface {

	public void initServer() throws InvalidComunicationStateException, BindException;

	public void waitForConnection() throws InvalidComunicationStateException;
	
	public void doComunication() throws InvalidComunicationStateException, IOException;

	public boolean isOpen() throws InvalidComunicationStateException;

}
