package com.xinnet.xa.core.vo;


public class TomcatMonitorData {
    private String startTime;
    private Long continuousWorkingTime;// 连续工作时间
    private Long maxHeap;
    private Long currentAssignmentHeap;// 当前分配的堆
	private Long usedHeap;
	private Double heapUsage;// 堆使用率
	private Long maxNonHeap;
	private Long currentAssignmentNonHeap;// 当前分配的堆
	private Long usedNonHeap;
	private Double nonheapUsage;// 堆使用率
	private Long permGen;
	private Long currentAssignmentPermGen;
	private Long usedPermGen;
	private Double permGenUsage;// 持久堆使用率
	private Integer maxThreads;
	private Integer currentThreadCount;
	private Integer currentThreadsBusy;
	
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public Long getContinuousWorkingTime() {
		return continuousWorkingTime;
	}
	public void setContinuousWorkingTime(Long continuousWorkingTime) {
		this.continuousWorkingTime = continuousWorkingTime;
	}
	public Long getMaxHeap() {
		return maxHeap;
	}
	public void setMaxHeap(Long maxHeap) {
		this.maxHeap = maxHeap;
	}
	public Long getCurrentAssignmentHeap() {
		return currentAssignmentHeap;
	}
	public void setCurrentAssignmentHeap(Long currentAssignmentHeap) {
		this.currentAssignmentHeap = currentAssignmentHeap;
	}
	public Long getUsedHeap() {
		return usedHeap;
	}
	public void setUsedHeap(Long usedHeap) {
		this.usedHeap = usedHeap;
	}
	public Double getHeapUsage() {
		return heapUsage;
	}
	public void setHeapUsage(Double heapUsage) {
		this.heapUsage = heapUsage;
	}
	public Long getMaxNonHeap() {
		return maxNonHeap;
	}
	public void setMaxNonHeap(Long maxNonHeap) {
		this.maxNonHeap = maxNonHeap;
	}
	public Long getCurrentAssignmentNonHeap() {
		return currentAssignmentNonHeap;
	}
	public void setCurrentAssignmentNonHeap(Long currentAssignmentNonHeap) {
		this.currentAssignmentNonHeap = currentAssignmentNonHeap;
	}
	public Long getUsedNonHeap() {
		return usedNonHeap;
	}
	public void setUsedNonHeap(Long usedNonHeap) {
		this.usedNonHeap = usedNonHeap;
	}
	public Double getNonheapUsage() {
		return nonheapUsage;
	}
	public void setNonheapUsage(Double nonheapUsage) {
		this.nonheapUsage = nonheapUsage;
	}
	public Long getPermGen() {
		return permGen;
	}
	public void setPermGen(Long permGen) {
		this.permGen = permGen;
	}
	public Long getCurrentAssignmentPermGen() {
		return currentAssignmentPermGen;
	}
	public void setCurrentAssignmentPermGen(Long currentAssignmentPermGen) {
		this.currentAssignmentPermGen = currentAssignmentPermGen;
	}
	public Long getUsedPermGen() {
		return usedPermGen;
	}
	public void setUsedPermGen(Long usedPermGen) {
		this.usedPermGen = usedPermGen;
	}
	public Double getPermGenUsage() {
		return permGenUsage;
	}
	public void setPermGenUsage(Double permGenUsage) {
		this.permGenUsage = permGenUsage;
	}
	public Integer getMaxThreads() {
		return maxThreads;
	}
	public void setMaxThreads(Integer maxThreads) {
		this.maxThreads = maxThreads;
	}
	public Integer getCurrentThreadCount() {
		return currentThreadCount;
	}
	public void setCurrentThreadCount(Integer currentThreadCount) {
		this.currentThreadCount = currentThreadCount;
	}
	public Integer getCurrentThreadsBusy() {
		return currentThreadsBusy;
	}
	public void setCurrentThreadsBusy(Integer currentThreadsBusy) {
		this.currentThreadsBusy = currentThreadsBusy;
	}
	
	

 

}
