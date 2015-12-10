package com.xinnet.xa.controller.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="monitor_component_exception")
public class CompExceptionMessage {
	@Id
	@Column(name="id")
	@GeneratedValue
	private Integer id;
	@Column(name="ip")
	private String ip;
	@Column(name="port")
	private Integer port;
	@Column(name="comp_type")
	private String compType;
	@Column(name="message")
	private String message;
	
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
	public String getCompType() {
		return compType;
	}
	public void setCompType(String compType) {
		this.compType = compType;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message.length()>200?message.substring(0, 200):message;
	}
	
	

}
