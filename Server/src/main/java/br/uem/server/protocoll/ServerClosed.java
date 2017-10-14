package br.uem.server.protocoll;

import java.io.IOException;
import java.net.BindException;

import br.uem.commons.comunication.InvalidComunicationStateException;

public class ServerClosed implements ServerState {

	public ServerClosed() {

	}

	@Override
	public void doComunication() throws InvalidComunicationStateException, IOException {
		throw new InvalidComunicationStateException("O Server fechado. Impossível enviar mensagens.");
	}

	@Override
	public boolean isOpen() throws InvalidComunicationStateException {
		return false;
	}

	@Override
	public void initServer() throws InvalidComunicationStateException, BindException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void waitForConnection() throws InvalidComunicationStateException {
		// TODO Auto-generated method stub
		
	}

}
