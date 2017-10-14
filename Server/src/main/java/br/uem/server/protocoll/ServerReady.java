package br.uem.server.protocoll;

import java.io.IOException;

import org.apache.log4j.Logger;

import br.uem.commons.comunication.InvalidComunicationStateException;
import br.uem.server.Server;

public class ServerReady implements ServerState {

	private Server server;
	private Logger logger = Logger.getLogger(ServerReady.class);
	
	public ServerReady(Server server) {
		this.server = server;
	}

	@Override
	public void initServer() throws InvalidComunicationStateException {
		logger.warn("O servidor já está pronto para enviar e receber mensagens");
	}

	@Override
	public void waitForConnection() throws InvalidComunicationStateException {
		logger.warn("O servidor já estabeleceu um conexão com o cliente");
	}

	@Override
	public boolean isOpen() throws InvalidComunicationStateException {
		return !server.getSocket().isClosed();
	}

	@Override
	public void doComunication() throws InvalidComunicationStateException, IOException {
		try {
			this.server.getOperationRunner().execute(this.server);
			this.server.setState(new ServerSayGoodbye(this.server));
		} catch (Exception e) {
			logger.error("Erro na execução da operação do cliente", e);
		} 
	}

}
