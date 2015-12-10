package com.xinnet.xa.core.vo;

public class CompConstantData {
	private String queues;
	private Integer manageListSize;
	private String types;
	private Integer saveThreadNum;

	public String getQueues() {
		return queues;
	}

	public void setQueues(String queues) {
		this.queues = queues;
	}

	public Integer getManageListSize() {
		return manageListSize;
	}

	public void setManageListSize(Integer manageListSize) {
		this.manageListSize = manageListSize;
	}

	public String getTypes() {
		return types;
	}

	public void setTypes(String types) {
		this.types = types;
	}

	public Integer getSaveThreadNum() {
		return saveThreadNum;
	}

	public void setSaveThreadNum(Integer saveThreadNum) {
		this.saveThreadNum = saveThreadNum;
	}

	@Override
	public String toString() {
		return "CompConstantData [queues=" + queues + ", manageListSize="
				+ manageListSize + ", types=" + types + ", saveThreadNum="
				+ saveThreadNum + "]";
	}
	
	

}
