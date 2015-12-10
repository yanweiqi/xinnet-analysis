package com.xinnet.xa.analyzer.service.traffic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xinnet.xa.analyzer.common.AnalysisDataUtil;
import com.xinnet.xa.analyzer.common.SqlByTime;
import com.xinnet.xa.analyzer.dao.AnalyzerDao;
import com.xinnet.xa.analyzer.service.IAnalysisService;
import com.xinnet.xa.analyzer.vo.AnalysisParam;

@Service
public class TrafficByTimeAnalysisService implements IAnalysisService{
	@Autowired
	private AnalysisDataUtil analysisDataUtil;
	@Autowired
	private AnalyzerDao analyzerDao;
	
	public void analysis(AnalysisParam param) {
		analysisDataUtil.deleteTableBytime(SqlByTime.ANA_USER_COOKIES, param.getType(), param.getTime());
		analyzerDao.updateDateSql(SqlByTime.INSERT_UV_CHANNEL_AND_PRODUCT_LINE, param.getType(), param.getTime());
		
		analysisDataUtil.deleteTableBytime(SqlByTime.ANA_PV, param.getType(), param.getTime());
		analyzerDao.updateDateSql(SqlByTime.INSERT_PV_CHANNEL_AND_PRODUCT_LINE, param.getType(), param.getTime());
		
		analysisDataUtil.deleteTableBytime(SqlByTime.ANA_PV_BY_URL, param.getType(), param.getTime());
		analyzerDao.updateDateSql(SqlByTime.INSERT_PV_BY_URL, param.getType(), param.getTime());
		
		analysisDataUtil.deleteTableBytime(SqlByTime.ANA_USER_COOKIES_BY_URL, param.getType(),  param.getTime());
		analyzerDao.updateDateSql(SqlByTime.INSERT_UV_BY_URL, param.getType(), param.getTime());
		analyzerDao.updateDateSql(SqlByTime.UPDATE_UV_BY_URL_PAGE_TYPE,  param.getType(), param.getDay(),param.getTime());
		analyzerDao.updateDateSql(SqlByTime.UPDATE_UV_BY_URL_VISITS_NUMBER, param.getType(), param.getTime(), param.getTime());
		analyzerDao.updateDateSql(SqlByTime.UPDATE_UV_BY_URL_OUT_NUMBER, param.getType(), param.getTime());
		
		
	}
	
	

}
