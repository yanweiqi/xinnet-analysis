package com.xinnet.xa.analyzer.service.roi;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.xinnet.xa.analyzer.BaseSpringJunitUtil;
import com.xinnet.xa.analyzer.common.AnalyzerConstant.TimeType;
import com.xinnet.xa.analyzer.service.IAnalysisService;
import com.xinnet.xa.analyzer.vo.AnalysisParam;

public class RegisterByTimeAnalysisServiceTest extends BaseSpringJunitUtil {
	@Autowired
	private IAnalysisService registerByTimeAnalysisService;
	
	@Test
	public void testAnalysis(){
		AnalysisParam ap = new AnalysisParam("2014112001", TimeType.HOUR);
		registerByTimeAnalysisService.analysis(ap);
	}

}
