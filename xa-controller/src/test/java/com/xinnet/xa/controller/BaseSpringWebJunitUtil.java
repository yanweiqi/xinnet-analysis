package com.xinnet.xa.controller;

import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.web.servlet.mvc.annotation.AnnotationMethodHandlerAdapter;

public class BaseSpringWebJunitUtil extends BaseSpringJunitUtil{
	
	 @Autowired  
     protected AnnotationMethodHandlerAdapter handlerAdapter;  


	//声明Request与Response模拟对象
	public MockHttpServletRequest request;
	public MockHttpServletResponse response;
	public MockHttpSession session;


	//执行测试前先初始模拟对象
	@Before
	public void before() {
		request = new MockHttpServletRequest();
		response = new MockHttpServletResponse();
		session = new MockHttpSession();
		request.setCharacterEncoding("UTF-8");
	}


}
