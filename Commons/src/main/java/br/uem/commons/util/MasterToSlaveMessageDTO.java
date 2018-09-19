package br.uem.commons.util;

public class MasterToSlaveMessageDTO {

	private String model;
	private String ip;
	private String option;
	private Long tempoInicio;
	private Integer group;
	private Integer idThread;

	public void setModel(String model) {
		this.model = model;
	}

	public String getModel() {
		return model;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getIp() {
		return ip;
	}

	public void setOption(String option) {
		this.option = option;
	}

	public String getOption() {
		return option;
	}

	public void setTempoInicio(Long tempoInicio) {
		this.tempoInicio = tempoInicio;
	}

	public Long getTempoInicio() {
		return tempoInicio;
	}

	public Integer getGroup() {
		return group;
	}

	public void setGroup(Integer group) {
		this.group = group;
	}

	public Integer getIdThread() {
		return idThread;
	}

	public void setIdThread(Integer idThread) {
		this.idThread = idThread;
	}

}
