package com.xinnet.xa.analyzer.web;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xinnet.xa.analyzer.common.AnalysisDataUtil;
import com.xinnet.xa.analyzer.service.share.ShareService;

@Controller
@RequestMapping("/share")
public class ShareController {
	private Logger logger = Logger.getLogger(ShareController.class);
	
	@Autowired
	private ShareService shareService;
	@Autowired
	private AnalysisDataUtil analysisDataUtil;
	
	@RequestMapping("/backflow.do")
	public @ResponseBody String backflowDataByHour(String startTime,String endTime){
		logger.info(startTime+ " "+ endTime);
		boolean state = analysisDataUtil.timeAfter(startTime, endTime);
		if(state){
			shareService.backflowShareData(startTime, endTime);
		}
		String msg = state ? "param error": "success";
		return msg;
	}
	
	


}
