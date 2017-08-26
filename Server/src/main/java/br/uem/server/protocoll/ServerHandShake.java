package br.uem.server.protocoll;

import java.io.IOException;

import org.apache.log4j.Logger;

import br.uem.server.Server;

public class ServerHandShake implements ServerState {

	private Server server;
	private Logger logger = Logger.getLogger(ServerHandShake.class);
	
	public ServerHandShake(Server server) {
		this.server = server;
	}
	
	@Override
	public void initServer() throws InvalidServerStateException {
		logger.warn("O servidor esta esta executando o handshake.");
	}

	@Override
	public void waitForConnection() throws InvalidServerStateException {
		logger.warn("O servidor já estabeleceu a connexão com o cliente.");

	}

	String read() throws InvalidServerStateException, IOException {
		String clientMessage = server.getMessage();
		if (!"HEY".equalsIgnoreCase(clientMessage)){
			close();
			return null;
		}
		return clientMessage;
	}

	void write(String message) throws InvalidServerStateException, IOException {
		server.sendMessage("HEY");
		server.setState(new ServerReady(server));
	}

	public void close() throws InvalidServerStateException {
		server.closeResources();
		server.setState(new ServerWaiting(this.server));
	}

	@Override
	public boolean isOpen() throws InvalidServerStateException {
		return !server.getSocket().isClosed();
	}

	@Override
	public void doComunication() throws InvalidServerStateException, IOException {
		write(read());
	}

}
