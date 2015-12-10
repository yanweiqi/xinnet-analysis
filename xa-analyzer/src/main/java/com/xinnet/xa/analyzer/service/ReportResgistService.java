package com.xinnet.xa.analyzer.service;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taobao.ad.easyschedule.dataobject.JobData;
import com.xinnet.xa.analyzer.common.AnalysisDataUtil;
import com.xinnet.xa.analyzer.common.AnalyzerConstant;
import com.xinnet.xa.analyzer.common.AnalyzerConstant.ReportType;
import com.xinnet.xa.analyzer.dao.ReportDao;
import com.xinnet.xa.core.utils.CSVUtil;
import com.xinnet.xa.core.utils.DateUtil;
import com.xinnet.xa.core.utils.SendMailUtil;
import com.xinnet.xa.core.vo.MailParams;

@Service
public class ReportResgistService {
	private Logger logger = Logger.getLogger(ReportResgistService.class);
	@Autowired
	private ReportDao reportDao;
	@Autowired
	private SendMailUtil sendMailUtil;
	@Autowired
	private AnalysisDataUtil analysisDataUtil;
	
	public File createCsv(String fileBasePath,ReportType reportType,String day){
		String[] dbColumns = reportType.getDbCloumnNames().split(",");
		List<String[]> dataList =reportDao.getReportData(reportType.getDbSql(), dbColumns, day);
		String filePath = fileBasePath+reportType.getCsvFileName();
		String[] titles = reportType.getCsvCloumnNames().split(",");
		return CSVUtil.writeCSVByList(filePath, dataList, titles);
	}
	
	public  File[] getCsvFiles(String day){
		ReportType[] types = ReportType.values();
		List<File> csvFiles = new ArrayList<File>();
		String path = this.getFilePath(day);
		File[] files = null;
		for(ReportType reportType : types){
			File csvFile = createCsv(path, reportType, day);
			if(csvFile!=null){
				logger.info(reportType.getCsvFileName()+" create file succuess");
				csvFiles.add(csvFile);
			}
		}
		if(CollectionUtils.isNotEmpty(csvFiles)){
			files = csvFiles.toArray(new File[csvFiles.size()]);
		}
		return files;
	}
	
	
	
	public void sendCsvEmail(String day) throws Exception{
		if(StringUtils.isBlank(day)){
			day = DateUtil.addDay(new Date(), -1);
		}
		File[] files = this.getCsvFiles(day);
		MailParams mailParams = new MailParams();
		mailParams.setSubject(AnalyzerConstant.REPORT_TITLE);
		Map<String, String> params = new HashMap<String, String>();
		params.put("day", day);
		String text = sendMailUtil.getHtmlTextFromFreeMarker("reportRegist.ftl",
				params);
		mailParams.setBody(text);
		mailParams.setHtml(true);
		mailParams.setTo(AnalyzerConstant.MAIL_TO);
		mailParams.setCc(AnalyzerConstant.MAIL_CC);
		mailParams.setAttachments(files);
		sendMailUtil.sendMail(mailParams);
		 
	}
	
	public void sendReportResgisterJob(JobData jobData){
		String day = DateUtil.addDay(new Date(), -1);
		 new ReportThread(jobData, day).start();
	}
	
	public void sendReportByDay(String yesterday){
		new ReportThread(null, yesterday).start();
	}
	
	private String getFilePath(String day){
		return AnalyzerConstant.REPORT_FILE_PATH+File.separator+day+File.separator;
	}
	
	class ReportThread extends Thread{
		private JobData jobData;
		private String yesterday;
		public ReportThread(JobData jobData, String yesterday) {
			super();
			this.jobData = jobData;
			this.yesterday = yesterday;
		}
		@Override
		public void run() {
			 boolean success = false;
			 try {
				 sendCsvEmail(yesterday);
				 success=true;
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
			 if(jobData!=null){
				 analysisDataUtil.sendJob(success, jobData);
			 }
		}
		
	}
	
	

}
