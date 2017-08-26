package br.uem.client.protocol;

import java.io.IOException;

public class ClientClosed implements ClientState {

	public ClientClosed() {

	}

	@Override
	public void runClient() throws InvalidClientStateException {
		throw new InvalidClientStateException("Cliente fechado. Impossível rodar o cliente.");
	}

	@Override
	public void doComunication() throws InvalidClientStateException, IOException {
		throw new InvalidClientStateException("O cliente fechado. Impossível enviar mensagens.");
	}

	@Override
	public boolean isOpen() throws InvalidClientStateException {
		return false;
	}

}
