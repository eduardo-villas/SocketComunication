package br.uem.client.protocol;

import java.io.IOException;

import org.apache.log4j.Logger;

import br.uem.client.Client;

public class ClientHearsGoodbye extends ClientGoodBye {

	private Logger logger = Logger.getLogger(ClientHearsGoodbye.class);
	private Client client;

	public ClientHearsGoodbye(Client client) {
		super(client);
		this.client = client;
	}

	@Override
	public void doComunication() throws InvalidClientStateException, IOException {
		String message = this.client.getMessage();
		logger.info("finalizando conexão "+ message);
		this.client.closeResources();
		this.client.setState(new ClientClosed());
	}

}
