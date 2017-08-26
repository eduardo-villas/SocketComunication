package br.uem.client.protocol;

import br.uem.client.Client;

public abstract class ClientGoodBye implements ClientState {

	private Client client;

	public ClientGoodBye(Client client) {
		this.client = client;
	}

	@Override
	public void runClient() throws InvalidClientStateException {

	}

	@Override
	public boolean isOpen() throws InvalidClientStateException {
		return client.getSocket() != null && !client.getSocket().isClosed();
	}

}
