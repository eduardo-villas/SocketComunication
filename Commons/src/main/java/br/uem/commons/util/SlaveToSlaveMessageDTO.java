package br.uem.commons.util;

import java.util.Map;

public class SlaveToSlaveMessageDTO {

	private Integer size;
	private Integer idThread;
	private String ip;
	private Long id;
	private String option;
	private String root;
	private Integer group;
	private Map<String, String> subject;
	private Map<String, String> object;
	private Map<String, String> result;
	private Map<String, String> border;
	
	public Integer getSize() {
		return size;
	}

	public void setSize(Integer size) {
		this.size = size;
	}

	public Integer getIdThread() {
		return idThread;
	}

	public void setIdThread(Integer idThread) {
		this.idThread = idThread;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getOption() {
		return option;
	}

	public void setOption(String option) {
		this.option = option;
	}

	public String getRoot() {
		return root;
	}

	public void setRoot(String root) {
		this.root = root;
	}

	public Integer getGroup() {
		return group;
	}

	public void setGroup(Integer group) {
		this.group = group;
	}

	public Map<String, String> getSubject() {
		return subject;
	}

	public void setSubject(Map<String, String> subject) {
		this.subject = subject;
	}

	public Map<String, String> getObject() {
		return object;
	}

	public void setObject(Map<String, String> object) {
		this.object = object;
	}

	public Map<String, String> getResult() {
		return result;
	}

	public void setResult(Map<String, String> result) {
		this.result = result;
	}

	public Map<String, String> getBorder() {
		return border;
	}

	public void setBorder(Map<String, String> border) {
		this.border = border;
	}
	
}
