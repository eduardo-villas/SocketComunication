package br.uem.client.main;

import java.io.IOException;
import java.util.Calendar;

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
				
				String message = "8bytesMS";
				final int SIZE_PACKAGE = 4096*2;
				final String TAG = "pack"+SIZE_PACKAGE; 
				
				long start = now();
				int TOTAL_MESSAGES = 100000;
				
				for (int i = 0 ;i < TOTAL_MESSAGES; ++i) {
					logger.info(String.format("enviando mensagem %d: %s", i+1, message));
					client.sendMessage(message);
				}
				long finish = now();
				logger.info(String.format("tempo de transmissao sem agregacao TAG:%s %d",TAG, finish - start));
				
				start = now();
				StringBuilder sb = new StringBuilder();
				for (int i = 0 ;i < TOTAL_MESSAGES; ++i) {
					logger.info(String.format("enviando mensagem %d: %s", i+1, message));
					sb.append(message);
					if ((i+1) * 8 == 2048) {
						client.sendMessage(sb.toString());
						sb = new StringBuilder();
					}
				}
				client.sendMessage(sb.toString());
				finish = now();
				logger.info(String.format("tempo de transmissao com agregacao TAG:%s %d",TAG, finish - start));
				
			}

			private long now() {
				return Calendar.getInstance().getTime().getTime();
			}

		};

		ClientRunner clientRunner = new ClientRunner(operationRunner);
		Client client = new Client(App.SERVER_IP, App.SERVER_PORT);
		clientRunner.runClient(client);

	}
}
