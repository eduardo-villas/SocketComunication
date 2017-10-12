package br.uem.server.protocoll;

import java.io.IOException;

import br.uem.commons.comunication.InvalidComunicationStateException;
import br.uem.server.Server;

public class ServerSayGoodbye extends ServerGoodbye {

	public ServerSayGoodbye(Server server) {
		super(server);
	}

	@Override
	public void doComunication() throws InvalidComunicationStateException, IOException {
		write("GOODBYE");
	}

	private void write(String message) throws InvalidComunicationStateException, IOException {
		server.sendMessage("GOODBYE");
		close();
	}

	public void close() throws InvalidComunicationStateException {
		server.closeResources();
		server.setState(new ServerWaiting(server));
	}

}
