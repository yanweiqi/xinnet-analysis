package com.xinnet.xa.analyzer.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.xinnet.xa.analyzer.BaseSpringJunitUtil;

public class ReporterSeriveTest extends BaseSpringJunitUtil{
	@Autowired
	ReportResgistService reportResgistService;
	
	@Test
	public void testCsv() throws Exception{
		reportResgistService.sendCsvEmail("2014-10-11");
	}

}
