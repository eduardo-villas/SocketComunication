package br.uem.commons.comunication;

import java.io.IOException;

import org.apache.log4j.Logger;

import br.uem.commons.stats.ElapTime;
import br.uem.commons.stats.Statistic;

public class SenderMessages {

	private Logger logger;
	private Statistic statistic;
	private ElapTime elapTime = new ElapTime();

	public SenderMessages(Logger logger, Statistic statistic) {
		this.logger = logger;
		this.statistic = statistic;
	}

	final public void sendBytes(SenderReceiver senderReceiver, PacketCalculation packetCalculation) throws IOException {

		senderReceiver.sendMessage(packetCalculation.getKeyMessageWithout());
		statistic.analyze(elapTime, packetCalculation.getKeyMessageWithout());
		
		for (int i = 0; i < packetCalculation.getTOTAL_MESSAGES(); ++i) {
			elapTime.start();
			senderReceiver.sendMessage(packetCalculation.getMessage());
			elapTime.finish();
			statistic.analyze(elapTime, packetCalculation.getMessage());
		}

		senderReceiver.sendMessage(packetCalculation.getKeyMessageWith());
		statistic.analyze(elapTime, packetCalculation.getKeyMessageWith());
		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < packetCalculation.getTOTAL_MESSAGES(); ++i) {

			sb.append(packetCalculation.getMessage());
			if (sb.length() >= packetCalculation.getSIZE_PACKAGE()) {
				logger.info(String.format("Tamanho do pacote enviado %s", sb.length()));
				elapTime.start();
				senderReceiver.sendMessage(sb.toString());
				elapTime.finish();
				statistic.analyze(elapTime, sb.toString());
				sb = new StringBuilder();
			}
		}

		if (!sb.toString().isEmpty()) {
			logger.info(String.format("Tamanho do pacote enviado %s", sb.length()));
			elapTime.start();
			senderReceiver.sendMessage(sb.toString());
			elapTime.finish();
			statistic.analyze(elapTime, packetCalculation.getMessage());
		}

	}

}
