package br.uem.commons.util;

import static org.junit.Assert.assertEquals;

import java.lang.reflect.Type;
import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.Test;

import com.google.gson.reflect.TypeToken;

public class JSONUtilTest {

	
	@Test
	public void testStringFromOject() throws Exception {
		Map<String, String> map = new LinkedHashMap<>();
		
		map.put("key1", "value1");
		map.put("key2", "value2");
		map.put("key3", "value3");
		
		String stringMapActual = JSONUtil.stringFromOject(map);
		String stringMapExpected = "{\"key1\":\"value1\",\"key2\":\"value2\",\"key3\":\"value3\"}";
		
		assertEquals(stringMapExpected, stringMapActual);
	}

	@Test
	public void testObjectFromString() throws Exception {
		Map<String, String> mapExpected = new LinkedHashMap<>();
		
		mapExpected.put("key1", "value1");
		mapExpected.put("key2", "value2");
		mapExpected.put("key3", "value3");

		LinkedHashMap<String, String> mapActual = JSONUtil.objectFromString("{\"key1\":\"value1\",\"key2\":\"value2\",\"key3\":\"value3\"}", LinkedHashMap.class); 
		
		assertEquals(mapExpected, mapActual);

		
		Type typeObjec = new TypeToken<LinkedHashMap<String, String>>(){}.getType();
		mapActual = JSONUtil.objectFromString("{\"key1\":\"value1\",\"key2\":\"value2\",\"key3\":\"value3\"}", typeObjec);
		
		assertEquals(mapExpected, mapActual);
	}

}
