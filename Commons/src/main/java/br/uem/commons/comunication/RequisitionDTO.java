package br.uem.commons.comunication;

public class RequisitionDTO {

	private String model;
	private String requisitionOrigin;
	private long time;
	private String option;
	private Integer group;
	private String idThread;

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getRequisitionOrigin() {
		return requisitionOrigin;
	}

	public void setRequisitionOrigin(String requisitionOrigin) {
		this.requisitionOrigin = requisitionOrigin;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

	public String getOption() {
		return option;
	}

	public void setOption(String option) {
		this.option = option;
	}

	public Integer getGroup() {
		return group;
	}

	public void setGroup(Integer group) {
		this.group = group;
	}

	public String getIdThread() {
		return idThread;
	}

	public void setIdThread(String idThread) {
		this.idThread = idThread;
	}

}
