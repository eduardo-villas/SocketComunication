package br.uem.commons.comunication;

public class Constants {

	public static final String GOODBYE = "GOODBYE";
	public static final String HEY = "HEY\n";
	public static final int HEADER_SIZE = 32;

	public static boolean isGoodbye(String message) {
		return (Constants.GOODBYE+"\n").equals(message);
	}

}
