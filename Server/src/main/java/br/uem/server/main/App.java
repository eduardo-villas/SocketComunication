package br.uem.server.main;

import java.net.BindException;

import javax.net.ServerSocketFactory;

import org.apache.log4j.Logger;

import br.uem.commons.comunication.OperationRunner;
import br.uem.commons.comunication.ReceiverMessages;
import br.uem.commons.comunication.SenderReceiver;
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
			public void execute(SenderReceiver senderReceiver) throws Exception {

				ReceiverMessages receiverMessages = new ReceiverMessages(logger);
				receiverMessages.receive(senderReceiver);
			}

		};

		ServerRunner serverRunner = new ServerRunner(operationRunner);
		serverRunner.forever(server);

	}

}
