package com.xinnet.xa.analyzer.util;

public class TaobaoIpDTO {
	private int code;
	private TaoBaoIpData data;
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public TaoBaoIpData getData() {
		return data;
	}
	public void setData(TaoBaoIpData data) {
		this.data = data;
	}
	@Override
	public String toString() {
		return "TaobaoIpDTO [code=" + code + ", data=" + data + "]";
	}
	
	

}
