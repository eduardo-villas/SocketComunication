package br.uem.client.main;

import java.io.IOException;

import org.apache.log4j.Logger;

import br.uem.client.Client;
import br.uem.client.ClientRunner;
import br.uem.commons.comunication.InvalidComunicationStateException;
import br.uem.commons.comunication.OperationRunner;
import br.uem.commons.comunication.ReceiverMessages;
import br.uem.commons.comunication.SenderReceiver;

public class App {

	private static final int SERVER_PORT = 3000;
//	private static final String SERVER_IP = "54.186.97.210";
	private static final String SERVER_IP = "localhost";

	private static Logger logger = Logger.getLogger(App.class);

	public static void main(String args[]) throws InvalidComunicationStateException, InterruptedException, IOException {

		OperationRunner operationRunner = new OperationRunner() {

			@Override
			public void execute(SenderReceiver senderReceiver) throws Exception {
				
				ReceiverMessages receiverMessages = new ReceiverMessages(logger, "new-stats-client-receiver.log");
				receiverMessages.receive(senderReceiver);
				
			}

		};

		ClientRunner clientRunner = new ClientRunner(operationRunner);
		Client client = new Client(App.SERVER_IP, App.SERVER_PORT);
		clientRunner.runClient(client);

	}
}
