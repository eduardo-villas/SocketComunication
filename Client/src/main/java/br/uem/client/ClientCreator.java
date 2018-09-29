package br.uem.client;

import java.io.IOException;

import org.apache.log4j.Logger;

import br.uem.commons.comunication.OperationRunner;

public class ClientCreator {

	private int port;
	private OperationRunner operationRunner;
	private String ip;

	public ClientCreator(String ip, int port, OperationRunner operationRunner) {
		this.ip = ip;
		this.port = port;
		this.operationRunner = operationRunner;
	}
	
	public Client createClient() {

		final Client client ;
		try {
			client = new Client(ip, port);
		} catch (IOException ex) {
			String errorMessage = "Erro ao criar o cliente";
			Logger.getLogger(ClientCreator.class).error(errorMessage, ex);
			throw new RuntimeException(errorMessage, ex);
		}


		ClientRunner clientRunner = new ClientRunner(operationRunner);
		clientRunner.runClient(client);

		return client;
	}

}
