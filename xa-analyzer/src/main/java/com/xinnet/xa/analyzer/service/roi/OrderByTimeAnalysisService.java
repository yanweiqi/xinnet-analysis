package com.xinnet.xa.analyzer.service.roi;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.xinnet.xa.analyzer.common.AnalysisDataUtil;
import com.xinnet.xa.analyzer.common.AnalyzerConstant;
import com.xinnet.xa.analyzer.common.SqlByTime;
import com.xinnet.xa.analyzer.dao.OrderDataDao;
import com.xinnet.xa.analyzer.service.IAnalysisService;
import com.xinnet.xa.analyzer.vo.AnalysisParam;
import com.xinnet.xa.analyzer.vo.OrderDetail;
import com.xinnet.xa.core.service.ExceptionMessageService;
import com.xinnet.xa.core.utils.HttpClientUtil;

@Service
public class OrderByTimeAnalysisService implements IAnalysisService {
	private Logger logger = Logger.getLogger(getClass());
	@Autowired
	private OrderDataDao orderDataDao;
	@Autowired
	private AnalysisDataUtil analysisDataUtil;
	@Autowired
	private ExceptionMessageService exceptionMessageService;

	@Override
	public void analysis(AnalysisParam analysisParam) {
		
		boolean state = this.insertDbData(analysisParam);
		if(state){
		   this.analysisGoodsOrder(analysisParam);
		   this.analysisDetailData(analysisParam);
           this.analysisOrderCountByProuctLine(analysisParam);
           analysisOrderCountByUrl(analysisParam);
		}
	}
	
	private void analysisGoodsOrder(AnalysisParam analysisParam){
		analysisDataUtil.deleteTableBytime(SqlByTime.ANA_GOODS_DETAIL_BY_URL, analysisParam.getType(), analysisParam.getTime());
		orderDataDao.updateDateSql(SqlByTime.INSERT_GOODS_ORDER_DETAIL_BY_URL, analysisParam.getType(), analysisParam.getTime(),analysisParam.getStartTime(),analysisParam.getEndTime());
		analysisDataUtil.analysisUrlDetailId(SqlByTime.ANA_GOODS_DETAIL_BY_URL, "request_url", analysisParam.getTime(), analysisParam.getType());
		analysisDataUtil.deleteTableBytime(SqlByTime.ANA_SHOPCAT_COUNT_BY_URL, analysisParam.getType(), analysisParam.getTime());
		orderDataDao.updateDateSql(SqlByTime.INSERT_GOODS_ORDER_COUNT_SHOPING_CAR,analysisParam.getType(), analysisParam.getTime());
		orderDataDao.update(SqlByTime.INSERT_GOODS_ORDER_COUNT_HAVE_TO_PAY,analysisParam.getStartTime(),analysisParam.getEndTime());
		
	}
	
	private boolean insertDbData(AnalysisParam analysisParam){
		boolean state = false;
		List<OrderDetail> list = this.getOrderDataByTime(analysisParam.getStartTime(), analysisParam.getEndTime());
		if(CollectionUtils.isNotEmpty(list)){
		    analysisDataUtil.deleteTableBytime(SqlByTime.ANA_ORDER_DETAIL, analysisParam.getType(), analysisParam.getTime());
		    this.batchInser(list);
		    state = true;
		}
		return state;
		
	}
	
	private void analysisDetailData(AnalysisParam analysisParam){
		orderDataDao.update(SqlByTime.UPDATE_ORDER_DETAIL_DATE,analysisParam.getStartTime(),analysisParam.getEndTime());
		analysisAreadId(analysisParam);
		orderDataDao.updateDateSql(SqlByTime.UPDATE_ORDER_DETAIL_PRODUCT_LINE_BY_GA_SORUCE, analysisParam.getType(), analysisParam.getTime());
		orderDataDao.updateDateSql(SqlByTime.UPDATE_ORDER_DETAIL_PRODUCT_LINE_BY_XA_SORUCE, analysisParam.getType(), analysisParam.getTime());
		orderDataDao.updateDateSql(SqlByTime.UPDATE_ORDER_DETAIL_CHANNEL_NULL, analysisParam.getType(), analysisParam.getTime());
		orderDataDao.updateDateSql(SqlByTime.UPDATE_ORDER_PRODUCT_LINE_NULL, analysisParam.getType(), analysisParam.getTime());
		this.analysisOrderDetailNAProductLineAndUrl(analysisParam);
		this.analysisOrderDetailBuyType(analysisParam);
		this.analysisOrderDetailPayType(analysisParam);
		orderDataDao.updateDateSql(SqlByTime.UPDATE_ORDER_DETAIL_COOKIES_ID, analysisParam.getType(), analysisParam.getDay()+AnalyzerConstant.START_TIME,analysisParam.getDay()+AnalyzerConstant.END_TIME,analysisParam.getTime());
		orderDataDao.updateDateSql(SqlByTime.UPDATE_ORDER_DETAIL_LANDING_PAGE_URL_DETAIL_ID, analysisParam.getType(),analysisParam.getTime());
		
	}
	
