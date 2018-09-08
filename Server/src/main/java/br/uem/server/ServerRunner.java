package br.uem.server;

import java.io.IOException;

import org.apache.log4j.Logger;

import br.uem.commons.comunication.InvalidComunicationStateException;
import br.uem.commons.comunication.OperationRunner;

public class ServerRunner {

	Logger logger = Logger.getLogger(ServerRunner.class);
	private OperationRunner operationRunner;

	public ServerRunner(OperationRunner operationRunner) {
		this.operationRunner = operationRunner;
	}
	
	public void forever(Server server) {
		server.setOperationRunner(operationRunner);
		while (!server.getHasError()) {
			try {
				server.waitForConnection();
				doComunication(server);
			} catch (InvalidComunicationStateException e) {
				logger.error("Ocorreu um erro na comunicação com o cliente.", e);
			}
		}
		logger.info("Servidor sendo finalizado.");
	}

	private void doComunication(Server server) {
		try {
			while (server.isOpen()) {
				server.doComunication();
			}
		} catch (InvalidComunicationStateException e) {
			server.setHasError(true);
			logger.error("Erro na comunicacao com o cliente ", e);
		} catch (IOException e) {
			server.setHasError(true);
			logger.error("Erro na comunicacao com o cliente ", e);
		}
	}

}
