package br.uem.client.protocol;

import java.io.IOException;

import br.uem.client.Client;
import br.uem.commons.comunication.InvalidComunicationStateException;

public class ClientError implements ClientState {

	private Client client;
	
	public ClientError(Client client) {
		this.client = client;
	}

	@Override
	public void runClient() throws InvalidComunicationStateException {
		this.client.setState(new ClientStoped(client));
		this.client.runClient();
	}

	@Override
	public boolean isOpen() throws InvalidComunicationStateException {
		return false;
	}

	void read() throws InvalidComunicationStateException, IOException {
		throw new InvalidComunicationStateException("Estado de cliente inválido para operação.");
	}

	void write() throws InvalidComunicationStateException, IOException {
		throw new InvalidComunicationStateException("Estado de cliente inválido para operação.");
	}

	@Override
	public void doComunication() throws InvalidComunicationStateException, IOException {
		write();
		read();
	}

}
