package br.uem.server.protocoll;

import java.io.IOException;

import br.uem.commons.comunication.Constants;
import br.uem.commons.comunication.InvalidComunicationStateException;
import br.uem.server.Server;

public class ServerSayGoodbye extends ServerGoodbye {

	public ServerSayGoodbye(Server server) {
		super(server);
	}

	@Override
	public void doComunication() throws InvalidComunicationStateException, IOException {
		this.server.sendMessage(Constants.GOODBYE);
		this.server.setState(new ServerHearsGoodbye(this.server));
	}

}
