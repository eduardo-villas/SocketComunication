package br.uem.client.protocol;

import java.io.IOException;

import org.apache.log4j.Logger;

import br.uem.client.Client;
import br.uem.commons.comunication.InvalidComunicationStateException;

public class ClientReady implements ClientState {

	Logger logger = Logger.getLogger(ClientReady.class);
	private Client client;

	public ClientReady(Client client) {
		this.client = client;
	}

	@Override
	public void runClient() throws InvalidComunicationStateException {
		logger.warn("O Cliente já está rodando.");
	}

	@Override
	public boolean isOpen() throws InvalidComunicationStateException {
		return !client.getSocket().isClosed();
	}

	@Override
	public void doComunication() throws InvalidComunicationStateException, IOException {
		try {
			this.client.getOperationRunner().execute(this.client);
			this.client.setState(new ClientSayGoodbye(this.client));
		} catch (Exception e) {
			logger.error("Erro na execução da operação do cliente", e);
		} 
	}

}
