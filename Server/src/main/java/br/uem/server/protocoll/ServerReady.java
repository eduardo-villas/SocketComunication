package br.uem.server.protocoll;

import java.io.IOException;

import org.apache.log4j.Logger;

import br.uem.server.Server;

public class ServerReady implements ServerState {

	private Server server;
	private Logger logger = Logger.getLogger(ServerReady.class);
	
	public ServerReady(Server server) {
		this.server = server;
	}

	@Override
	public void initServer() throws InvalidServerStateException {
		logger.warn("O servidor já está pronto para enviar e receber mensagens");
	}

	@Override
	public void waitForConnection() throws InvalidServerStateException {
		logger.warn("O servidor já estabeleceu um conexão com o cliente");
	}

	@Override
	public boolean isOpen() throws InvalidServerStateException {
		return !server.getSocket().isClosed();
	}

	@Override
	public void doComunication() throws InvalidServerStateException, IOException {
		try {
			this.server.getOperationRunner().execute(this.server);
		} catch (Exception e) {
			logger.error("Ocorreu um erro na execução da operação.");
		} finally {
			this.server.setState(new ServerSayGoodbye(this.server));
		}
	}

}
