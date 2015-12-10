package com.xinnet.xa.core.vo;

import java.io.Serializable;
import java.util.Date;

public class ComponentData implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 805694831091656853L;
	private String ip;
	private int port;
	private String componentType;
	private String componentID;
	private Date lastModified;
	private int status;
	private String description;
	
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	public String getComponentType() {
		return componentType;
	}
	public void setComponentType(String componentType) {
		this.componentType = componentType;
	}
	
	
	 
	public Date getLastModified() {
		return lastModified;
	}
	public void setLastModified(Date lastModified) {
		this.lastModified = lastModified;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getComponentID() {
		return componentID;
	}
	public void setComponentID(String componentID) {
		this.componentID = componentID;
	}
	
	
	
	

}
