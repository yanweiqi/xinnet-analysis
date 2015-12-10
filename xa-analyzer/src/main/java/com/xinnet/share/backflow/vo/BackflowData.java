package com.xinnet.share.backflow.vo;

import java.io.Serializable;

public class BackflowData implements Serializable{
	private static final long serialVersionUID = 583661181142151333L;
	private int shareTypeId;
	private String shareToolName;
	private String shareToolId;
	private String shareHycode;
	private String area;
	private String ip;
	private String accessTime;
	private String code;
	
	public int getShareTypeId() {
		return shareTypeId;
	}
	public void setShareTypeId(int shareTypeId) {
		this.shareTypeId = shareTypeId;
	}
	public String getShareToolName() {
		return shareToolName;
	}
	public void setShareToolName(String shareToolName) {
		this.shareToolName = shareToolName;
	}
	public String getShareToolId() {
		return shareToolId;
	}
	public void setShareToolId(String shareToolId) {
		this.shareToolId = shareToolId;
	}
	public String getShareHycode() {
		return shareHycode;
	}
	public void setShareHycode(String shareHycode) {
		this.shareHycode = shareHycode;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getAccessTime() {
		return accessTime;
	}
	public void setAccessTime(String accessTime) {
		this.accessTime = accessTime;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	
	@Override
	public String toString() {
		return "BackflowData [shareTypeId=" + shareTypeId
				+ ", shareToolName=" + shareToolName + ", shareToolId="
				+ shareToolId + ", shareHycode=" + shareHycode + ", area="
				+ area + ", ip=" + ip + ", accessTime=" + accessTime
				+ ", code=" + code + "]";
	}

}
