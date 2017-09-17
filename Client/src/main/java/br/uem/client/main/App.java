package br.uem.client.main;

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
				
				final int transferInMB = 500;
				sendBytes(client, 32, 1, transferInMB);
				sendBytes(client, 32, 2, transferInMB);
				sendBytes(client, 32, 4, transferInMB);
				sendBytes(client, 32, 8, transferInMB);
				sendBytes(client, 32, 16, transferInMB);
				
				sendBytes(client, 64, 1, transferInMB);
				sendBytes(client, 64, 2, transferInMB);
				sendBytes(client, 64, 4, transferInMB);
				sendBytes(client, 64, 8, transferInMB);
				sendBytes(client, 64, 16, transferInMB);

				sendBytes(client, 128, 1, transferInMB);
				sendBytes(client, 128, 2, transferInMB);
				sendBytes(client, 128, 4, transferInMB);
				sendBytes(client, 128, 8, transferInMB);
				sendBytes(client, 128, 16, transferInMB);
				
			}

			private void sendBytes(Client client, final int SIZE_MESSAGE, final int multiplyPacket, final int transferInMB) throws IOException {
				
				String message = String.join("", Collections.nCopies(SIZE_MESSAGE/8, "8bytesMS"));;
				final int SIZE_PACKAGE = 1024*multiplyPacket;
				final String TAG = "packet"+SIZE_PACKAGE; 
				ElapTime elapTime = new ElapTime();
				//(1638400 *  32) / (1024*1024) para mensagens de 32 bytes 50mb
//				final int TOTAL_MESSAGES = 1638400;
				final int TOTAL_MESSAGES = (transferInMB * (1024*1024)) / SIZE_MESSAGE;
				final int MB_TRANSFER = (TOTAL_MESSAGES *  SIZE_MESSAGE) / (1024*1024);// QUANTIDADE EM MB para mensagens de 32 bytes 50mb
				
				PrintWriter printWriter = new PrintWriter(new FileWriter("stats.log", true));
				ElapTime elapTimeTotal = new ElapTime();
				long timeTransferWithoutPacket = 0 ;
				elapTimeTotal.start();
				for (int i = 0 ;i < TOTAL_MESSAGES; ++i) {
					logger.info(String.format("enviando mensagem sem agregação %d: %s", i+1, message));
					elapTime.start();
					client.sendMessage(message);
					elapTime.finish();
					timeTransferWithoutPacket += elapTime.elapTime();
				}
				elapTime.finish();
				elapTimeTotal.finish();
				final long totalTimeWithoutPacket = elapTimeTotal.elapTime();
				logger.info(String.format("tempo de transmissao sem agregacao TAG:%s %d",TAG, timeTransferWithoutPacket));
				
				long timeTransferWithPacket = 0;
				elapTimeTotal.start();
				StringBuilder sb = new StringBuilder();
				for (int i = 0 ;i < TOTAL_MESSAGES; ++i) {
					logger.info(String.format("enviando mensagem com agregação %d: %s", i+1, message));
					sb.append(message);
					if (sb.length() >= SIZE_PACKAGE) {
						logger.info(String.format("Tamanho do pacote enviado %s", sb.length()));
						elapTime.start();
						client.sendMessage(sb.toString());
						elapTime.finish();
						timeTransferWithPacket += elapTime.elapTime();
						sb = new StringBuilder();
					}
				}
				if (!sb.toString().isEmpty()) {
					logger.info(String.format("Tamanho do pacote enviado %s", sb.length()));
					elapTime.start();
					client.sendMessage(sb.toString());
					elapTime.finish();
					timeTransferWithPacket += elapTime.elapTime();
				}
				elapTimeTotal.finish();
				final long totalTimeWithPacket = elapTimeTotal.elapTime();
				
				List<String> stats;

				stats = Lists.newArrayList("\nTeste",
						
						String.format("Quantidade de mensagens %s.", TOTAL_MESSAGES),
						String.format("Tamanho das mensagens %s bytes", SIZE_MESSAGE),
						String.format("Tamanho dos pacotes %s bytes", SIZE_PACKAGE),
						String.format("Total transferido %s MB", MB_TRANSFER),
						
						String.format("Tempo de transmissão em milisegundos sem agregação   %s", ElapTime.toMiliseconds(timeTransferWithoutPacket)),
						String.format("Tempo de transmissão em nanosegundos sem agregação   %s", timeTransferWithoutPacket),
						String.format("Tempo total em milisegundos sem agregação            %s", ElapTime.toMiliseconds(totalTimeWithoutPacket)),
						String.format("Tempo total em nanosegundos sem agregação            %s", totalTimeWithoutPacket),
						String.format("Tempo de transmissão em milisegundos com agregação   %s", ElapTime.toMiliseconds(timeTransferWithPacket)),
						String.format("Tempo de transmissão em nanosegundos com agregação   %s", timeTransferWithPacket),
						String.format("Tempo de total em milisegundos com agregação         %s", ElapTime.toMiliseconds(totalTimeWithPacket)),
						String.format("Tempo de total em nanosegundos com agregação         %s", totalTimeWithPacket),
						
						String.format("Percentual transferencia                            %4.3f", ((new Double(timeTransferWithPacket)/new Double(timeTransferWithoutPacket) * 100) - 100)),
						String.format("Percentual total                                    %4.3f", ((new Double(totalTimeWithPacket)/new Double(totalTimeWithoutPacket) * 100) - 100))
						);
				
				stats.stream().map(a -> a + "\n").forEach(printWriter::write);
				
				printWriter.flush();
				printWriter.close();
				elapTime.finish();
				logger.info(String.format("tempo de transmissao com agregacao TAG:%s %d",TAG, timeTransferWithPacket));
			}

		};

		ClientRunner clientRunner = new ClientRunner(operationRunner);
		Client client = new Client(App.SERVER_IP, App.SERVER_PORT);
		clientRunner.runClient(client);

	}
}
