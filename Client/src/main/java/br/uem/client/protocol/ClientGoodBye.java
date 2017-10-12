package br.uem.client.protocol;

import br.uem.client.Client;
import br.uem.commons.comunication.InvalidComunicationStateException;

public abstract class ClientGoodBye implements ClientState {

	private Client client;

	public ClientGoodBye(Client client) {
		this.client = client;
	}

	@Override
	public void runClient() throws InvalidComunicationStateException {

	}

	@Override
	public boolean isOpen() throws InvalidComunicationStateException {
		return client.getSocket() != null && !client.getSocket().isClosed();
	}

}
