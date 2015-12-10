package com.xinnet.xa.core.vo;

public class CompData {

	private String ip;

	private int port;

	private String type;

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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String createCompUrl() {
		return "http://" + ip + ":" + port + "/" + type;
	}

	@Override
	public String toString() {
		return "CompData [ip=" + ip + ", port=" + port + ", type=" + type + "]";
	}

}
