package com.xinnet.xa.analyzer.service.traffic;

import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xinnet.xa.analyzer.common.AnalysisDataUtil;
import com.xinnet.xa.analyzer.common.SqlByTime;
import com.xinnet.xa.analyzer.dao.AnalyzerDao;
import com.xinnet.xa.analyzer.service.IAnalysisService;
import com.xinnet.xa.analyzer.vo.AnalysisParam;
import com.xinnet.xa.core.utils.DateUtil;

@Service
public class HyCodeByTimeAnalysisService implements IAnalysisService {
	private Logger logger = Logger.getLogger(getClass());
	@Autowired
	private AnalyzerDao analyzerDao;
	@Autowired
	private AnalysisDataUtil analysisDataUtil;

	@Override
	public void analysis(AnalysisParam analysisParam) {
        initTempCookies(analysisParam);
        analysisHyData(analysisParam);
	}

	private void initTempCookies(AnalysisParam analysisParam) {
		analyzerDao.truncateTable(SqlByTime.TEMP_USER_COOKEID);
		Date date = new Date();
		String day = analysisParam.getDay();
		try {
			 
			date = DateUtil.parseDate(day);
		} catch (Exception e) {
           logger.error(e.getMessage(), e);
		}
		String least30Days =DateUtil.addDay(date, -30);
		analyzerDao.update(SqlByTime.INSERT_TEMP_USER_COOKIES_FROM_UV,analysisParam.getStartTime(),analysisParam.getEndTime(), least30Days,day);
	}
	
	private void analysisHyData(AnalysisParam analysisParam){
		analysisDataUtil.deleteTableBytime(SqlByTime.ANA_HY_SOURCE, analysisParam.getType(), analysisParam.getTime());
		analyzerDao.update(SqlByTime.INSET_HY_SOURCE, analysisParam.getStartTime(),analysisParam.getEndTime());
		analyzerDao.update(SqlByTime.UPDATE_HY_DATA_PRODUCT_LINE_BY_ORDER);
		analyzerDao.update(SqlByTime.UPDATE_HY_DATA_PRODUCT_LINE_OTHER);
		analysisDataUtil.analysisAreaId(SqlByTime.ANA_HY_SOURCE,analysisParam.getType(),analysisParam.getTime());
	}

}
