package com.xinnet.xa.analyzer.service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.StopWatch;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taobao.ad.easyschedule.dataobject.JobData;
import com.xinnet.xa.analyzer.common.AnalysisDataUtil;
import com.xinnet.xa.analyzer.common.AnalyzerConstant;
import com.xinnet.xa.analyzer.common.AnalyzerConstant.TimeType;
import com.xinnet.xa.analyzer.service.share.ShareService;
import com.xinnet.xa.analyzer.vo.AnalysisParam;
import com.xinnet.xa.analyzer.vo.RunParam;
import com.xinnet.xa.core.service.ExceptionMessageService;
import com.xinnet.xa.core.utils.DateUtil;

@Service
public class DataAnalysisService {
	private Logger logger = Logger.getLogger(getClass());
	private List<IAnalysisService> hourServiceList;
	private List<IAnalysisService> dayServiceList;
	private List<IAnalysisService> qualityServiceList;

	@Autowired
	private IAnalysisService tempDataByTimeAnalysisService;
	@Autowired
	private IAnalysisService trafficByTimeAnalysisService;
	@Autowired
	private IAnalysisService trafficQualityAnalysisService;
	@Autowired
	private IAnalysisService hyCodeByTimeAnalysisService;
	@Autowired
	private IAnalysisService registerByTimeAnalysisService;
	@Autowired
	private IAnalysisService orderByTimeAnalysisService;
	@Autowired
	private ExceptionMessageService exceptionMessageService;
	@Autowired
	private AnalysisDataUtil analysisDataUtil;
	@Autowired
	private ShareService shareService;

	@PostConstruct
	public void init() {
		this.createHourAnalysisServiceList();
		this.createQualityServiceList();
		this.createDayAnalysisServiceList();
		new AnalysisThread().start();
	}

	private void createHourAnalysisServiceList() {
		hourServiceList = new ArrayList<IAnalysisService>();
		hourServiceList.add(tempDataByTimeAnalysisService);
		hourServiceList.add(trafficByTimeAnalysisService);
		hourServiceList.add(hyCodeByTimeAnalysisService);
		hourServiceList.add(registerByTimeAnalysisService);
		hourServiceList.add(orderByTimeAnalysisService);

	}

	private void createQualityServiceList() {
		qualityServiceList = new ArrayList<IAnalysisService>();
		qualityServiceList.add(trafficQualityAnalysisService);

	}

	private void createDayAnalysisServiceList() {
		dayServiceList = new ArrayList<IAnalysisService>();
		dayServiceList.addAll(hourServiceList);
		dayServiceList.addAll(qualityServiceList);

	}

	private Date addTime(Date date, int amount, TimeType type) {
		Date result = null;
		switch (type) {
		case HOUR:
			result = DateUtil.addHours(date, amount);
			break;
		case DAY:
			result = DateUtil.addDays(date, amount);
			break;
		default:
			break;
		}
		return result;
	}

	private Date parseDate(String source, TimeType type) throws ParseException {
		Date result = null;
		switch (type) {
		case HOUR:
			result = DateUtil.parseDateDateHour(source);
			break;
		case DAY:
			result = DateUtil.parseDate(source);
			break;

		default:
			break;
		}
		return result;
	}

	private void addJobRunParamList(List<RunParam> runParamList, JobData jobData) {
		if (CollectionUtils.isNotEmpty(runParamList)) {
			if (jobData != null) {
				RunParam param = runParamList.get(runParamList.size() - 1);
				param.setJobData(jobData);
			}
			this.addAnalysisQueue(runParamList);

		} else {
			logger.info("runParamList is empty");
		}

	}

