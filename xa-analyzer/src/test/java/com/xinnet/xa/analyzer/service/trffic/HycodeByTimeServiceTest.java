package com.xinnet.xa.analyzer.service.trffic;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.xinnet.xa.analyzer.BaseSpringJunitUtil;
import com.xinnet.xa.analyzer.common.AnalyzerConstant.TimeType;
import com.xinnet.xa.analyzer.service.IAnalysisService;
import com.xinnet.xa.analyzer.vo.AnalysisParam;

public class HycodeByTimeServiceTest extends BaseSpringJunitUtil {
	@Autowired
	private IAnalysisService hyCodeByTimeAnalysisService;
	
	@Test
	public void testAnalysis(){
		AnalysisParam ap = new AnalysisParam("2014112001", TimeType.HOUR);
		hyCodeByTimeAnalysisService.analysis(ap);
	}

}
