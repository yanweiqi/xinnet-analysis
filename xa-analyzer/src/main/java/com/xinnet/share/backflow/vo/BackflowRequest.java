package com.xinnet.share.backflow.vo;

import java.io.Serializable;
import java.util.List;

import com.xinnet.share.backflow.common.BackflowConstant.BackflowDataType;

public class BackflowRequest implements Serializable{
	
	
	private static final long serialVersionUID = -8725986849706648371L;
	private String startDate;
	 private String endDate;
	 private BackflowDataType type;
	 private List<BackflowData> list;
	 
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	
	public BackflowDataType getType() {
		return type;
	}
	public void setType(BackflowDataType type) {
		this.type = type;
	}
	public List<BackflowData> getList() {
		return list;
	}
	public void setList(List<BackflowData> list) {
		this.list = list;
	}
	 
	 
	 

}
