package com.xinnet.xa.core.utils;

import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;

import com.xinnet.xa.core.vo.TrackData;

public class Utils {

	private static Logger logger = Logger.getLogger(Utils.class);

	public static void sendJson(HttpServletResponse response, Object object) {

		response.setContentType("text/html");
		PrintWriter out = null;
		try {
			String json =JsonUtil.buildObjectToJson(object);
			out = response.getWriter();
            out.println(json.toString());
			out.flush();
		} catch (Exception e) {
			logger.error("send json error:" + e.getMessage(), e);
		} finally {
			if (out != null) {
				out.close();
			}
		}
	}
	
	public static String getUUID(){
		return UUID.randomUUID().toString();
	}
	
	public static Map<String,String> getUtm(String url){
		
		Map<String,String> utmMap = new HashMap<String, String>();
		int begin = url.indexOf("?");
		url = url.substring(begin+1);
		String[] utms = url.split("&");
		for(String utm: utms){
			if(utm.startsWith("utm_")){
				String[] utmArray = utm.split("=");
				utmMap.put(utmArray[0], utmArray[1]);
			}
		}
		return utmMap;
	}
	
	public static String getUrlDecoder(String url){
		String decoderUrl ="";
		try {
			decoderUrl =URLDecoder.decode(url, "utf-8") ;
		} catch (UnsupportedEncodingException e) {
			 
			try {
				decoderUrl= URLDecoder.decode(url.replaceAll("%", "%25"), "utf-8");
			} catch (UnsupportedEncodingException e1) {
				logger.error(e1.getMessage(), e1);
				decoderUrl=url;
			}
		}
		return decoderUrl;
	}
	
	public static String getExceptionStacks(Exception e){
		StackTraceElement[] stacks = e.getStackTrace();
		StringBuilder sb = new StringBuilder();
		for(StackTraceElement stack: stacks){
			sb.append("<br>").append(stack.toString());
		}
		return sb.toString();
	}
	
	public static void main(String[] args) throws Exception {
		String s ="http://www.xinnet.com/?utm_source=baidu&utm_medium=cpc&utm_campaign=%E5%B9%BF%E4%B8%9C-%E5%9F%9F%E5%90%8D%E6%B3%A8%E5%86%8C%E7%8B%AC%E7%AB%8B&utm_content=%E5%9F%9F%E5%90%8D%E6%B3%A8%E5%86%8C&utm_term=%E5%9F%9F%E5%90%8D%E6%B3%A8%E5%86%8C";
		s=URLDecoder.decode(s, "utf-8");
		Map<String,String> utmMap = getUtm(s);
		TrackData data = new TrackData();
		data.setPrevUrl(s);
		BeanUtils.populate(data, utmMap);
		System.out.println(data.getUtm_content());
	}

}
