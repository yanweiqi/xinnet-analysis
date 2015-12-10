package com.xinnet.xa.analyzer.web;

import java.util.HashMap;
import java.util.Map;

import com.xinnet.xa.core.utils.HttpClientUtil;

public class HttpClientTest {
	public static void main(String[] args) throws Exception {
		Map<String,String> params = new HashMap<String,String>();
		params.put("starHour", "2015010423");
		params.put("endHour", "2015010423");
		String url="http://121.14.68.214:8082/analyzer/analysisHourRange.do";
		String result = HttpClientUtil.post(url, params, "utf-8");
		System.out.println(result);
	}

}
