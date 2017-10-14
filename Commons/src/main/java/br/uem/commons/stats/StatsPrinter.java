package br.uem.commons.stats;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;

public class StatsPrinter {

	private String fileLogName;

	public StatsPrinter(String fileLogName) {
		this.fileLogName = fileLogName;
	}

	public void printStats(Statistic statistic) throws IOException, Exception {
		
		try (final PrintWriter printWriter = new PrintWriter(new FileWriter(fileLogName, true))) {
			
			List<StatisticInfo> stats = statistic.getStats();
			Iterator<StatisticInfo> iterator = stats.iterator();
			
			while (iterator.hasNext()) {

				StatisticInfo statisticInfo1 = iterator.next();
				long timeTransferWithoutPacket = statisticInfo1.getTimeTransfer();
				long timeTransferWithoutPacketTotal = statisticInfo1.getTimeTotalTransfer();

				StatisticInfo statisticInfo2 = iterator.next();
				long timeTransferWithPacket = statisticInfo2.getTimeTransfer();
				long timeTransferWithPacketTotal = statisticInfo2.getTimeTotalTransfer();

				printStatistic(printWriter, statisticInfo1, timeTransferWithoutPacket, statisticInfo2,
						timeTransferWithPacket, "Percentual transferencia");

				printStatistic(printWriter, statisticInfo1, timeTransferWithoutPacketTotal, statisticInfo2,
						timeTransferWithPacketTotal, "Percentual transferencia total");
				
				printWriter.write("\n");

			}

			printWriter.write("\n");

			printWriter.flush();
			printWriter.close();
			
		}
	}

	private void printStatistic(final PrintWriter printWriter, StatisticInfo statisticInfo1,
			long timeTransferWithoutPacket, StatisticInfo statisticInfo2, long timeTransferWithPacket,
			String title) {
//		printWriter.write(title+"\n");
		printWriter.write(String.format("%s= %d\n", statisticInfo1.getKeyInfo(), timeTransferWithoutPacket));
		printWriter.write(String.format("%s= %d\n", statisticInfo2.getKeyInfo(), timeTransferWithPacket));
		printWriter.write(String.format("%s                                 %4.3f\n", title,
				((new Double(timeTransferWithPacket) / new Double(timeTransferWithoutPacket) * 100) - 100)));
	}

	public String getFileLogName() {
		return this.fileLogName;
	}

}
