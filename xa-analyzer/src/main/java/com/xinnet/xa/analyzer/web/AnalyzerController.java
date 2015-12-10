package com.xinnet.xa.analyzer.web;

import java.text.ParseException;
import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taobao.ad.easyschedule.dataobject.JobData;
import com.taobao.ad.easyschedule.dataobject.JobResult;
import com.xinnet.xa.analyzer.common.AnalyzerConstant.TimeType;
import com.xinnet.xa.analyzer.service.DataAnalysisService;
import com.xinnet.xa.analyzer.service.ReportResgistService;
import com.xinnet.xa.core.utils.DateUtil;

@Controller
public class AnalyzerController {
	private Logger logger = Logger.getLogger(AnalyzerController.class);
	@Autowired
	private DataAnalysisService dataAnalysisService;
	@Autowired
	private ReportResgistService reportResgistService;
	
	@RequestMapping("/analysisByHourJob.do")
	public @ResponseBody JobResult analy(JobData jobData){
		logger.info(jobData);
		boolean state  = dataAnalysisService.runTimeRange("", "", jobData, TimeType.HOUR);
		JobResult result = state? JobResult.succcessResult():JobResult.errorResult(JobResult.RESULTCODE_JOB_REQUEST_FAILURE, "system error");
		result.setJobId(jobData.getJobId());
		return result;
	}
	
	@RequestMapping("/analysisByDayJob.do")
	public @ResponseBody JobResult analysisByDay(JobData jobData){
		boolean state  =dataAnalysisService.runTimeRange("", "", jobData, TimeType.DAY);
		JobResult result = state? JobResult.succcessResult():JobResult.errorResult(JobResult.RESULTCODE_JOB_REQUEST_FAILURE, "system error");
		result.setJobId(jobData.getJobId());
		return result;
	}
	
	@RequestMapping("/analysisHourRange.do")
	public @ResponseBody String analysisHourRange(String starHour,String endHour){
		 
		boolean state = dataAnalysisService.runTimeRange(starHour, endHour, null,TimeType.HOUR);
		String result = state?"Please check the time format":"Analysis of thread has been started, please check later";
		return result;
	}
	
	@RequestMapping("/analysisDayRange.do")
	public @ResponseBody String analysisDayRange(String starDay,String endDay){
		 
		boolean state = dataAnalysisService.runTimeRange(starDay, endDay, null,TimeType.DAY);
		String result = state?"Please check the time format":"Analysis of thread has been started, please check later";
		return result;
	}
	
	private boolean dateCheck(String day){
		if(DateUtil.isDate(day)){
			Date date =null;
			try {
				 date = DateUtil.parseDate(day);
			} catch (ParseException e) {
				logger.error(e.getMessage(), e);
			}
			if(date!=null && date.compareTo(new Date())<=0){
				return true;
			}
		}
		return false;
	}
	
	@RequestMapping("/sendResgistReportJob.do")
	public @ResponseBody JobResult sendResgistReportJob(JobData jobData){
		reportResgistService.sendReportResgisterJob(jobData);
		JobResult result = JobResult.succcessResult();
		result.setJobId(jobData.getJobId());
		return result;
	}
	
	@RequestMapping("/sendResgistReportByDay.do")
	public @ResponseBody String sendResgistReportByDay(String day){
		String result="Please check the time format";
		if(this.dateCheck(day)){
			reportResgistService.sendReportByDay(day);
			result = "Send Report of thread has been started, please check  your email later";
		}
		return result;
	}

}
