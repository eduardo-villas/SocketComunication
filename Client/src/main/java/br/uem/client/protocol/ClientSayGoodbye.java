package br.uem.client.protocol;

import java.io.IOException;

import br.uem.client.Client;

public class ClientSayGoodbye extends ClientGoodBye {

	private Client client;

	public ClientSayGoodbye(Client client) {
		super(client);
		this.client = client;
	}

	@Override
	public void doComunication() throws InvalidClientStateException, IOException {
		this.client.sendMessage("GOODBYE");
		this.client.setState(new ClientHearsGoodbye(this.client));
	}

}
