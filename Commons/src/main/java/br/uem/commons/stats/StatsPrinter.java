package br.uem.commons.stats;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.Map;

public class StatsPrinter {

	private String fileName;

	public StatsPrinter(String fileName) {
		this.fileName = fileName;
	}

	public void printStats(Statistic statistic) throws IOException {

		try (final PrintWriter printWriter = new PrintWriter(new FileWriter(fileName, true))) {
			Map<String, Long> stats = statistic.getStats();
			Iterator<String> iterator = stats.keySet().iterator();
			while (iterator.hasNext()) {

				String withoutKey = iterator.next();
				long timeTransferWithoutPacket = stats.get(withoutKey);

				String withKey = iterator.next();
				long timeTransferWithPacket = stats.get(withKey);

				printWriter.write(String.format("%s= %d\n", withoutKey, timeTransferWithoutPacket));
				printWriter.write(String.format("%s= %d\n", withKey, timeTransferWithPacket));
				printWriter.write(String.format("Percentual transferencia                                 %4.3f\n",
						((new Double(timeTransferWithPacket) / new Double(timeTransferWithoutPacket) * 100) - 100)));
			}

			printWriter.write("\n");

			printWriter.flush();
			printWriter.close();
			
		}
	}

}
