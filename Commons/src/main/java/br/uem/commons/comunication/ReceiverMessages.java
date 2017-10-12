package br.uem.commons.comunication;

import java.io.IOException;

import org.apache.log4j.Logger;

import br.uem.commons.stats.ElapTime;
import br.uem.commons.stats.Statistic;
import br.uem.commons.stats.StatsPrinter;

public class ReceiverMessages {

	private Logger logger;
	private Statistic statistic = new Statistic();
	private ElapTime elapTime = new ElapTime();

	public ReceiverMessages(Logger logger) {
		this.logger = logger;
	}

	final public void receive(SenderReceiver senderReceiver) throws InvalidComunicationStateException, IOException, Exception {
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
	
	
}
