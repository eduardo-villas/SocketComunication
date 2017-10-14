package br.uem.server.main;

import java.net.BindException;

import javax.net.ServerSocketFactory;

import org.apache.log4j.Logger;

import br.uem.commons.comunication.OperationRunner;
import br.uem.commons.comunication.PacketCalculation;
import br.uem.commons.comunication.SenderMessages;
import br.uem.commons.comunication.SenderReceiver;
import br.uem.commons.stats.Statistic;
import br.uem.commons.stats.StatsPrinter;
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
				
				Statistic statistic = new Statistic();
				
				SenderMessages senderMessages = new SenderMessages(logger, statistic);
				
				statistic.initialize();
				senderMessages.sendBytes(senderReceiver, new PacketCalculation(32, 1, transferInMB));
				senderMessages.sendBytes(senderReceiver, new PacketCalculation(32, 2, transferInMB));
				senderMessages.sendBytes(senderReceiver, new PacketCalculation(32, 4, transferInMB));
				senderMessages.sendBytes(senderReceiver, new PacketCalculation(32, 8, transferInMB));
				senderMessages.sendBytes(senderReceiver, new PacketCalculation(32, 16, transferInMB));
				
				senderMessages.sendBytes(senderReceiver, new PacketCalculation(64, 1, transferInMB));
				senderMessages.sendBytes(senderReceiver, new PacketCalculation(64, 2, transferInMB));
				senderMessages.sendBytes(senderReceiver, new PacketCalculation(64, 4, transferInMB));
				senderMessages.sendBytes(senderReceiver, new PacketCalculation(64, 8, transferInMB));
				senderMessages.sendBytes(senderReceiver, new PacketCalculation(64, 16, transferInMB));

				senderMessages.sendBytes(senderReceiver, new PacketCalculation(128, 1, transferInMB));
				senderMessages.sendBytes(senderReceiver, new PacketCalculation(128, 2, transferInMB));
				senderMessages.sendBytes(senderReceiver, new PacketCalculation(128, 4, transferInMB));
				senderMessages.sendBytes(senderReceiver, new PacketCalculation(128, 8, transferInMB));
				senderMessages.sendBytes(senderReceiver, new PacketCalculation(128, 16, transferInMB));

				String fileLogName = "new-stats-server-sender.log";
				StatsPrinter statsPrinter = new StatsPrinter(fileLogName);
				statsPrinter.printStats(statistic);
				System.out.println("Servidor terminado. Estatisticas em " + statsPrinter.getFileLogName());
}

		};

		ServerRunner serverRunner = new ServerRunner(operationRunner);
		serverRunner.forever(server);

	}

}
