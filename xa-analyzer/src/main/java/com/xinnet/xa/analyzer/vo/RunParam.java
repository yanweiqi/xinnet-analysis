package com.xinnet.xa.analyzer.vo;

import java.util.List;

import com.taobao.ad.easyschedule.dataobject.JobData;
import com.xinnet.xa.analyzer.service.IAnalysisService;

public class RunParam {
	private  AnalysisParam analysisparam;
	private JobData jobData;
	private List<IAnalysisService> analysisServices;
	
	
	public RunParam(AnalysisParam analysisparam,
			List<IAnalysisService> analysisServices) {
		super();
		this.analysisparam = analysisparam;
		this.analysisServices = analysisServices;
	}
	
	public AnalysisParam getAnalysisparam() {
		return analysisparam;
	}
	
	public void setAnalysisparam(AnalysisParam analysisparam) {
		this.analysisparam = analysisparam;
	}
	public JobData getJobData() {
		return jobData;
	}
	public void setJobData(JobData jobData) {
		this.jobData = jobData;
	}
	public List<IAnalysisService> getAnalysisServices() {
		return analysisServices;
	}
	public void setAnalysisServices(List<IAnalysisService> analysisServices) {
		this.analysisServices = analysisServices;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((analysisparam == null) ? 0 : analysisparam.hashCode());
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
		RunParam other = (RunParam) obj;
		if (analysisparam == null) {
			if (other.analysisparam != null)
				return false;
		} else if (!analysisparam.equals(other.analysisparam))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "RunParam [analysisparam=" + analysisparam + "]";
	}
	
	
	

}
