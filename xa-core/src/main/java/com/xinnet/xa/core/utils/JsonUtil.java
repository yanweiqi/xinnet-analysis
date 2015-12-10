package com.xinnet.xa.core.utils;

import java.io.IOException;
import java.io.StringWriter;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

public class JsonUtil {
	public static String buildObjectToJson(Object object) throws JsonGenerationException, JsonMappingException, IOException{
		ObjectMapper objectMapper = new ObjectMapper();
		StringWriter sw = new StringWriter();
		objectMapper.writeValue(sw, object);
		return sw.toString();
	}

}
