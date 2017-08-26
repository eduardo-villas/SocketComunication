package br.uem.server.protocoll;

import java.net.Socket;

import br.uem.server.Server;

public abstract class ServerGoodbye implements ServerState {

	protected Server server;
	public ServerGoodbye(Server server) {
		this.server = server;
	}

	@Override
	public void initServer() throws InvalidServerStateException {

	}

	@Override
	public void waitForConnection() throws InvalidServerStateException {

	}

	public void close() throws InvalidServerStateException {
		server.closeResources();
		server.setState(new ServerWaiting(server));
	}

	@Override
	public boolean isOpen() throws InvalidServerStateException {
		Socket socket = server.getSocket();
		return socket != null && !socket.isClosed();
	}

}
