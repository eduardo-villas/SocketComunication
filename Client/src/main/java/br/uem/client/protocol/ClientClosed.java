package br.uem.client.protocol;

import java.io.IOException;

import br.uem.commons.comunication.InvalidComunicationStateException;

public class ClientClosed implements ClientState {

	public ClientClosed() {

	}

	@Override
	public void runClient() throws InvalidComunicationStateException {
		throw new InvalidComunicationStateException("Cliente fechado. Impossível rodar o cliente.");
	}

	@Override
	public void doComunication() throws InvalidComunicationStateException, IOException {
		throw new InvalidComunicationStateException("O cliente fechado. Impossível enviar mensagens.");
	}

	@Override
	public boolean isOpen() throws InvalidComunicationStateException {
		return false;
	}

}
