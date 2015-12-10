package com.xinnet.xa.analyzer.service.traffic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xinnet.xa.analyzer.common.AnalysisDataUtil;
import com.xinnet.xa.analyzer.common.SqlByTime;
import com.xinnet.xa.analyzer.dao.AnalyzerDao;
import com.xinnet.xa.analyzer.service.IAnalysisService;
import com.xinnet.xa.analyzer.vo.AnalysisParam;

@Service
public class TrafficQualityAnalysisService implements IAnalysisService{
	@Autowired
	private AnalyzerDao analyzerDao;
	@Autowired
	private AnalysisDataUtil analysisDataUtil;
	
	public void analysis(AnalysisParam param) {
		analyzerDao.truncateTable(SqlByTime.TEMP_PAGE_VIEW);
		analyzerDao.update(analysisDataUtil.replaceMonthSql(SqlByTime.INSERT_TEMP_BY_ANALYSIS_PAGE_VIEW, param.getMonth()),param.getDay());
		analysisDataUtil.deleteTableBytime(SqlByTime.ANA_USER_COOKIES_STATE, param.getType(), param.getTime());
		analyzerDao.update(SqlByTime.INSERT_COOKIES_STATE_VTIME_AND_PAGE_NUM);
		analyzerDao.update(SqlByTime.UPDATE_COOKIES_STATE_ACCESS_COUNT,param.getTime());
		
	}
}
