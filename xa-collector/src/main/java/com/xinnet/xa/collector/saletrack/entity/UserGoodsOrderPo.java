package com.xinnet.xa.collector.saletrack.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.xinnet.xa.collector.general.entity.IdEntity;


/**
 * @author 闫伟旗[yank@xinnet.com]
 * @version 1.0
 * @since 2014-11-04
 */
@Entity
@Table(name="analytics_user_goods_order_detail")
public class UserGoodsOrderPo extends IdEntity{
	
	@Id
	@GeneratedValue
	private Long id;

	@Column(name="hycode")
	private String  hyCode;                		//会员编号
	
	@Column(name="goods_name")
	private String  goodsName;			  		//商品名称
	
	@Column(name="goods_code")
	private String  goodsCode;			  		//商品编号
	
	@Column(name="goods_money")
	private Double  goodsMoney;            		//商品价格
	
	@Column(name="goods_number")
	private Integer goodsNumber;           		//商品数量
	
	@Column(name="goods_buy_long")
	private String goodsBuyLong;          		//商品购买时长
	
	@Column(name="goods_buy_times")
	private Integer goodsBuyTimes;         		//商品购买数量
	
	@Column(name="goods_buy_type")
	private String  goodsBuyType;           	//商品购买类型
	
	@Column(name="service_code")
	private String  goodsServiceCode;       	//商品服务编号
	
	@Column(name="shopcart_cookie_id")
	private String shopCartCookieId;       		//购物车CookieId
	
	@Column(name="add_shopcart_datetime")
	private Date    addShopCartDateTime;    	//购物车添商品加时间
	
	@Column(name="add_shopcart_current_url")
	private String  addShopCartCurrentUrl;      //添加购物车当前URL
	
	@Column(name="shopcart_order_total_money")
	private Double  shopCartOrderTotalMoney;	//购物车订单总额
	
	@Column(name="shopcart_order_code")
	private String shopCartOrderCode;      	    //购物车订单编号
	
	@Column(name="shopcart_settle_datetime")
	private Date  shopCartSettleDateTime;       //购物车结算时间
	
	@Column(name="goods_order_code")
	private String  goodsOrderCode;        		//商品订单编号
	
	@Column(name="goods_order_status")
	private String  goodsOrderStatus;           //订单状态
	
	@Column(name="order_pay_type")
	private String  orderPayType;               //订单付款类型
	
	@Column(name="order_success_pay_datetime")
	private Date  orderSuccessPayDateTime;      //订单成功支付时间
			      
	@Column(name="order_pay_bill_number")
	private String orderPayBillNumber;          //订单支付流水号
	
	@Column(name="user_cookie_id")
	private String  userCookieId;                //标志用户来源cookieId
	
	@Column(name="favorable_code")
	private String  favorableCode;               //优惠码
	
	@Column(name="shopcart_goods_cookie_id")
	private String shopCartGoodsCookieId;        //商品cookie_id
	
	@Transient
	private String  businessOperationType;       //操作类型
	
	@Column(name="shopcart_goods_status")
	private String  shopCartGoodsStatus;        //购物车商品状态 
	
	@Column(name="time_sequence")
	private String   timeSequence;              //时间顺序
	
	public Long getId() {return id;}
	public void setId(Long id) {this.id = id;}
	
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
	public Double getGoodsMoney() {
		return goodsMoney;
	}
	public void setGoodsMoney(Double goodsMoney) {
		this.goodsMoney = goodsMoney;
	}
	public Integer getGoodsNumber() {
		return goodsNumber;
	}
	public void setGoodsNumber(Integer goodsNumber) {
		this.goodsNumber = goodsNumber;
	}
	public String getGoodsBuyLong() {
		return goodsBuyLong;
	}
	public void setGoodsBuyLong(String goodsBuyLong) {
		this.goodsBuyLong = goodsBuyLong;
	}
	public Integer getGoodsBuyTimes() {
		return goodsBuyTimes;
	}
	public void setGoodsBuyTimes(Integer goodsBuyTimes) {
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
	public Date getAddShopCartDateTime() {
		return addShopCartDateTime;
	}
	public void setAddShopCartDateTime(Date addShopCartDateTime) {
		this.addShopCartDateTime = addShopCartDateTime;
	}
	public String getAddShopCartCurrentUrl() {
		return addShopCartCurrentUrl;
	}
	public void setAddShopCartCurrentUrl(String addShopCartCurrentUrl) {
		this.addShopCartCurrentUrl = addShopCartCurrentUrl;
	}
	public Double getShopCartOrderTotalMoney() {
		return shopCartOrderTotalMoney;
	}
	public void setShopCartOrderTotalMoney(Double shopCartOrderTotalMoney) {
		this.shopCartOrderTotalMoney = shopCartOrderTotalMoney;
	}
	public String getShopCartOrderCode() {
		return shopCartOrderCode;
	}
	public void setShopCartOrderCode(String shopCartOrderCode) {
		this.shopCartOrderCode = shopCartOrderCode;
	}
	public Date getShopCartSettleDateTime() {
		return shopCartSettleDateTime;
	}
	public void setShopCartSettleDateTime(Date shopCartSettleDateTime) {
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
	public Date getOrderSuccessPayDateTime() {
		return orderSuccessPayDateTime;
	}
	public void setOrderSuccessPayDateTime(Date orderSuccessPayDateTime) {
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
	public String getShopCartGoodsCookieId() {
		return shopCartGoodsCookieId;
	}
	public void setShopCartGoodsCookieId(String shopCartGoodsCookieId) {
		this.shopCartGoodsCookieId = shopCartGoodsCookieId;
	}
	
	public String getBusinessOperationType() {
		return businessOperationType;
	}
	public void setBusinessOperationType(String businessOperationType) {
		this.businessOperationType = businessOperationType;
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
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}	
}
