package br.uem.server.protocoll;

import java.io.IOException;

import org.apache.log4j.Logger;

import br.uem.commons.comunication.InvalidComunicationStateException;
import br.uem.server.Server;

public class ServerHearsGoodbye extends ServerGoodbye {

	private Logger logger = Logger.getLogger(ServerHearsGoodbye.class);
	private Server server;

	public ServerHearsGoodbye(Server server) {
		super(server);
		this.server = server;
	}

	@Override
	public void doComunication() throws InvalidComunicationStateException, IOException {
		String message = this.server.getMessage();
		logger.info("finalizando conexão "+ message);
		this.server.closeResources();
		this.server.setState(new ServerWaiting(this.server));
	}

}
