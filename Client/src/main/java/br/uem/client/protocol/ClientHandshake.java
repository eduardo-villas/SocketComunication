package br.uem.client.protocol;

import java.io.IOException;

import org.apache.log4j.Logger;

import br.uem.client.Client;
import br.uem.commons.comunication.Constants;
import br.uem.commons.comunication.InvalidComunicationStateException;

public class ClientHandshake implements ClientState {

	private Client client;
	private Logger logger = Logger.getLogger(ClientHandshake.class);

	public ClientHandshake(Client client) {
		this.client = client;
	}

	@Override
	public void runClient() throws InvalidComunicationStateException {

	}

	void read() throws InvalidComunicationStateException, IOException {
		String clientMessage = client.getMessage();
		logger.info("Servidor respondeu: " + clientMessage);
		if (!Constants.HEY.equalsIgnoreCase(clientMessage)) {
			this.client.closeResources();
			this.client.setState(new ClientClosed());
			return;
		}
		
		client.setState(new ClientReady(client));
	}

	void write() throws InvalidComunicationStateException, IOException {
		logger.info("Cliente começando a comunicação com: 'HEY'");
		client.sendMessage("HEY");
	}

	@Override
	public void doComunication() throws InvalidComunicationStateException, IOException {
		write();
		read();
	}

	@Override
	public boolean isOpen() throws InvalidComunicationStateException {
		return !this.client.getSocket().isClosed();
	}

}
