package br.uem.client;

import java.io.IOException;

import br.uem.commons.comunication.InvalidComunicationStateException;

public interface ClientInterface {

	public void runClient() throws InvalidComunicationStateException;

	public void doComunication() throws InvalidComunicationStateException, IOException;

	public boolean isOpen() throws InvalidComunicationStateException;

}
