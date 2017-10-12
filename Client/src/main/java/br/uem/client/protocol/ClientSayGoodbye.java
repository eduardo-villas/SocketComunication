package br.uem.client.protocol;

import java.io.IOException;

import br.uem.client.Client;
import br.uem.commons.comunication.InvalidComunicationStateException;

public class ClientSayGoodbye extends ClientGoodBye {

	private Client client;

	public ClientSayGoodbye(Client client) {
		super(client);
		this.client = client;
	}

	@Override
	public void doComunication() throws InvalidComunicationStateException, IOException {
		this.client.sendMessage("GOODBYE");
		this.client.setState(new ClientHearsGoodbye(this.client));
	}

}
