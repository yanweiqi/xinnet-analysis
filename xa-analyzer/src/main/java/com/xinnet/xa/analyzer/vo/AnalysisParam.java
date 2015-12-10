package com.xinnet.xa.analyzer.vo;

import java.text.ParseException;
import java.util.Date;

import org.apache.log4j.Logger;

import com.xinnet.xa.analyzer.common.AnalyzerConstant;
import com.xinnet.xa.analyzer.common.AnalyzerConstant.TimeType;
import com.xinnet.xa.core.utils.DateUtil;

public class AnalysisParam {
	private Logger logger = Logger.getLogger(getClass());
	private String time;
	private TimeType type;
	private String startTime;
	private String endTime;
	private String day;
	private String month;
	

	public AnalysisParam(String time, TimeType type) {
		super();
		this.time = time;
		this.type = type;
		analysisTime();
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public TimeType getType() {
		return type;
	}

	public void setType(TimeType type) {
		this.type = type;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}
   
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
    
	
	
	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}
    
	
	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	private void analysisTime() {
		this.day=time;
		if (type == TimeType.DAY) {
			startTime = time + AnalyzerConstant.START_TIME;
			endTime = time + AnalyzerConstant.END_TIME;
		}
		if (type == TimeType.HOUR) {
			this.convertHour();
			try {
				day =DateUtil.formatDate(DateUtil.parseDateDateHour(time));
			} catch (ParseException e) {
				logger.error(e.getMessage(), e);
			}
		}
		converMonth();
	}

	private void convertHour() {
		StringBuilder sb = new StringBuilder();
		sb.append(time.substring(0, 4));
		sb.append("-").append(time.substring(4, 6)).append("-")
				.append(time.substring(6, 8)).append(" ")
				.append(time.substring(8, 10));

		startTime = sb.toString() + ":00:00";
		endTime = sb.toString() + ":59:59";
	}
	
	private void converMonth(){
		StringBuilder sb = new StringBuilder();
		month=sb.append(day.substring(0, 4)).append("_").append(day.substring(5, 7)).toString();
	}
	
	 

	public String getAfterHourEndTime() {
		String afterTime = endTime;
		try {
			Date date = DateUtil.parseDateTime(endTime);
			afterTime = DateUtil.formatDateTime(DateUtil.addHours(date, 1));
		} catch (ParseException e) {
			logger.error(e.getMessage(), e);
		}
		return afterTime;
	}

	public String getBeforHour() {
		String beforHour = time;
		try {
			if (type == TimeType.DAY) {
				beforHour = DateUtil.formatDateHour(DateUtil.parseDate(time));
			}
			beforHour = DateUtil.addHoursString(beforHour, -1);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return beforHour;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((time == null) ? 0 : time.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AnalysisParam other = (AnalysisParam) obj;
		if (time == null) {
			if (other.time != null)
				return false;
		} else if (!time.equals(other.time))
			return false;
		if (type != other.type)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "AnalysisParam [time=" + time + ", type=" + type
				+ ", startTime=" + startTime + ", endTime=" + endTime + "]";
	}
	
	

}
