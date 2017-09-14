package br.uem.client.main;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.List;

import org.apache.log4j.Logger;

import com.google.common.collect.Lists;

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
				
				final int SIZE_MESSAGE = 8*8;
				String message = String.join("", Collections.nCopies(SIZE_MESSAGE/8, "8bytesMS"));;
				final int SIZE_PACKAGE = 1024*4;
				final String TAG = "packet"+SIZE_PACKAGE; 
				ElapTime elapTime = new ElapTime();
				
				final int TOTAL_MESSAGES = 500000;
				
				PrintWriter printWriter = new PrintWriter(new FileWriter("stats.txt", true));
				
				long timeWithoutPacket = 0 ;
				for (int i = 0 ;i < TOTAL_MESSAGES; ++i) {
					logger.info(String.format("enviando mensagem %d: %s", i+1, message));
					elapTime.start();
					client.sendMessage(message);
					elapTime.finish();
					timeWithoutPacket += elapTime.elapTime();
				}
				elapTime.finish();
				logger.info(String.format("tempo de transmissao sem agregacao TAG:%s %d",TAG, timeWithoutPacket));
				
				long timeWithPacket = 0;
				StringBuilder sb = new StringBuilder();
				for (int i = 0 ;i < TOTAL_MESSAGES; ++i) {
					logger.info(String.format("enviando mensagem %d: %s", i+1, message));
					sb.append(message);
					if (sb.length() >= SIZE_PACKAGE) {
						logger.info(String.format("Tamanho do pacote enviado %s", sb.length()));
						elapTime.start();
						client.sendMessage(sb.toString());
						elapTime.finish();
						timeWithPacket += elapTime.elapTime();
						sb = new StringBuilder();
					}
				}
				if (!sb.toString().isEmpty()) {
					logger.info(String.format("Tamanho do pacote enviado %s", sb.length()));
					elapTime.start();
					client.sendMessage(sb.toString());
					elapTime.finish();
					timeWithPacket += elapTime.elapTime();
				}
				
				List<String> stats;
				long perc = ((timeWithPacket/timeWithoutPacket)*100);
				stats = Lists.newArrayList("Teste",
						String.format("Quantidade de mensagens %s.", TOTAL_MESSAGES),
						String.format("Tamanho das mensagens %s bytes", SIZE_MESSAGE),
						String.format("Tamanho dos pacotes %s bytes", SIZE_PACKAGE),
						String.format("Tempo de transmissão em milisegundos sem agregação %s", ElapTime.toMiliseconds(timeWithoutPacket)),
						String.format("Tempo de transmissão em milisegundos com agregação %s", ElapTime.toMiliseconds(timeWithPacket)),
						String.format("Percentual de  %f%5", perc)
						);
				
				stats.stream().map(a -> a + "\n").forEach(printWriter::write);
				
				printWriter.flush();
				printWriter.close();
				elapTime.finish();
				logger.info(String.format("tempo de transmissao com agregacao TAG:%s %d",TAG, timeWithPacket));
				
			}

		};

		ClientRunner clientRunner = new ClientRunner(operationRunner);
		Client client = new Client(App.SERVER_IP, App.SERVER_PORT);
		clientRunner.runClient(client);

	}
}
