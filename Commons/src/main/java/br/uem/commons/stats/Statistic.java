package br.uem.commons.stats;

import java.util.LinkedHashMap;
import java.util.Map;

public class Statistic {

	
	private Map<String, Long> stats = new LinkedHashMap<>();
	private long currentTimeTransfer = 0L;
	private String currentKey = null;

	public Statistic() {
		
	}
	
	void analyze() {
		
	}
}
