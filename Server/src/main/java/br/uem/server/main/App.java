package br.uem.server.main;

import java.net.BindException;

import javax.net.ServerSocketFactory;

import org.apache.log4j.Logger;

import br.uem.server.OperationRunner;
import br.uem.server.Server;
import br.uem.server.ServerRunner;

public class App {

	private static Logger logger = Logger.getLogger(App.class);
	private static int PORT = 3000;

	public static void main(String args[]) throws Exception {

		Server server = new Server(ServerSocketFactory.getDefault(), App.PORT);

		try {
			server.initServer();
		} catch (BindException bindException) {
			logger.error(String.format("Erro. porta %s em uso.", server.getPort()), bindException);
			throw bindException;
		}

		OperationRunner operationRunner = new OperationRunner() {

			@Override
			public void execute(Server server) throws Exception {
				int cont = 0;
				while (server.isOpen()) {
					logger.info(String.format("mensagem %d enviada pelo cliente %s", ++cont, server.getMessage()));
				}

			}

		};

		ServerRunner serverRunner = new ServerRunner(operationRunner);
		serverRunner.forever(server);

	}

}
