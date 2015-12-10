package com.xinnet.xa.analyzer.common;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.taobao.ad.easyschedule.dataobject.JobData;
import com.taobao.ad.easyschedule.dataobject.JobResult;
import com.xinnet.xa.analyzer.common.AnalyzerConstant.TimeType;
import com.xinnet.xa.analyzer.dao.TempDataDao;
import com.xinnet.xa.analyzer.vo.UrlInfo;
import com.xinnet.xa.core.utils.DateUtil;
import com.xinnet.xa.core.utils.HttpClientUtil;

@Component
public class AnalysisDataUtil {
	private Logger logger = Logger.getLogger(getClass());
	@Autowired
	private TempDataDao tempDataDao;

	public void analysisUrlDetailId(String tableName, String urlColumnName,
			String date,TimeType type) {
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("{table_name}", tableName);
		paramMap.put("{url_column_name}", urlColumnName);
		executeTimeSql(SqlByTime.UPDATE_URL_TYPE, paramMap,type,date);

		List<UrlInfo> urlInfos = tempDataDao.getInfoFuzzySearchData();
		Collections.sort(urlInfos);
		//String sql = this.replaceSql(SqlByTime.UPDATE_URL_DATA, paramMap);
		for (UrlInfo urlInfo : urlInfos) {
			executeTimeSql(SqlByTime.UPDATE_URL_DATA, paramMap,type,urlInfo.getDetailId(),
					getUrlFuzzySearchWhere(urlInfo.getUrl()),date);
			 
		}
		executeTimeSql(SqlByTime.UPDATE_OTHER_URL_TYPE, paramMap,type,date);
	}

	public void analysisAreaId(String tableName,TimeType timeType,
			String time) {
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("{table_name}", tableName);
		executeTimeSql(SqlByTime.UPDATE_AREA_ID, paramMap,timeType,time);
		executeTimeSql(SqlByTime.UPDATE_AREA_ID_OTHER, paramMap,timeType,time);
		executeTimeSql(SqlByTime.UPDATE_AREA_ID_UNKNOW, paramMap,timeType,time);

	}
	
	public String getMaxTime(TimeType type) {
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("{column_name}", type.getColumnName());
		String sql = this.replaceSql(SqlByTime.SELECT_MAX_TIME, paramMap);
		return tempDataDao.getJdbcTemplate().queryForObject(sql, String.class);
		 
	}

	public void sendJob(boolean success, JobData jobData) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("success", success ? String.valueOf(JobResult.SUCCESS_TRUE)
				: String.valueOf(JobResult.SUCCESS_FALSE));
		params.put(
				"resultCode",
				success ? String.valueOf(JobResult.RESULTCODE_NORMAL) : String
						.valueOf(JobResult.RESULTCODE_JOB_REQUEST_FAILURE));
		params.put("jobGroup", jobData.getJobGroup());
		params.put("jobName", jobData.getJobName());
		params.put("jobId", jobData.getJobId());
		String callUrl = jobData.getCallBackUrl();
		String[] urls = callUrl.split(":");
		StringBuilder sb = new StringBuilder(
				AnalyzerConstant.SCHEDULING_PLATFROM_URL);
		callUrl = sb.append(":").append(urls[urls.length - 1]).toString();
		logger.info("callUrl:" + callUrl);
		try {
			HttpClientUtil.post(callUrl, params, "utf-8");
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	private String getUrlFuzzySearchWhere(String url) {
		return url.startsWith("http://") ? url + "%" : "%" + url + "%";
	}

	public void deleteTableBytime(String tableName, TimeType timeType,
			String time) {
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("{table_name}", tableName);
		executeTimeSql(SqlByTime.DELETE_TABLE_BY_TIME, paramMap,timeType,time);

	}

	public void analysisDate(String tableName, String urlColumnName) {
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("{table_name}", tableName);
		paramMap.put("{url_column_name}", urlColumnName);
		executeSql(SqlByTime.UPDATE_DAY_DATE, paramMap);

	}

	public String replaceSql(String primitiveSql, Map<String, String> paramMap) {
		String sql = primitiveSql;
		Set<Entry<String, String>> entrySet = paramMap.entrySet();
		for (Entry<String, String> entry : entrySet) {
			sql = sql.replace(entry.getKey(), entry.getValue());
		}
		return sql;
	}
	
	public String replaceMonthSql(String primitiveSql,String month){
		Map<String, String> paramMap = new HashMap<String, String>(1);
		paramMap.put("{month}", month);
		return this.replaceSql(primitiveSql, paramMap);
	}

	private void executeSql(String primitiveSql, Map<String, String> paramMap) {
		String sql = this.replaceSql(primitiveSql, paramMap);
		tempDataDao.update(sql);
	}
	
	private void executeTimeSql(String primitiveSql,Map<String,String> paramMap,TimeType type,Object... args){
		String sql = this.replaceSql(primitiveSql, paramMap);
		tempDataDao.updateDateSql(sql, type,args);
	}
	
	public boolean timeAfter(String startTime,String endTime){
		boolean state = false;
		try {
			Date start = DateUtil.parseDateTime(startTime);
			Date end = DateUtil.parseDateTime(endTime);
			if(start.before(end)){
				state = true;
			}
			
		} catch (Exception e) {
			logger.error("startTime:"+startTime+" endTime:"+endTime+" "+e.getMessage(), e);
		}
		return state;
	}

}
