package com.xinnet.xa.core.utils;

import java.net.URL;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.web.util.WebUtils;

public class XaWebUtils extends WebUtils {
	private static Logger logger = Logger.getLogger(XaWebUtils.class);
	public static String getRemortIP(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
	       if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
	           ip = request.getHeader("Proxy-Client-IP");
	       }
	       if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
	           ip = request.getHeader("WL-Proxy-Client-IP");
	       }
	       if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
	           ip = request.getRemoteAddr();
	       }
	       return ip;
	}

	public static String getCookiesString(HttpServletRequest request) {
		StringBuilder sb = new StringBuilder();
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {

			for (Cookie c : cookies)
				sb.append(c.getName()).append("=").append(c.getValue()).append(";");
		}
		return sb.toString();
	}
	
	public static URL getURL(String source){
		URL url = null;
		if(StringUtils.isNotBlank(source)){
			try {
				url = new URL(source);
				
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
		}
		return url;
	}

}