	public boolean runTimeRange(String startTime, String endTime,
			JobData jobData, TimeType type) {
		boolean state = false;
		try {
			Date endDate = StringUtils.isBlank(endTime) ? addTime(new Date(),
					-1, type) : this.parseDate(endTime, type);
			Date startDate = StringUtils.isBlank(startTime) ? addTime(
					parseDate(analysisDataUtil.getMaxTime(type), type), 1, type)
					: parseDate(startTime, type);
			if (!startDate.after(endDate)) {
				List<RunParam> runParamList = new ArrayList<RunParam>();
				if (TimeType.HOUR == type) {
					getServices(startDate, endDate, runParamList);
				} else if (TimeType.DAY == type) {
					do {
						String day = DateUtil.formatDate(startDate);
						runParamList.addAll(this.createDayRunParam(day));
						startDate = DateUtil.addDays(startDate, 1);
					} while (!startDate.after(endDate));
				}
				addJobRunParamList(runParamList, jobData);
				state=true;
			} else {
				logger.error("start date:" + DateUtil.formatDateTime(startDate)
						+ " befor end date:" + DateUtil.formatDateTime(endDate));
			}

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return state;
	}

	public static void main(String[] args) {
		DataAnalysisService d = new DataAnalysisService();
		d.runTimeRange("2014122000", null, null, TimeType.HOUR);

	}

	private void addAnalysisQueue(List<RunParam> runParamList) {
		for (RunParam rp : runParamList) {
			if (!AnalyzerConstant.ANALYSIS_QUEUE.contains(rp)) {
				try {
					AnalyzerConstant.ANALYSIS_QUEUE.put(rp);
					logger.info(rp.toString() + " add queue");
				} catch (InterruptedException e) {
					logger.error(rp.toString(), e);
				}
			} else {
				logger.info(rp.toString() + " is exists");
			}

		}
	}

	private void getServices(Date startDate, Date endDate,
			List<RunParam> runParamList) throws ParseException {

		String startDay = DateUtil.formatDate(startDate);
		String endDay = DateUtil.formatDate(endDate);
		if (startDay.equals(endDay)) {
			List<RunParam> subRunParam = this.getServicesByOneDay(startDate,
					endDate, startDay);
			if (CollectionUtils.isNotEmpty(subRunParam)) {
				runParamList.addAll(subRunParam);
			}
			return;
		} else {
			Date dayEndDate = covHourDate(DateUtil.parseDateTime(startDay
					+ AnalyzerConstant.END_TIME));
			List<RunParam> subRunParam = this.getServicesByOneDay(startDate,
					dayEndDate, startDay);
			if (CollectionUtils.isNotEmpty(subRunParam)) {
				runParamList.addAll(subRunParam);
			}
			startDate = DateUtil.addHours(dayEndDate, 1);
			getServices(startDate, endDate, runParamList);
		}
	}

	private Date covHourDate(Date date) throws ParseException {
		String hour = DateUtil.formatDateHour(date);
		return DateUtil.parseDateDateHour(hour);
	}

	private List<RunParam> getServicesByOneDay(Date startDate, Date endDate,
			String day) {
		long hourDifference = DateUtil.timeDifferenceHour(endDate, startDate);
		List<RunParam> runParamList = hourDifference == 23 ? this
				.createDayRunParam(day) : this.createDayOfHourRunParam(
				startDate, hourDifference, day);
		return runParamList;
	}

	private List<RunParam> createDayRunParam(String day) {
		List<RunParam> runParamList = new ArrayList<RunParam>();
		AnalysisParam dayParam = new AnalysisParam(day, TimeType.DAY);
		RunParam dayRunParam = new RunParam(dayParam, dayServiceList);
		runParamList.add(dayRunParam);
		return runParamList;
	}

	private List<RunParam> createDayOfHourRunParam(Date startDate,
			long hourDifference, String day) {
		List<RunParam> runParamList = new ArrayList<RunParam>();
		for (int i = 0; i <= hourDifference; i++) {
			createHourRunParam(startDate, runParamList);
			startDate = DateUtil.addHours(startDate, 1);
		}
		return runParamList;
	}

	private List<RunParam> createHourRunParam(Date startDate,
			List<RunParam> runParamList) {
		// List<RunParam> runParamList = new ArrayList<RunParam>();
		String hour = DateUtil.formatDateHour(startDate);
		AnalysisParam ap = new AnalysisParam(hour, TimeType.HOUR);
		RunParam rp = new RunParam(ap, hourServiceList);
		runParamList.add(rp);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(startDate);
		if (calendar.get(Calendar.HOUR_OF_DAY) == 23) {
			AnalysisParam qualityParam = new AnalysisParam(
					DateUtil.formatDate(startDate), TimeType.DAY);
			RunParam qualityRunParam = new RunParam(qualityParam,
					qualityServiceList);
			runParamList.add(qualityRunParam);
		}
		return runParamList;
	}

	class AnalysisThread extends Thread {

		@Override
		public void run() {
			while (true) {
				boolean state = false;
				JobData jobData = null;
				RunParam param = null;
				try {
					 param = AnalyzerConstant.ANALYSIS_QUEUE.take();
					StopWatch watch = new StopWatch();
					watch.start();
					logger.info(param.toString() + " start");
					List<IAnalysisService> list = param.getAnalysisServices();
					AnalysisParam analysisParam = param.getAnalysisparam();
					jobData = param.getJobData();
					for (IAnalysisService analysisService : list) {
						analysisService.analysis(analysisParam);
					}
					shareService.backflowShareData(analysisParam.getStartTime(), analysisParam.getEndTime());
					state = true;
					watch.stop();
					logger.info(param.toString() + " end run time:"
							+ watch.getTime());
				} catch (Exception e) {
					String msg=param==null?e.getMessage():param.toString()+" "+e.getMessage();
					logger.error(msg, e);
					exceptionMessageService.sendException(e);
				}
				if (jobData != null) {
					analysisDataUtil.sendJob(state, jobData);
				}

			}
		}

	}

}
