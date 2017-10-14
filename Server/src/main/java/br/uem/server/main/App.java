package br.uem.server.main;

import java.net.BindException;

import javax.net.ServerSocketFactory;

import org.apache.log4j.Logger;

import br.uem.commons.comunication.OperationRunner;
import br.uem.commons.comunication.SenderMessages;
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

				final int transferInMB = 1;
				
				SenderMessages senderMessages = new SenderMessages(logger, "new-stats-server-sender.log");
				
				senderMessages.sendBytes(senderReceiver, 32, 1, transferInMB);
				senderMessages.sendBytes(senderReceiver, 32, 2, transferInMB);
				senderMessages.sendBytes(senderReceiver, 32, 4, transferInMB);
				senderMessages.sendBytes(senderReceiver, 32, 8, transferInMB);
				senderMessages.sendBytes(senderReceiver, 32, 16, transferInMB);
				
				senderMessages.sendBytes(senderReceiver, 64, 1, transferInMB);
				senderMessages.sendBytes(senderReceiver, 64, 2, transferInMB);
				senderMessages.sendBytes(senderReceiver, 64, 4, transferInMB);
				senderMessages.sendBytes(senderReceiver, 64, 8, transferInMB);
				senderMessages.sendBytes(senderReceiver, 64, 16, transferInMB);

				senderMessages.sendBytes(senderReceiver, 128, 1, transferInMB);
				senderMessages.sendBytes(senderReceiver, 128, 2, transferInMB);
				senderMessages.sendBytes(senderReceiver, 128, 4, transferInMB);
				senderMessages.sendBytes(senderReceiver, 128, 8, transferInMB);
				senderMessages.sendBytes(senderReceiver, 128, 16, transferInMB);

			}

		};

		ServerRunner serverRunner = new ServerRunner(operationRunner);
		serverRunner.forever(server);

	}

}
