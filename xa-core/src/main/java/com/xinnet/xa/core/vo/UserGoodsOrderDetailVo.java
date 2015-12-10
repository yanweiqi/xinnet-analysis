package com.xinnet.xa.core.vo;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;


/**
 * @author 闫伟旗[yanweiqi@xinnet.com]
 * @version 1.0
 * @since 2014-11-04
 */
public class UserGoodsOrderDetailVo  {

	private String  hyCode;                		//会员编号
	private String  goodsName;			  		//商品名称
	private String  goodsCode;			  		//商品编号
	private String  goodsMoney;            		//商品价格
	private String  goodsNumber;           		//商品数量
	private String  goodsBuyLong;          		//商品购买时长
	private String  goodsBuyTimes;         		//商品购买数量
	private String  goodsBuyType;           	//商品购买类型
	private String  goodsServiceCode;       	//商品服务编号
	private String  shopCartCookieId;       	//购物车CookieId
	private String  addShopCartDateTime;    	//购物车添商品加时间
	private String  addShopCartCurrentUrl;      //添加购物车当前URL
	private String  shopCartOrderTotalMoney;	//购物车订单总额
	private String  shopCartOrderCode;      	//购物车订单编号
	private String  shopCartSettleDateTime;     //购物车结算时间
	private String  goodsOrderCode;        		//商品订单编号
	private String  goodsOrderStatus;           //订单状态
	private String  orderPayType;               //订单付款类型
	private String  orderSuccessPayDateTime;    //订单成功支付时间
	private String  orderPayBillNumber;         //订单支付流水号
	private String  userCookieId;               //标志用户来源cookieId
	private String  favorableCode;              //优惠码
	private String  businessOperationType;      //操作类型
	private String  shopCartGoodsCookieId;      //标示商品cookieId
	private String  shopCartGoodsStatus;        //购物车商品状态
	private String   timeSequence;                  //时间顺序
	
	public String getHyCode() {
		return hyCode;
	}
	public void setHyCode(String hyCode) {
		this.hyCode = hyCode;
	}
	public String getGoodsName() {
		return goodsName;
	}
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
	public String getGoodsCode() {
		return goodsCode;
	}
	public void setGoodsCode(String goodsCode) {
		this.goodsCode = goodsCode;
	}
	public String getGoodsMoney() {
		return goodsMoney;
	}
	public void setGoodsMoney(String goodsMoney) {
		this.goodsMoney = goodsMoney;
	}
	public String getGoodsNumber() {
		return goodsNumber;
	}
	public void setGoodsNumber(String goodsNumber) {
		this.goodsNumber = goodsNumber;
	}
	public String getGoodsBuyLong() {
		return goodsBuyLong;
	}
	public void setGoodsBuyLong(String goodsBuyLong) {
		this.goodsBuyLong = goodsBuyLong;
	}
	public String getGoodsBuyTimes() {
		return goodsBuyTimes;
	}
	public void setGoodsBuyTimes(String goodsBuyTimes) {
		this.goodsBuyTimes = goodsBuyTimes;
	}
	public String getGoodsBuyType() {
		return goodsBuyType;
	}
	public void setGoodsBuyType(String goodsBuyType) {
		this.goodsBuyType = goodsBuyType;
	}
	public String getGoodsServiceCode() {
		return goodsServiceCode;
	}
	public void setGoodsServiceCode(String goodsServiceCode) {
		this.goodsServiceCode = goodsServiceCode;
	}
	public String getShopCartCookieId() {
		return shopCartCookieId;
	}
	public void setShopCartCookieId(String shopCartCookieId) {
		this.shopCartCookieId = shopCartCookieId;
	}
	public String getAddShopCartDateTime() {
		return addShopCartDateTime;
	}
	public void setAddShopCartDateTime(String addShopCartDateTime) {
		this.addShopCartDateTime = addShopCartDateTime;
	}	
	public String getAddShopCartCurrentUrl() {
		return addShopCartCurrentUrl;
	}
	public void setAddShopCartCurrentUrl(String addShopCartCurrentUrl) {
		this.addShopCartCurrentUrl = addShopCartCurrentUrl;
	}
	public String getShopCartOrderTotalMoney() {
		return shopCartOrderTotalMoney;
	}
	public void setShopCartOrderTotalMoney(String shopCartOrderTotalMoney) {
		this.shopCartOrderTotalMoney = shopCartOrderTotalMoney;
	}
	public String getShopCartOrderCode() {
		return shopCartOrderCode;
	}
	public void setShopCartOrderCode(String shopCartOrderCode) {
		this.shopCartOrderCode = shopCartOrderCode;
	}
	public String getShopCartSettleDateTime() {
		return shopCartSettleDateTime;
	}
	public void setShopCartSettleDateTime(String shopCartSettleDateTime) {
		this.shopCartSettleDateTime = shopCartSettleDateTime;
	}
	public String getGoodsOrderCode() {
		return goodsOrderCode;
	}
	public void setGoodsOrderCode(String goodsOrderCode) {
		this.goodsOrderCode = goodsOrderCode;
	}
	public String getGoodsOrderStatus() {
		return goodsOrderStatus;
	}
	public void setGoodsOrderStatus(String goodsOrderStatus) {
		this.goodsOrderStatus = goodsOrderStatus;
	}
	public String getOrderPayType() {
		return orderPayType;
	}
	public void setOrderPayType(String orderPayType) {
		this.orderPayType = orderPayType;
	}
	public String getOrderSuccessPayDateTime() {
		return orderSuccessPayDateTime;
	}
	public void setOrderSuccessPayDateTime(String orderSuccessPayDateTime) {
		this.orderSuccessPayDateTime = orderSuccessPayDateTime;
	}
	public String getOrderPayBillNumber() {
		return orderPayBillNumber;
	}
	public void setOrderPayBillNumber(String orderPayBillNumber) {
		this.orderPayBillNumber = orderPayBillNumber;
	}
	public String getUserCookieId() {
		return userCookieId;
	}
	public void setUserCookieId(String userCookieId) {
		this.userCookieId = userCookieId;
	}
	public String getFavorableCode() {
		return favorableCode;
	}
	public void setFavorableCode(String favorableCode) {
		this.favorableCode = favorableCode;
	}
	public String getBusinessOperationType() {
		return businessOperationType;
	}
	public void setBusinessOperationType(String businessOperationType) {
		this.businessOperationType = businessOperationType;
	}
	public String getShopCartGoodsCookieId() {
		return shopCartGoodsCookieId;
	}
	public void setShopCartGoodsCookieId(String shopCartGoodsCookieId) {
		this.shopCartGoodsCookieId = shopCartGoodsCookieId;
	}
	public String getShopCartGoodsStatus() {
		return shopCartGoodsStatus;
	}
	public void setShopCartGoodsStatus(String shopCartGoodsStatus) {
		this.shopCartGoodsStatus = shopCartGoodsStatus;
	}
	public String getTimeSequence() {
		return timeSequence;
	}
	public void setTimeSequence(String timeSequence) {
		this.timeSequence = timeSequence;
	}
	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
    
	
}
