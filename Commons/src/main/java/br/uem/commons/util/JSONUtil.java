package br.uem.commons.util;

import java.lang.reflect.Type;

import com.google.gson.Gson;

public class JSONUtil {

	private static final Gson gson;
	
	static {
		gson = new Gson(); 
	} 
	
	private JSONUtil() {
		
	}
	
	public static String stringFromOject(Object object) {
		return gson.toJson(object);
	}

	public static <T> T objectFromString(String jsonString, Class<T> objectClass) {
		return gson.fromJson(jsonString, objectClass);
	}

	public static <T> T objectFromString(String jsonString, Type typeObject) {
		return gson.fromJson(jsonString, typeObject);
	}
	
}
