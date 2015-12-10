package com.xinnet.share.backflow.vo;

import java.io.Serializable;

public class BackflowResponse implements Serializable{
	private static final long serialVersionUID = 948812373613172685L;
	private boolean success;
	private String errorCode;
	
	
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public String getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	
	

}
