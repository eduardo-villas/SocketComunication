package br.uem.client;

import java.io.IOException;

import br.uem.client.protocol.InvalidClientStateException;

public interface ClientInterface {

	public void runClient() throws InvalidClientStateException;

	public void doComunication()  throws InvalidClientStateException, IOException;

	public boolean isOpen() throws InvalidClientStateException;

}
