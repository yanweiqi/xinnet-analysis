package com.xinnet.xa.analyzer.service.roi;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.xinnet.xa.analyzer.BaseSpringJunitUtil;
import com.xinnet.xa.analyzer.common.AnalyzerConstant.TimeType;
import com.xinnet.xa.analyzer.service.IAnalysisService;
import com.xinnet.xa.analyzer.vo.AnalysisParam;

public class OrderByTimeAnalysisServiceTest extends BaseSpringJunitUtil {
	@Autowired
	private IAnalysisService orderByTimeAnalysisService;
	
	@Test
	public void testAnalysis(){
		AnalysisParam ap = new AnalysisParam("2014112001", TimeType.HOUR);
		orderByTimeAnalysisService.analysis(ap);
	}

}
