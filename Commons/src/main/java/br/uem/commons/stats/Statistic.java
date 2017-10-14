package br.uem.commons.stats;

import java.util.ArrayList;
import java.util.List;

public class Statistic {

	private List<StatisticInfo> stats;
	private long currentTimeTransfer = 0L;
	private String currentKey = null;
	private long currentTotalTimeTransfer = 0L;
	private ElapTime elapTimeTotal;
	
	public Statistic() {
		
	}

	public void initialize() {
		stats = new ArrayList<>(32);
		this.currentTimeTransfer = 0L;
		this.currentTotalTimeTransfer = 0L;
		this.currentKey = null;
		this.elapTimeTotal = new ElapTime();
		elapTimeTotal.start();
	}

	public void analyze(ElapTime elapTime, String message) {
		
		if (message.startsWith("TestKey")) {
			if (currentTimeTransfer != 0) {
				finishCurrentTotalTime();
				addCurrentStatsInfo();
			}

			elapTimeTotal.start();
			currentKey = message;
			currentTimeTransfer = 0L;
			currentTotalTimeTransfer = 0L;
		}
		currentTimeTransfer += elapTime.elapTime();

	}

	public List<StatisticInfo> getStats() {
		finishCurrentTotalTime();
		addCurrentStatsInfo();
		return this.stats;
	}

	private void finishCurrentTotalTime() {
		elapTimeTotal.finish();
		currentTotalTimeTransfer = elapTimeTotal.elapTime();
	}

	private void addCurrentStatsInfo() {
		StatisticInfo statisticInfo = new StatisticInfo();
		statisticInfo.setKeyInfo(currentKey);
		statisticInfo.setTimeTransfer(currentTimeTransfer);
		statisticInfo.setTimeTotalTransfer(currentTotalTimeTransfer);
		this.stats.add(statisticInfo);
	}

}
