package br.uem.server.main;

import java.net.BindException;

import javax.net.ServerSocketFactory;

import org.apache.log4j.Logger;

import br.uem.commons.comunication.OperationRunner;
import br.uem.commons.comunication.SenderReceiver;
import br.uem.commons.stats.ElapTime;
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

			Statistic statistic = new Statistic();
			ElapTime elapTime = new ElapTime();

			@Override
			public void execute(SenderReceiver senderReceiver) throws Exception {

				int cont = 0;
				this.statistic.initialize();

				while (senderReceiver.isOpen()) {

					try {

						elapTime.start();
						String message = senderReceiver.getMessage();
						elapTime.finish();
						statistic.analyze(elapTime, message);
						logger.info(String.format("mensagem %d enviada pelo cliente %s", ++cont, message));

					} catch (Exception e) {

						StatsPrinter statsPrinter = new StatsPrinter("new-stats-server.log");
						statsPrinter.printStats(statistic);
						throw e;

					}
				}
			}

		};

		ServerRunner serverRunner = new ServerRunner(operationRunner);
		serverRunner.forever(server);

	}

}
