package br.uem.client;

import java.io.IOException;

import org.apache.log4j.Logger;

import br.uem.commons.comunication.InvalidComunicationStateException;
import br.uem.commons.comunication.OperationRunner;

public class ClientRunner {

	Logger logger = Logger.getLogger(ClientRunner.class);
	private OperationRunner operationRunner;

	public ClientRunner(OperationRunner operationRunner) {
		this.operationRunner = operationRunner;
	}

	public void runClient(Client client) {
		client.setOperationRunner(operationRunner);
		try {
			client.runClient();
			doComunication(client);
			logger.info("Terminando a comunicação com o servidor");
		} catch (InvalidComunicationStateException e) {
			logger.error("Ocorreu um erro na comunicação com o cliente.", e);
		}
		logger.info("Cliente sendo finalizado.");
	}

	private void doComunication(Client client) {
		try {
			while (client.isOpen()) {
				client.doComunication();
			}
		} catch (InvalidComunicationStateException e) {
			logger.error("Erro na comunicacao com o cliente ", e);
		} catch (IOException e) {
			logger.error("Erro na comunicacao com o cliente ", e);
		}
	}

}
