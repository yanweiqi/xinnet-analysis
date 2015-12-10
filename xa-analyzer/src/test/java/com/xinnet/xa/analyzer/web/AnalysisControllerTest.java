package com.xinnet.xa.analyzer.web;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.xinnet.xa.analyzer.BaseWebTest;

public class AnalysisControllerTest extends BaseWebTest {
	@Autowired
	private AnalyzerController analyzerController;
	
	@Test
	public void analysisByDay() throws Exception{
		Map<String, String> parMap = new HashMap<String, String>();
		parMap.put("day", "2014-09-27");
		request.addParameters(parMap);
		request.setMethod("GET");
		request.setRequestURI("/analysisByDay.do");
		handlerAdapter.handle(request, response, analyzerController);
		Thread.sleep(1000*60);
	}

}
