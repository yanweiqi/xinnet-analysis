package com.xinnet.xa.analyzer.service;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.xinnet.xa.analyzer.vo.AnalysisParam;

public interface IAnalysisService {
	@Transactional(propagation=Propagation.REQUIRES_NEW, readOnly=false)
	public void analysis(AnalysisParam analysisParam);

}
