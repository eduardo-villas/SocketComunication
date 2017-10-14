package br.uem.commons.stats;

public class StatisticInfo {

	private String keyInfo;
	private Long timeTransfer;
	private Long timeTotalTransfer;
	
	public String getKeyInfo() {
		return keyInfo;
	}
	public void setKeyInfo(String keyInfo) {
		this.keyInfo = keyInfo;
	}
	public Long getTimeTransfer() {
		return timeTransfer;
	}
	public void setTimeTransfer(Long timeTransfer) {
		this.timeTransfer = timeTransfer;
	}
	public Long getTimeTotalTransfer() {
		return timeTotalTransfer;
	}
	public void setTimeTotalTransfer(Long timeTotalTransfer) {
		this.timeTotalTransfer = timeTotalTransfer;
	}
	
}
