package com.xinnet.xa.controller.web;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.xinnet.xa.controller.BaseSpringWebJunitUtil;

public class RegisterControolerTest extends BaseSpringWebJunitUtil {
	@Autowired
	private RegisterController registerController;
	
	@Test
	public void testSendError() throws Exception{
		Map<String, String> parMap = new HashMap<String, String>();
		parMap.put("ip", "127.0.0.1");
		parMap.put("port", "8077");
		parMap.put("compType", "analyst");
		parMap.put("message", "test");
		parMap.put("stacks", "sstest");
		request.addParameters(parMap);
		request.setMethod("POST");
		request.setRequestURI("/sendError.do");
		handlerAdapter.handle(request, response, registerController);
	}

}
