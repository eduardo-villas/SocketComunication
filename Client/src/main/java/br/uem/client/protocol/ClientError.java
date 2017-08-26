package br.uem.client.protocol;

import java.io.IOException;

import br.uem.client.Client;

public class ClientError implements ClientState {

	private Client client;
	
	public ClientError(Client client) {
		this.client = client;
	}

	@Override
	public void runClient() throws InvalidClientStateException {
		this.client.setState(new ClientStoped(client));
		this.client.runClient();
	}

	@Override
	public boolean isOpen() throws InvalidClientStateException {
		return false;
	}

	void read() throws InvalidClientStateException, IOException {
		throw new InvalidClientStateException("Estado de cliente inválido para operação.");
	}

	void write() throws InvalidClientStateException, IOException {
		throw new InvalidClientStateException("Estado de cliente inválido para operação.");
	}

	@Override
	public void doComunication() throws InvalidClientStateException, IOException {
		write();
		read();
	}

}
