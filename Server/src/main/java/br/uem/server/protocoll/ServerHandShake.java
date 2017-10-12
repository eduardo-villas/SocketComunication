package br.uem.server.protocoll;

import java.io.IOException;

import org.apache.log4j.Logger;

import br.uem.commons.comunication.Constants;
import br.uem.commons.comunication.InvalidComunicationStateException;
import br.uem.server.Server;

public class ServerHandShake implements ServerState {

	private Server server;
	private Logger logger = Logger.getLogger(ServerHandShake.class);
	
	public ServerHandShake(Server server) {
		this.server = server;
	}
	
	@Override
	public void initServer() throws InvalidComunicationStateException {
		logger.warn("O servidor esta esta executando o handshake.");
	}

	@Override
	public void waitForConnection() throws InvalidComunicationStateException {
		logger.warn("O servidor já estabeleceu a connexão com o cliente.");

	}

	String read() throws InvalidComunicationStateException, IOException {
		String clientMessage = server.getMessage();
		if (!Constants.HEY.equalsIgnoreCase(clientMessage)){
			close();
			return null;
		}
		return clientMessage;
	}

	void write(String message) throws InvalidComunicationStateException, IOException {
		server.sendMessage("HEY");
		server.setState(new ServerReady(server));
	}

	public void close() throws InvalidComunicationStateException {
		server.closeResources();
		server.setState(new ServerWaiting(this.server));
	}

	@Override
	public boolean isOpen() throws InvalidComunicationStateException {
		return !server.getSocket().isClosed();
	}

	@Override
	public void doComunication() throws InvalidComunicationStateException, IOException {
		write(read());
	}

}
