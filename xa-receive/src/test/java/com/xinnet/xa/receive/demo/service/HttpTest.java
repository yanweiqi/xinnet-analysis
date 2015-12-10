package com.xinnet.xa.receive.demo.service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.xinnet.xa.core.utils.HttpClientUtil;

public class HttpTest {
	 
	
	public static void main(String[] args) throws Exception {
		ExecutorService es = Executors.newFixedThreadPool(100);
		for(int i=0;i<10;i++){
			es.execute(new Runnable() {
				
				@Override
				public void run() {
					Map<String, String> parMap = new HashMap<String, String>();
					parMap.put("domain", "gqq.com");
					parMap.put("prevUrl", "http://www.baidu.com/s?wd=%E5%9F%9F%E5%90%8D%E6%B3%A8%E5%86%8C&rsv_bp=0&tn=baidu&rsv_spt=3&ie=utf-8&rsv_sug3=4&rsv_sug4=182&rsv_sug1=2&oq=yu&rsv_sug2=1&f=3&rsp=0&inputT=2602&rsv_sug=1");
					parMap.put("currentUrl", "http://www.xinnet.com/?utm_source=baidu&utm_medium=cpc&utm_campaign=%E7%AB%9E%E5%93%81%E8%AF%8D-%E5%9F%9F%E5%90%8D&utm_content=%E5%9F%9F%E5%90%8D-godaddy-%E9%87%8D%E7%82%B9&utm_term=godaddy");
					parMap.put("action", "testAction");
					parMap.put("operationType", "T");
					parMap.put("operationData", "hycost,http://ssss.com||hysost,http://scss.com");
					parMap.put("vtime", "111111111");
					parMap.put("ftime", "22222");
					parMap.put("ltime", "22992");
					parMap.put("terminal", "ie789");
					parMap.put("tenantID", "222");
					parMap.put("actionTime", "2014-06-13 11:20:58");
					parMap.put("utm_campaign", "广东-域名注册独立");
					parMap.put("jsSessionId", "sdsfd11");
					try {
						HttpClientUtil.post("http://119.10.114.194:8092/receive/track.do", parMap, "utf-8");
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
			});
		}
		 
	}

}
