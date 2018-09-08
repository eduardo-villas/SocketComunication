package br.uem.commons.util;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

public class MapParser {

	public static Map<String, String> parseMessageParams(String message) {
		String parameters[] = message.split(";");
		Map<String, String> parsedParams = new HashMap<>();
		for (String param : parameters) {
			String KeyValue[] = param.split("=");
			parsedParams.put(KeyValue[0], KeyValue[1].replaceAll("\n", ""));//TODO: Exception ao realizar o parse dessa linha.
		}
		return parsedParams;
	}

	static public String toString(Map<String, String> map) {
		return map.toString().replaceAll("\\{", "").replaceAll("\\}", "");
	}

	static public String toString(Hashtable<String, String> map) {
		return map.toString().replaceAll("\\{", "").replaceAll("\\}", "");
	}

}
