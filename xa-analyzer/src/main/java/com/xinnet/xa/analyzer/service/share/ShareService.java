package com.xinnet.xa.analyzer.service.share;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xinnet.share.backflow.common.BackflowConstant.BackflowDataType;
import com.xinnet.share.backflow.vo.BackflowData;
import com.xinnet.share.backflow.vo.BackflowRequest;
import com.xinnet.share.backflow.vo.BackflowResponse;
import com.xinnet.xa.analyzer.dao.ShareDao;
import com.xinnet.xa.core.service.ExceptionMessageService;

@Service
public class ShareService {
	private Logger logger = Logger.getLogger(ShareService.class);
	@Autowired
	private ShareDao shareDao;
	@Autowired
	private IBackflowService backflowService;
	@Autowired
	private ExceptionMessageService exceptionMessageService;

	
	
	public void backflowShareData(String startTime,String endTime){
		List<BackflowData> uvList = shareDao.findShareUv(startTime, endTime);
		this.backflow(startTime, endTime, BackflowDataType.UV, uvList);
		List<BackflowData> registerList = shareDao.findShareRegister(startTime, endTime);
		this.backflow(startTime, endTime,  BackflowDataType.REGISTER, registerList);
		List<BackflowData> orderList = shareDao.findShareOrder(startTime, endTime);
		this.backflow(startTime, endTime, BackflowDataType.ORDER, orderList);
	}
	
	

	private void backflow(String startTime,String endTime, BackflowDataType type,List<BackflowData> list) {
		if (CollectionUtils.isNotEmpty(list)) {
			BackflowRequest request = new BackflowRequest();
			request.setStartDate(startTime);
			request.setEndDate(endTime);
			request.setList(list);
			request.setType(type);
			BackflowResponse response = backflowService.backflow(request);
			this.dealResponse(response, type);
		} else {
			logger.info(startTime +" - "+endTime + " share "+type+" data is null");
		}
	}

 
	private void dealResponse(BackflowResponse response, BackflowDataType type) {
		if (!response.isSuccess()) {
			String errorMsg = " 营销中心  backflow " + type + " 接口错误"
					+ response.getErrorCode();
			logger.error(errorMsg);
			exceptionMessageService.sendException(new Exception(errorMsg));
		} else {
			logger.info(" 营销中心  backflow " + type + " success");
		}
	}

}
