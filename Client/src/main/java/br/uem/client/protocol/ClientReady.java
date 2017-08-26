package br.uem.client.protocol;

import java.io.IOException;

import org.apache.log4j.Logger;

import br.uem.client.Client;

public class ClientReady implements ClientState {

	Logger logger = Logger.getLogger(ClientReady.class);
	private Client client;

	public ClientReady(Client client) {
		this.client = client;
	}

	@Override
	public void runClient() throws InvalidClientStateException {
		logger.warn("O Cliente já está rodando.");
	}

	@Override
	public boolean isOpen() throws InvalidClientStateException {
		return !client.getSocket().isClosed();
	}

	@Override
	public void doComunication() throws InvalidClientStateException, IOException {
		try {
			this.client.getOperationRunner().execute(this.client);
		} catch (Exception e) {
			logger.error("Erro na execução da operação do cliente", e);
		} finally {
			this.client.setState(new ClientSayGoodbye(this.client));
		}
	}

}
