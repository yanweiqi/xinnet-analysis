package com.xinnet.xa.analyzer.service.roi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xinnet.xa.analyzer.common.AnalysisDataUtil;
import com.xinnet.xa.analyzer.common.SqlByTime;
import com.xinnet.xa.analyzer.dao.AnalyzerDao;
import com.xinnet.xa.analyzer.service.IAnalysisService;
import com.xinnet.xa.analyzer.vo.AnalysisParam;

@Service
public class RegisterByTimeAnalysisService implements IAnalysisService {
	//private Logger logger = Logger.getLogger(getClass());
	
	@Autowired
	private AnalyzerDao analyzerDao;
	@Autowired
	private AnalysisDataUtil analysisDataUtil;

	@Override
	public void analysis(AnalysisParam analysisParam) {
		insertRegisterDetail(analysisParam);
		inserRegisterFailedDetail(analysisParam);
		analysisRegisterCount(analysisParam);
		analysisRegisterCountByUrl(analysisParam);
	}
	
	private void insertRegisterDetail(AnalysisParam analysisParam){
		analysisDataUtil.deleteTableBytime(SqlByTime.ANA_REGISTER_DETAIL, analysisParam.getType(), analysisParam.getTime());
		analyzerDao.updateDateSql(SqlByTime.INSERT_REGISTER_DETAIL, analysisParam.getType(), analysisParam.getTime());
		analyzerDao.update(SqlByTime.UPDATE_REGISTER_DETAIL_COOKIES_ID, analysisParam.getStartTime(),analysisParam.getEndTime());
		analyzerDao.updateDateSql(SqlByTime.UPDATE_REGISTER_DETAIL_URIL_INFO_ID,analysisParam.getType(), analysisParam.getTime());
		analyzerDao.updateDateSql(SqlByTime.UPDATE_REGISTER_DEATIL_OTHER_URL_INFO_ID,analysisParam.getType(), analysisParam.getTime());
		analyzerDao.updateDateSql(SqlByTime.UPDATE_REGISTER_DETAIL_ACCESS_TYPE,analysisParam.getType(), analysisParam.getTime());
		analyzerDao.truncateTable(SqlByTime.TEMP_PAGE_VIEW);
		analyzerDao.update(analysisDataUtil.replaceMonthSql(SqlByTime.INSERT_INTO_TEMP_MIN_URL_DELTAIL_ID,analysisParam.getMonth()),analysisParam.getDay());
		analyzerDao.updateDateSql(SqlByTime.UPDATE_REGISTR_DETAIL_LANDING_PAGE_ID, analysisParam.getType(), analysisParam.getTime());
	}
	
	private void inserRegisterFailedDetail(AnalysisParam analysisParam){
		 analysisDataUtil.deleteTableBytime(SqlByTime.ANA_USER_REGISTER_FAILED, analysisParam.getType(), analysisParam.getTime());
		 analyzerDao.updateDateSql(SqlByTime.INSERT_REGISTER_FAIL,analysisParam.getType(), analysisParam.getTime());
		 analyzerDao.updateDateSql(SqlByTime.UPDATE_REGISTER_FAILE_URL_INFO_ID, analysisParam.getType(), analysisParam.getTime());
		 
	}
	
	private void analysisRegisterCountByUrl(AnalysisParam analysisParam){
		 analysisDataUtil.deleteTableBytime(SqlByTime.ANA_USER_REGISTER_BY_URL, analysisParam.getType(), analysisParam.getTime());
		 analyzerDao.updateDateSql(SqlByTime.INSERT_REGISTER_COUNT_BY_URL_SUCCESS,analysisParam.getType(), analysisParam.getTime());
		 analyzerDao.updateDateSql(SqlByTime.INSERT_REGISTER_COUNT_BY_URL_FAILED,analysisParam.getType(), analysisParam.getTime());
		 analyzerDao.updateDateSql(SqlByTime.INSERT_REGISTER_COUNT_BY_URL_LANDING_PAGE, analysisParam.getType(), analysisParam.getTime());
	}
	
	private void analysisRegisterCount(AnalysisParam analysisParam){
		 analysisDataUtil.deleteTableBytime(SqlByTime.ANA_REGISTER_COUNT, analysisParam.getType(), analysisParam.getTime());
		 analyzerDao.updateDateSql(SqlByTime.INSERT_REGISTER_COUNT, analysisParam.getType(), analysisParam.getTime());
	}

}
