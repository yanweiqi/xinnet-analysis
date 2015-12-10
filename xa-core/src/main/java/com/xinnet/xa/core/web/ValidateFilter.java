package com.xinnet.xa.core.web;

import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.xinnet.xa.core.utils.XaWebUtils;

public class ValidateFilter implements Filter {
	
	public static Logger logger = Logger.getLogger(ValidateFilter.class);
	
	@Override
	public void destroy() {
		// TODO Auto-generated method stub
	}
	
	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		String ip = XaWebUtils.getRemortIP(request);
		Map<String,String[]> parameterMap =   request.getParameterMap();
		String uri = request.getRequestURI();
		StringBuilder sb = new StringBuilder();
		sb.append("uri:").append(uri).append(" ip:").append(ip).append(" parammeter:");
		Set<Entry<String,String[]>> entrySet = parameterMap.entrySet();
		for(Entry<String,String[]> entry : entrySet){
			sb.append(entry.getKey()).append("=").append(entry.getValue()[0]).append(",");
		}
		logger.info(sb);
		chain.doFilter(req,res);
	}
	
	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
	}
}
