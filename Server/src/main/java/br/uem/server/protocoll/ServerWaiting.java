package br.uem.server.protocoll;

import java.io.IOException;

import org.apache.log4j.Logger;

import br.uem.server.Server;

public class ServerWaiting implements ServerState {

	Logger logger = Logger.getLogger(ServerWaiting.class);
	private Server server;

	public ServerWaiting(Server server) {
		this.server = server;
	}

	@Override
	public void initServer() throws InvalidServerStateException {
		logger.warn("O servidor já foi iniciado.");
	}

	@Override
	public void waitForConnection() throws InvalidServerStateException {
		try {
			server.accept();
			server.initializeIOBuffers();
			server.setState(new ServerHandShake(server));
		} catch (IOException e) {
			logger.error("Erro ao estabelecer connexão com o cliente.", e);
		}

	}

	String read() throws InvalidServerStateException, IOException {
		throw new InvalidServerStateException(
				"O servidor esta aguardando connexões impossivel ler mensagens do cliente");
	}

	void write(String message) throws InvalidServerStateException, IOException {
		throw new InvalidServerStateException(
				"O servidor esta aguardando connexões impossivel enviar mensagens para o cliente");
	}

	@Override
	public boolean isOpen() throws InvalidServerStateException {
		return false;
	}

	@Override
	public void doComunication() throws InvalidServerStateException, IOException {
		write(read());
	}

}
