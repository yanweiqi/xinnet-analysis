package com.xinnet.xa.analyzer.vo;


public class UrlInfo implements Comparable<UrlInfo> {
	private String url;
	private int typeId;
	private int detailId;
	private int priority;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int getTypeId() {
		return typeId;
	}

	public void setTypeId(int typeId) {
		this.typeId = typeId;
	}

	public int getDetailId() {
		return detailId;
	}

	public void setDetailId(int detailId) {
		this.detailId = detailId;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	@Override
	public int compareTo(UrlInfo o) {
		if (priority == o.getPriority())
			return 0;
		return o.getPriority()-priority;
	}

}
