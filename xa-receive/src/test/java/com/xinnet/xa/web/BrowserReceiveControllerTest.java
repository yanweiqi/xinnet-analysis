package com.xinnet.xa.web;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.xinnet.xa.BaseWebTest;
import com.xinnet.xa.receive.web.BrowserReceiveController;

public class BrowserReceiveControllerTest extends BaseWebTest {
	@Autowired
	private BrowserReceiveController browserReceiveController;

	@Test
	public void track() throws Exception {
		request.addHeader("X-Real-IP", "121.33.22.11");
		Cookie[] cookies = new Cookie[1];
		cookies[0] = new Cookie("cuId", "111");
		request.setCookies(cookies);
		Map<String, String> parMap = new HashMap<String, String>();
		parMap.put("domain", "gqq.com");
		parMap.put("prevUrl", "http://www.baidu.com/s?wd=%E5%9F%9F%E5%90%8D%E6%B3%A8%E5%86%8C&rsv_bp=0&tn=baidu&rsv_spt=3&ie=utf-8&rsv_sug3=4&rsv_sug4=182&rsv_sug1=2&oq=yu&rsv_sug2=1&f=3&rsp=0&inputT=2602&rsv_sug=1");
		parMap.put("currentUrl", "http://www.xinnet.com/?utm_source=baidu&utm_medium=cpc&utm_campaign=%E7%AB%9E%E5%93%81%E8%AF%8D-%E5%9F%9F%E5%90%8D&utm_content=%E5%9F%9F%E5%90%8D-godaddy-%E9%87%8D%E7%82%B9&utm_term=godaddy");
		parMap.put("action", "testAction");
		parMap.put("operationType", "L");
		parMap.put("operationData", "hycost,http://ssss.com||hysost,http://scss.com");
		parMap.put("vtime", "111111111");
		parMap.put("ftime", "22222");
		parMap.put("ltime", "22992");
		parMap.put("terminal", "ie789");
		parMap.put("tenantID", "222");
		parMap.put("actionTime", "2014-10-13 11:20:58");
		parMap.put("utm_campaign", "广东-域名注册独立");
		parMap.put("jsSessionId", "sdsfd11");
		request.addParameters(parMap);
		request.setMethod("GET");
		request.setRequestURI("/track.do");
		for (int i = 0; i < 20; i++)
			handlerAdapter.handle(request, response, browserReceiveController);
	    Thread.sleep(1000*60*20);
	}

}
