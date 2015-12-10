package com.xinnet.xa.core.vo;

public class Site {
	private String sitName;
	private String sitId;
	private String userId;

	public String getSitName() {
		return sitName;
	}

	public void setSitName(String sitName) {
		this.sitName = sitName;
	}

	public String getSitId() {
		return sitId;
	}

	public void setSitId(String sitId) {
		this.sitId = sitId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	@Override
	public String toString() {
		return "Site [sitName=" + sitName + ", sitId=" + sitId + ", userId="
				+ userId + "]";
	}
	
	

}
