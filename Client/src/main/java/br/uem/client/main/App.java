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
import br.uem.commons.comunication.InvalidComunicationStateException;
import br.uem.commons.comunication.OperationRunner;
import br.uem.commons.comunication.SenderReceiver;
import br.uem.commons.stats.ElapTime;

public class App {

	private static final int SERVER_PORT = 3000;
	private static final String SERVER_IP = "54.186.97.210";
//	private static final String SERVER_IP = "localhost";

	private static Logger logger = Logger.getLogger(App.class);

	public static void main(String args[]) throws InvalidComunicationStateException, InterruptedException, IOException {

		OperationRunner operationRunner = new OperationRunner() {

			@Override
			public void execute(SenderReceiver senderReceiver) throws Exception {
				
				final int transferInMB = 1;
				
				sendBytes(senderReceiver, 32, 1, transferInMB);
				sendBytes(senderReceiver, 32, 2, transferInMB);
				sendBytes(senderReceiver, 32, 4, transferInMB);
				sendBytes(senderReceiver, 32, 8, transferInMB);
				sendBytes(senderReceiver, 32, 16, transferInMB);
				
				sendBytes(senderReceiver, 64, 1, transferInMB);
				sendBytes(senderReceiver, 64, 2, transferInMB);
				sendBytes(senderReceiver, 64, 4, transferInMB);
				sendBytes(senderReceiver, 64, 8, transferInMB);
				sendBytes(senderReceiver, 64, 16, transferInMB);

				sendBytes(senderReceiver, 128, 1, transferInMB);
				sendBytes(senderReceiver, 128, 2, transferInMB);
				sendBytes(senderReceiver, 128, 4, transferInMB);
				sendBytes(senderReceiver, 128, 8, transferInMB);
				sendBytes(senderReceiver, 128, 16, transferInMB);
				
			}

			private void sendBytes(SenderReceiver senderReceiver, final int SIZE_MESSAGE, final int multiplyPacket, final int transferInMB) throws IOException {
				
				String message = String.join("", Collections.nCopies(SIZE_MESSAGE/8, "8bytesMS"));
				final int SIZE_PACKAGE = 1024*multiplyPacket;
				final String TAG = "packet"+SIZE_PACKAGE; 
				ElapTime elapTime = new ElapTime();
				//(1638400 *  32) / (1024*1024) para mensagens de 32 bytes 50mb
//				final int TOTAL_MESSAGES = 1638400;
				final int TOTAL_MESSAGES = (transferInMB * (1024*1024)) / SIZE_MESSAGE;
				final int MB_TRANSFER = (TOTAL_MESSAGES *  SIZE_MESSAGE) / (1024*1024);// QUANTIDADE EM MB para mensagens de 32 bytes 50mb
				
				PrintWriter printWriter = new PrintWriter(new FileWriter("stats10.log", true));
				ElapTime elapTimeTotal = new ElapTime();
				long timeTransferWithoutPacket = 0 ;
				senderReceiver.sendMessage(String.format("TestKey:type=%s: sizemessage=%d: packet=%d", "withoutpacket", SIZE_MESSAGE, SIZE_PACKAGE));
				elapTimeTotal.start();
				for (int i = 0 ;i < TOTAL_MESSAGES; ++i) {
					logger.info(String.format("enviando mensagem sem agregação %d: %s", i+1, message));
					elapTime.start();
					senderReceiver.sendMessage(message);
					elapTime.finish();
					timeTransferWithoutPacket += elapTime.elapTime();
				}
				elapTime.finish();
				elapTimeTotal.finish();
				final long totalTimeWithoutPacket = elapTimeTotal.elapTime();
				logger.info(String.format("tempo de transmissao sem agregacao TAG:%s %d",TAG, timeTransferWithoutPacket));
				
				long timeTransferWithPacket = 0;
				elapTimeTotal.start();
				senderReceiver.sendMessage(String.format("TestKey:type=   %s: sizemessage=%d: packet=%d", "withpacket", SIZE_MESSAGE, SIZE_PACKAGE));
				StringBuilder sb = new StringBuilder();
				for (int i = 0 ;i < TOTAL_MESSAGES; ++i) {
					logger.info(String.format("enviando mensagem com agregação %d: %s", i+1, message));
					sb.append(message);
					if (sb.length() >= SIZE_PACKAGE) {
						logger.info(String.format("Tamanho do pacote enviado %s", sb.length()));
						elapTime.start();
						senderReceiver.sendMessage(sb.toString());
						elapTime.finish();
						timeTransferWithPacket += elapTime.elapTime();
						sb = new StringBuilder();
					}
				}
				if (!sb.toString().isEmpty()) {
					logger.info(String.format("Tamanho do pacote enviado %s", sb.length()));
					elapTime.start();
					senderReceiver.sendMessage(sb.toString());
					elapTime.finish();
					timeTransferWithPacket += elapTime.elapTime();
				}
				elapTimeTotal.finish();
				final long totalTimeWithPacket = elapTimeTotal.elapTime();
				
				List<String> stats;

				stats = Lists.newArrayList("\nTeste",
						
						String.format("Quantidade de mensagens %s.", TOTAL_MESSAGES),
						String.format("Tamanho das mensagens %s bytes **", SIZE_MESSAGE),
						String.format("Tamanho dos pacotes %s bytes **", SIZE_PACKAGE),
						String.format("Total transferido %s MB", MB_TRANSFER),
						
						"\nSEM AGREGAÇÃO",
						String.format("Tempo de transmissão em milisegundos agregação   %s", ElapTime.toMiliseconds(timeTransferWithoutPacket)),
						String.format("Tempo de transmissão em nanosegundos agregação   %s", timeTransferWithoutPacket),
						String.format("Tempo total em milisegundos agregação            %s", ElapTime.toMiliseconds(totalTimeWithoutPacket)),
						String.format("Tempo total em nanosegundos agregação            %s", totalTimeWithoutPacket),
						"\nCOM AGREGAÇÃO",
						String.format("Tempo de transmissão em milisegundos agregação   %s", ElapTime.toMiliseconds(timeTransferWithPacket)),
						String.format("Tempo de transmissão em nanosegundos agregação   %s", timeTransferWithPacket),
						String.format("Tempo de total em milisegundos agregação         %s", ElapTime.toMiliseconds(totalTimeWithPacket)),
						String.format("Tempo de total em nanosegundos agregação         %s", totalTimeWithPacket),
						"\nPERCENTUAL DE DESEMPENHO",
						String.format("Percentual transferencia                            %4.3f", ((new Double(timeTransferWithPacket)/new Double(timeTransferWithoutPacket) * 100) - 100)),
						String.format("Percentual total                                    %4.3f", ((new Double(totalTimeWithPacket)/new Double(totalTimeWithoutPacket) * 100) - 100)),
						"----------------------------------------------------------\n");
				
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
