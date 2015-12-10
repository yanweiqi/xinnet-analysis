package com.xinnet.xa.core.vo;

public class CompMonitorData {
	
	private Integer state;
	
    private Integer receiveCount;
    
	private Integer collectorQueueCount;
	
	private String collectorThreadStates;
	
	public Integer getReceiveCount() {
		return receiveCount;
	}
	public void setReceiveCount(Integer receiveCount) {
		this.receiveCount = receiveCount;
	}
	public Integer getCollectorQueueCount() {
		return collectorQueueCount;
	}
	public void setCollectorQueueCount(Integer collectorQueueCount) {
		this.collectorQueueCount = collectorQueueCount;
	}
	public String getCollectorThreadStates() {
		return collectorThreadStates;
	}
	public void setCollectorThreadStates(String collectorThreadStates) {
		this.collectorThreadStates = collectorThreadStates;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	
	
	
	

}
