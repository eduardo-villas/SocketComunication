package br.uem.server;

import java.io.IOException;
import java.net.BindException;

import br.uem.server.protocoll.InvalidServerStateException;

public interface ServerInterface {

	public void initServer() throws InvalidServerStateException, BindException;

	public void waitForConnection() throws InvalidServerStateException;
	
	public void doComunication() throws InvalidServerStateException, IOException;

	public boolean isOpen() throws InvalidServerStateException;

}
