package br.uem.commons.stats;

import java.util.LinkedHashMap;
import java.util.Map;

public class Statistic {

	private Map<String, Long> stats = new LinkedHashMap<>();
	private long currentTimeTransfer = 0L;
	private String currentKey = null;
	
	public Statistic() {
		
	}

	public void initialize() {
		this.stats = new LinkedHashMap<>();
		this.currentTimeTransfer = 0L;
		this.currentKey = null;
	}

	public void analyze(ElapTime elapTime, String message) {

		if (message.startsWith("TestKey")) {
			if (currentTimeTransfer != 0) {
				stats.put(currentKey, currentTimeTransfer);
			}
			currentKey = message;
			currentTimeTransfer = 0L;
		}
		currentTimeTransfer += elapTime.elapTime();

	}

	public Map<String, Long> getStats() {
		finalyze();
		return this.stats;
	}

	private void finalyze() {
		this.stats.put(currentKey, currentTimeTransfer);
	}

}
