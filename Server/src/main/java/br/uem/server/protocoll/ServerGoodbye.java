package br.uem.server.protocoll;

import java.net.Socket;

import br.uem.commons.comunication.InvalidComunicationStateException;
import br.uem.server.Server;

public abstract class ServerGoodbye implements ServerState {

	protected Server server;
	public ServerGoodbye(Server server) {
		this.server = server;
	}

	@Override
	public void initServer() throws InvalidComunicationStateException {

	}

	@Override
	public void waitForConnection() throws InvalidComunicationStateException {

	}

	@Override
	public boolean isOpen() throws InvalidComunicationStateException {
		Socket socket = server.getSocket();
		return socket != null && !socket.isClosed();
	}

}
