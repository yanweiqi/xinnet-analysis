package com.xinnet.xa.analyzer.vo;

public class SEOSourceType {
	private String name;
	private String source;
	private String medium;
	private int soruceTypeId;
	private int channelId;
	
	
	public int getSoruceTypeId() {
		return soruceTypeId;
	}
	public void setSoruceTypeId(int soruceTypeId) {
		this.soruceTypeId = soruceTypeId;
	}
	public int getChannelId() {
		return channelId;
	}
	public void setChannelId(int channelId) {
		this.channelId = channelId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getMedium() {
		return medium;
	}
	public void setMedium(String medium) {
		this.medium = medium;
	}
	

}
