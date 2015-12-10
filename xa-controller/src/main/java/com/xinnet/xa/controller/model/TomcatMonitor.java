package com.xinnet.xa.controller.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="monitor_tomcat")
public class TomcatMonitor {
	@Id
	@Column(name="id")
	@GeneratedValue
	private Integer id;
	@Column
	private String ip;
	@Column
	private Integer port;
	@Column
	private String startTime;
	@Column
	private Long continuousWorkingTime;//连续工作时间
	@Column
	private Long maxHeap;
	@Column
	private Long currentAssignmentHeap;//当前分配的堆
	@Column
	private Long usedHeap;
	@Column
	private Double heapUsage;//堆使用率
	@Column
	private Long maxNonHeap;
	@Column
	private Long currentAssignmentNonHeap;//当前分配的堆
	@Column
	private Long usedNonHeap;
	@Column
	private Double nonheapUsage;//堆使用率
	@Column
	private Long permGen;
	@Column
	private Long currentAssignmentPermGen;
	@Column
	private Long usedPermGen;
	@Column
	private Double permGenUsage;//持久堆使用率
	@Column
	private Integer maxThreads;
	@Column
	private Integer currentThreadCount;
	@Column
	private Integer currentThreadsBusy;
	@Column
	private Timestamp monitorTime;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public Integer getPort() {
		return port;
	}
	public void setPort(Integer port) {
		this.port = port;
	}
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
	public Timestamp getMonitorTime() {
		return monitorTime;
	}
	public void setMonitorTime(Timestamp monitorTime) {
		this.monitorTime = monitorTime;
	}
	
	
}
