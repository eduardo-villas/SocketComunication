package br.uem.client.main;

import java.io.IOException;

import org.apache.log4j.Logger;

import br.uem.client.Client;
import br.uem.client.ClientRunner;
import br.uem.client.OperationRunner;
import br.uem.client.protocol.InvalidClientStateException;

public class App {

	private static final int SERVER_PORT = 3000;
	private static final String SERVER_IP = "localhost";

	private static Logger logger = Logger.getLogger(App.class);

	public static void main(String args[]) throws InvalidClientStateException, InterruptedException, IOException {

		OperationRunner operationRunner = new OperationRunner() {

			@Override
			public void execute(Client client) throws Exception {
				String message = "mensagem do cliente";

				logger.info("enviando mensagem " + message);
				client.sendMessage(message);

				logger.info("enviando mensagem " + message);
				client.sendMessage(message);

				logger.info("enviando mensagem " + message);
				client.sendMessage(message);

				logger.info("enviando mensagem " + message);
				client.sendMessage(message);

				logger.info("enviando mensagem " + message);
				client.sendMessage(message);

				logger.info("enviando mensagem " + message);
				client.sendMessage(message);

			}

		};

		ClientRunner clientRunner = new ClientRunner(operationRunner);
		Client client = new Client(App.SERVER_IP, App.SERVER_PORT);
		clientRunner.runClient(client);

	}
}
