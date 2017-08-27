package br.uem.client.protocol;

import java.io.IOException;

import org.apache.log4j.Logger;

import br.uem.client.Client;

public class ClientHandshake implements ClientState {

	private Client client;
	private Logger logger = Logger.getLogger(ClientHandshake.class);

	public ClientHandshake(Client client) {
		this.client = client;
	}

	@Override
	public void runClient() throws InvalidClientStateException {

	}

	void read() throws InvalidClientStateException, IOException {
		String clientMessage = client.getMessage();
		logger.info("Servidor respondeu: " + clientMessage);
		if (!"HEY".equalsIgnoreCase(clientMessage)) {
			this.client.closeResources();
			this.client.setState(new ClientClosed());
			return;
		}
		
		client.setState(new ClientReady(client));
	}

	void write() throws InvalidClientStateException, IOException {
		logger.info("Cliente começando a comunicação com: 'HEY'");
		client.sendMessage("HEY");
	}

	@Override
	public void doComunication() throws InvalidClientStateException, IOException {
		write();
		read();
	}

	@Override
	public boolean isOpen() throws InvalidClientStateException {
		return !this.client.getSocket().isClosed();
	}

}
