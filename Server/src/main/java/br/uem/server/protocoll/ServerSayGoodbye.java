package br.uem.server.protocoll;

import java.io.IOException;

import br.uem.server.Server;

public class ServerSayGoodbye extends ServerGoodbye {

	public ServerSayGoodbye(Server server) {
		super(server);
	}

	@Override
	public void doComunication() throws InvalidServerStateException, IOException {
		write("GOODBYE");
	}

	private void write(String message) throws InvalidServerStateException, IOException {
		server.sendMessage("GOODBYE");
		close();
	}

	public void close() throws InvalidServerStateException {
		server.closeResources();
		server.setState(new ServerWaiting(server));
	}

}
