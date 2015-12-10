package com.xinnet.xa.controller.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="monitor_component_state")
public class CompMonitor {
	@Id
	@Column(name="id")
	@GeneratedValue
	private Integer id;
	@Column(name="ip")
	private String ip;
	@Column(name="port")
	private Integer port;
	@Column(name="receiveCount")
	private Integer receiveCount;
	@Column(name="collectorQueueCount")
	private Integer collectorQueueCount;
	@Column(name="collectorThreadStates")
	private String collectorThreadStates;
	@Column(name="state")
	private Integer state;
	@Column(name="monitorTime")
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
	public Timestamp getMonitorTime() {
		return monitorTime;
	}
	public void setMonitorTime(Timestamp monitorTime) {
		this.monitorTime = monitorTime;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	

}
