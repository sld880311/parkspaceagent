package com.parkspace.agent.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @Title: JsonUtils.java
 * @Package com.parkspace.util
 * <p>Description:</p>
 * @author sunld
 * @version V1.0.0 
 * <p>CreateDate:2017年10月12日 下午4:24:25</p>
*/

public class JsonUtils {
	/**
	 * @Title: str2Object
	 * <p>Description:</p>
	 * @param     json 需要转换的内容
	 * @param     classType 目的对象类型
	 * @return Object    返回类型
	 * @throws
	 * <p>CreateDate:2017年10月12日 下午4:24:58</p>
	 */
	public static <T> T str2Object(String json,Class<T> classType) {
		ObjectMapper mapper = new ObjectMapper(); 
		try {
			return mapper.readValue(json, classType);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} 
	}
	
	public static String object2String(Object json) {
		String requestJson = "";
		ObjectMapper mapper = new ObjectMapper(); 
		try {
			requestJson = mapper.writeValueAsString(json);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		} 
		return requestJson;
	}
}
