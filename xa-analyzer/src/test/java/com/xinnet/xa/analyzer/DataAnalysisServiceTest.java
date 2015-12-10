package com.xinnet.xa.analyzer;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.xinnet.xa.analyzer.common.AnalyzerConstant.TimeType;
import com.xinnet.xa.analyzer.service.DataAnalysisService;

public class DataAnalysisServiceTest extends BaseSpringJunitUtil {
	@Autowired
	private DataAnalysisService dataAnalysisService;
	
	@Test
	public void testAnalysis() throws InterruptedException{
		dataAnalysisService.runTimeRange("2014112023", "2014112023", null, TimeType.HOUR);
		Thread.sleep(1000*60*30);
	}

}
