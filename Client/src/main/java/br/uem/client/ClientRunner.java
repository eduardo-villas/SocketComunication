package br.uem.client;

import java.io.IOException;

import org.apache.log4j.Logger;

import com.google.common.base.Preconditions;

import br.uem.commons.comunication.InvalidComunicationStateException;
import br.uem.commons.comunication.OperationRunner;


public class ClientRunner {

	Logger logger = Logger.getLogger(ClientRunner.class);
	private OperationRunner operationRunner;

	public ClientRunner(OperationRunner operationRunner) {
		this.operationRunner = operationRunner;
	}

	public void runClient(Client client) {
		Preconditions.checkArgument(operationRunner != null, "Operatin runner não pode ser nulo");
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

	public void setOperation(OperationRunner operationRunner) {
		this.operationRunner = operationRunner;
		
	}

}
