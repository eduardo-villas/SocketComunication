package br.uem.commons.comunication;

import java.util.Collections;

public class PacketCalculation {

	private String keyMessageWithout;
	private String keyMessageWith;
	private int MB_TRANSFER;
	private int TOTAL_MESSAGES;
	private String TAG;
	private int SIZE_PACKAGE;
	private String message;
	private int SIZE_MESSAGE;

	public PacketCalculation(final int SIZE_MESSAGE, final int multiplyPacket, final int transferInMB) {
		// (1638400 * 32) / (1024*1024) para mensagens de 32 bytes 50mb
		// final int TOTAL_MESSAGES = 1638400;
		this.SIZE_MESSAGE = SIZE_MESSAGE;
		message = String.join("", Collections.nCopies(SIZE_MESSAGE / 8, "8bytesMS"));
		SIZE_PACKAGE = 1024 * multiplyPacket;
		TAG = "packet" + SIZE_PACKAGE;
		TOTAL_MESSAGES = (transferInMB * (1024 * 1024)) / SIZE_MESSAGE;
		MB_TRANSFER = (TOTAL_MESSAGES * SIZE_MESSAGE) / (1024 * 1024);// QUANTIDADE
																		// EM MB
																		// para
																		// mensagens
																		// de 32
																		// bytes
																		// 50mb
		keyMessageWithout = String.format("TestKey:type=%s: sizemessage=%d: packet=%d", "withoutpacket", SIZE_MESSAGE,
				SIZE_PACKAGE);
		keyMessageWith = String.format("TestKey:type=%s: sizemessage=%d: packet=%d", "withpacket", SIZE_MESSAGE,
				SIZE_PACKAGE);
	}

	public String getKeyMessageWithout() {
		return keyMessageWithout;
	}

	public String getKeyMessageWith() {
		return keyMessageWith;
	}

	public final int getMB_TRANSFER() {
		return MB_TRANSFER;
	}

	public final int getTOTAL_MESSAGES() {
		return TOTAL_MESSAGES;
	}

	public final String getTAG() {
		return TAG;
	}

	public final int getSIZE_PACKAGE() {
		return SIZE_PACKAGE;
	}

	public final String getMessage() {
		return message;
	}

	public final int getSIZE_MESSAGE() {
		return SIZE_MESSAGE;
	}

}
