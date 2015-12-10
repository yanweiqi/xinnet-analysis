package com.xinnet.xa.analyzer.service.traffic;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xinnet.xa.analyzer.common.AnalysisDataUtil;
import com.xinnet.xa.analyzer.common.SqlByTime;
import com.xinnet.xa.analyzer.dao.ConfigDao;
import com.xinnet.xa.analyzer.dao.TempDataDao;
import com.xinnet.xa.analyzer.service.IAnalysisService;
import com.xinnet.xa.analyzer.vo.AnalysisParam;
import com.xinnet.xa.analyzer.vo.SEOKeyword;
import com.xinnet.xa.analyzer.vo.SEOSourceType;
import com.xinnet.xa.analyzer.vo.SourceKeyword;

@Service
public class TempDataByTimeAnalysisService implements IAnalysisService{
	private Logger logger = Logger.getLogger(getClass());
	@Autowired
	private TempDataDao tempDataDao;
	@Autowired
	private ConfigDao configDao;
	@Autowired
	private AnalysisDataUtil analysisDataUtil;

	 
	public void analysis(AnalysisParam param) {
		insertTempPageView(param);
		
		inserTempUserCookies(param);
		tempDataDao.update(SqlByTime.UPDATE_TEMP_DAY_CHANNEL);
		tempDataDao.update(SqlByTime.UPDATE_TEMP_DAY_ABNORMAL_DATA);
		
		analysisDataUtil.analysisUrlDetailId(SqlByTime.TEMP_PAGE_VIEW, "current_page", param.getTime(),param.getType());
		
		tempDataDao.update(analysisDataUtil.replaceMonthSql(SqlByTime.CREATE_ANA_PAGE_VIEW, param.getMonth()));
		analysisDataUtil.deleteTableBytime(analysisDataUtil.replaceMonthSql(SqlByTime.ANA_PAGE_VIEW, param.getMonth()) , param.getType(), param.getTime());
		tempDataDao.updateDateSql(analysisDataUtil.replaceMonthSql(SqlByTime.INSERT_ANALYTICS_PAGE_VIEW,param.getMonth()),param.getType(),param.getTime());
		logger.info("analysisData complete");
	}
	
	private void insertTempPageView(AnalysisParam param){
		tempDataDao.truncateTable(SqlByTime.TEMP_PAGE_VIEW);
		tempDataDao.update(SqlByTime.INSERT_TEMP_PAGE_VIEW__DAY_DATA_BY_PAGETYPE,param.getStartTime(),param.getAfterHourEndTime());
		tempDataDao.update(SqlByTime.INSERT_TEMP_PAGE_VIEW__DAY_DATA_BY_VTIME,param.getStartTime(),param.getAfterHourEndTime());
		tempDataDao.update(SqlByTime.UPDATE_TEMP_PAGE_VIEW_DAY_VTIME);
		tempDataDao.update(SqlByTime.DEL_TEMP_PAGE_VIEW_DAY_VTIME_PAGE);
		tempDataDao.update(SqlByTime.FORMAT_TEMP_PAGE_VIEW__DAY);
		analysisDataUtil.analysisDate(SqlByTime.TEMP_PAGE_VIEW, "action_time");
		logger.info("insertTempPageView complete");
	}
	
	
	
	private void inserTempUserCookies(AnalysisParam param){
		tempDataDao.truncateTable(SqlByTime.TEMP_USER_COOKEID);
		
		tempDataDao.update(SqlByTime.INSERT_TEMP_USER_COOKIES_SEM);
		this.insertTempUserCookiesSeoChannel();
		tempDataDao.update(SqlByTime.INSERT_TEMP_USER_COOKIES_EDM);
		tempDataDao.update(SqlByTime.INSERT_TEMP_USER_COOKIES_CPS);
		tempDataDao.update(SqlByTime.INSERT_TEMP_USER_COOKIES_REFERRAL);
		tempDataDao.update(SqlByTime.INSERT_TEMP_USER_COOKIES_DIRECT);
		tempDataDao.update(SqlByTime.INSERT_TEMP_USER_COOKIES_NA);
		
		analysisDataUtil.analysisAreaId(SqlByTime.TEMP_USER_COOKEID,param.getType(),param.getTime());
		
		tempDataDao.update(SqlByTime.UPDATE_TEMP_USER_COOKIES_ACCESS_TYPE_BY_TEMP_PAGE_VIEW);
		tempDataDao.update(SqlByTime.UPDATE_TEMP_USER_COOKIES_ACCESS_TYPE_BY_ANALYTIS_USER_COOKIES,param.getDay());
		tempDataDao.update(SqlByTime.UPDATE_TEMP_USER_COOKIES_ACCESS_TYPE);
		tempDataDao.update(SqlByTime.UPDATE_TEMP_USER_COOKIES_ACCESS_TYPE_BY_TERMINAL);
		updateTempUserCookiesChannelAndProductLine();
		tempDataDao.update(SqlByTime.UPDATE_TEMP_SOURCE_SEO_KEYWORD);
		
		tempDataDao.update(SqlByTime.UPDATE_TEMP_USER_COOKIES_BY_BEFOR_HOUR,param.getBeforHour());
		logger.info("inserTempUserCookies complete");
	}
	
	 

	public void insertTempUserCookiesSeoChannel(){
		List<SEOSourceType> list = configDao.getSEOSourceType();
		for(SEOSourceType sst : list){
			tempDataDao.update(SqlByTime.INSERT_TEMP_USER_COOKIES_SEO,sst.getName(),sst.getMedium(),sst.getChannelId(),sst.getSoruceTypeId(),"%"+sst.getSource()+"%");
		}
		
	}
	
	public void updateTempUserCookiesChannelAndProductLine(){
		tempDataDao.update(SqlByTime.UPDATE_TEMP_USER_COOKIES_SEM_EDM_PRODUCT_LINE);
		this.updateSEOProuctListApproximateMatching();
        tempDataDao.update(SqlByTime.UPDATE_TEMP_USER_COOKIES_PRODUCT_LINE_OTHER);
        
	}
	
	public void updateSEOProuctListApproximateMatching() {
		List<SEOKeyword> seoKeywordList = configDao.getSEOKeywords();
		List<SourceKeyword> sourceKeywordList = tempDataDao.getTempSEOSourceKeywords();
		if (CollectionUtils.isNotEmpty(sourceKeywordList)) {
			List<SourceKeyword> updateList = this.analystKeyword(
					seoKeywordList, sourceKeywordList);
			tempDataDao.batchUpdateTempSEOProductLine(updateList);
		}
	}

	public List<SourceKeyword> analystKeyword(List<SEOKeyword> seoKeywordList,
			List<SourceKeyword> sourceKeywordList) {
		List<SourceKeyword> updateList = new ArrayList<SourceKeyword>();
		for (SourceKeyword sourceKeyword : sourceKeywordList) {
			int index = Integer.MAX_VALUE;
			String searchKeyword = sourceKeyword.getKeyword();
			if (StringUtils.isNotBlank(searchKeyword)) {
				for (SEOKeyword seoKeyword : seoKeywordList) {
					String keyword = seoKeyword.getValue();
					if (StringUtils.isNotBlank(keyword)) {
						int strIndex = searchKeyword.indexOf(keyword);
						if (strIndex >= 0 && index > strIndex) {
							index = strIndex;
							sourceKeyword.setProductLineId(seoKeyword
									.getProductLineId());
							if (index == 0) {
								break;
							}
						}
					}
				}
			}
			if (index < Integer.MAX_VALUE) {
				updateList.add(sourceKeyword);
			}
		}
		return updateList;
	}
	

	 

}