	private void analysisAreadId(AnalysisParam analysisParam){
		orderDataDao.updateDateSql(SqlByTime.UPDATE_ORDER_DETAIL_AREA_ID_BY_HY_SOURCE, analysisParam.getType(), analysisParam.getTime());
		orderDataDao.updateDateSql(SqlByTime.UPDATE_ORDER_DETAIL_AREA_ID_UNKOWN, analysisParam.getType(), analysisParam.getTime());
	}
	
	private void analysisOrderCountByProuctLine(AnalysisParam analysisParam){
		analysisDataUtil.deleteTableBytime(SqlByTime.ANA_ORDER_COUNT, analysisParam.getType(), analysisParam.getTime());
		orderDataDao.updateDateSql(SqlByTime.INSERT_ORDER_COUNT,analysisParam.getType(), analysisParam.getTime());
		 
	}
	
	private void analysisOrderDetailPayType(AnalysisParam analysisParam){
		for(AnalyzerConstant.OrderPayType payType:AnalyzerConstant.OrderPayType.values()){
			String sql = SqlByTime.UPDATE_ORDER_DETAIL_PAY_TYPE.replace("${type}", String.valueOf(payType.getType())).replace("${condition}", payType.getCondition());
			orderDataDao.updateDateSql(sql, analysisParam.getType(), analysisParam.getTime());
		}
	}
	
	private void analysisOrderDetailBuyType(AnalysisParam analysisParam){
		orderDataDao.updateDateSql(SqlByTime.UPDATE_ORDER_DETAIL_BUY_TYPE_RENEW,analysisParam.getType(), analysisParam.getTime());
		orderDataDao.updateDateSql(SqlByTime.UPDATE_ORDER_DETAIL_BUY_TYPE_RETURN_PREMIUM,analysisParam.getType(), analysisParam.getTime());
		orderDataDao.updateDateSql(SqlByTime.UPDATE_ORDER_DETAIL_BUY_TYPE_POUNDAGE,analysisParam.getType(), analysisParam.getTime());
		orderDataDao.updateDateSql(SqlByTime.UPDATE_ORDER_DETAIL_BUY_TYPE_OPEING_OF_NEW,analysisParam.getType(), analysisParam.getTime());
		logger.info("end analysisOrderDetailBuyType");
	}
	
	private void analysisOrderCountByUrl(AnalysisParam analysisParam){
		analysisDataUtil.deleteTableBytime(SqlByTime.ANA_ORDER_COUNT_URL, analysisParam.getType(), analysisParam.getTime());
		orderDataDao.updateDateSql(SqlByTime.UPDATE_ORDER_COUNT_BY_URL,analysisParam.getType(), analysisParam.getTime());
		orderDataDao.updateDateSql(SqlByTime.UPDATE_ORDER_COUNT_BY_URL_BY_LANDING_PAGE, analysisParam.getType(),analysisParam.getTime());
	}
	
	private void analysisOrderDetailNAProductLineAndUrl(AnalysisParam analysisParam){
		for(AnalyzerConstant.OrderMatchType matchType: AnalyzerConstant.OrderMatchType.values()){
			String sqlBase = SqlByTime.UPDATE_ORDER_DETAIL_MATCH_BASE.replace("${type}", String.valueOf(matchType.getType())).replace("${condition}", matchType.getCondition());
			orderDataDao.updateDateSql(sqlBase+SqlByTime.UPDATE_ORDER_DETAIL_SPECIAL_DATE_PRODUCT_LINE,analysisParam.getType(), analysisParam.getTime());
		}
		orderDataDao.updateDateSql(SqlByTime.UPDATE_ORDER_DETAIL_URL_DETAIL_ID_BY_SHOPCAR_URL,analysisParam.getType(),analysisParam.getStartTime(),analysisParam.getEndTime(),analysisParam.getTime());
		orderDataDao.updateDateSql(SqlByTime.UPDATE_ORDER_DETAIL_BY_OLD,analysisParam.getType(),analysisParam.getTime());
		orderDataDao.updateDateSql(SqlByTime.UPDATE_ORDER_DETAIL_OTHER_URL_DETAIL_ID,analysisParam.getType(),analysisParam.getTime());
	}
	
	private void batchInser(List<OrderDetail> list){
		int batchSize=200;
		int length = list.size();
		int count = length%batchSize>0? (length/batchSize)+1:length/100;
		int start =0;
		int end = 0;
		for(int i=0;i<count;i++){
			start = i*batchSize;
			end = i==count-1?length:start+batchSize;
			List<OrderDetail> subList = list.subList(start, end);
			orderDataDao.batchInsertData(subList);
		}
		
	}
	
	private List<OrderDetail> getOrderDataByTime(String startTime,String endTime){
		List<OrderDetail> list = new ArrayList<OrderDetail>(1000);
		Map<String, String> params = new HashMap<String, String>();
		params.put("startDate", startTime);
		params.put("endDate", endTime);
		logger.info("to get order date from "+AnalyzerConstant.ORDER_DATA_URL);
		try {
			String  result = HttpClientUtil.post(AnalyzerConstant.ORDER_DATA_URL, params, "utf-8");
		    list = JSON.parseArray(result, OrderDetail.class);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			exceptionMessageService.sendException(e);
		}
		
		return list;
	}
	 

}
