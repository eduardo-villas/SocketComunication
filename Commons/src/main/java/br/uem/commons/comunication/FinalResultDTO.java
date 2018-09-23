package br.uem.commons.comunication;

import java.util.Hashtable;

public class FinalResultDTO {

	private String ipServerRF;
	private String consultaRF;
	private long idRF;
	private int resultSize;
	private Hashtable<String, String> resultFinalRF;

	public String getIpServerRF() {
		return ipServerRF;
	}

	public void setIpServerRF(String ipServerRF) {
		this.ipServerRF = ipServerRF;
	}

	public String getConsultaRF() {
		return consultaRF;
	}

	public void setConsultaRF(String consultaRF) {
		this.consultaRF = consultaRF;
	}

	public long getIdRF() {
		return idRF;
	}

	public void setIdRF(long idRF) {
		this.idRF = idRF;
	}

	public int getResultSize() {
		return resultSize;
	}
	
	public void setResultSize(int resultSize) {
		this.resultSize = resultSize;
	}

	public Hashtable<String, String> getResultFinalRF() {
		return resultFinalRF;
	}

	public void setResultFinalRF(Hashtable<String, String> resultFinalRF) {
		this.resultFinalRF = resultFinalRF;
	}

}
