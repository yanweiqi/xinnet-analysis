package com.xinnet.xa.analyzer.vo;

/**
 * 正式库订单详细 vo
 * @author lenovo
 *
 */
public class OrderDetail {
	
	private String operationTime;
	private String agentCode;
	private String OrderCode; 
	private String goodsName;
	private Double totalMoney;
	private String  isRefund;
	private String  buyType;
	private String superGoodsName;
	private String goodsClassName;
	
	public String getGoodsClassName() {
		return goodsClassName;
	}
	public void setGoodsClassName(String goodsClassName) {
		this.goodsClassName = goodsClassName;
	}
	public String getOperationTime() {
		return operationTime;
	}
	public void setOperationTime(String operationTime) {
		this.operationTime = operationTime;
	}
	public String getAgentCode() {
		return agentCode;
	}
	public void setAgentCode(String agentCode) {
		this.agentCode = agentCode;
	}
	public String getOrderCode() {
		return OrderCode;
	}
	public void setOrderCode(String orderCode) {
		OrderCode = orderCode;
	}

	public Double getTotalMoney() {
		return totalMoney;
	}
	public void setTotalMoney(Double totalMoney) {
		this.totalMoney = totalMoney;
	}
	public String getGoodsName() {
		return goodsName;
	}
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
	public String getSuperGoodsName() {
		return superGoodsName;
	}
	public void setSuperGoodsName(String superGoodsName) {
		this.superGoodsName = superGoodsName;
	}
	public String getIsRefund() {
		return isRefund;
	}
	public void setIsRefund(String isRefund) {
		this.isRefund = isRefund;
	}
	public String getBuyType() {
		return buyType;
	}
	public void setBuyType(String buyType) {
		this.buyType = buyType;
	}
	@Override
	public String toString() {
		return "OrderDetail [operationTime=" + operationTime + ", agentCode="
				+ agentCode + ", OrderCode=" + OrderCode + ", goodsName="
				+ goodsName + ", totalMoney=" + totalMoney + ", isRefund="
				+ isRefund + ", buyType=" + buyType + ", superGoodsName="
				+ superGoodsName + ", goodsClassName=" + goodsClassName + "]";
	}
	
	
}
