package com.xinnet.xa.collector.saletrack.vo;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;

import com.xinnet.xa.collector.saletrack.entity.UserGoodsOrderPo;


/**
 * 类描述：商品销售数据操作类型
 * @author 闫伟旗[yanweiqi@xinnet.com]
 * @version 
 * @since 2014-11-11
 */
public enum BusinessOperationType {
	
	/**
	 * 功能描述：商品加入购物车
	 */
     GOODSADDSHOPCART(1,"save","商品加入购物车","add"),
     
     /**
      * 功能描述：商品移除购物车
      */
     GOODSREMOVESHOPCART(2,"update","商品移除购物车","remove"),
     
     /**
      * 功能描述：进入购物车
      */
     INTOSHOPCART(3,"update","进入购物车","into"),
     
     /**
      * 功能描述：购物车商品结算
      */
     SHOPCARTSETTLEMENT(4,"update","购物车商品结算,生成订单","to_orders"),
     
     /**
      * 功能描述：订单结算
      */
     ORDERPAYMENTTRACK(5,"update","订单结算","pay_orders"),
     
     /**
      * 功能描述：更新数据
      */
     UPDATEGOODSORDERSHOPCART(6,"update","更新数据","");
     
     private int index;
     private String opertionType;
     private String descriptions;
     private String mark;
     
     private static final Logger logger = Logger.getLogger(BusinessOperationType.class);
     
     private BusinessOperationType(int index,String opertionType,String descriptions,String mark){
    	 this.index = index;
    	 this.opertionType = opertionType;
    	 this.descriptions =  descriptions;
    	 this.mark =mark;
     } 
      	
 	/**
 	 * 功能描述：订单编号
 	 */
 	public static String generateShopCartOrderCode(UserGoodsOrderPo userGoodsOrderPo){
 		StringBuilder sb = new StringBuilder();
		try {
	 		int hash = Math.abs(userGoodsOrderPo.getHyCode().hashCode());
	 		sb.append(hash);
			String shopCartSettleDateTime = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
			sb.append(shopCartSettleDateTime);
			sb.append(userGoodsOrderPo.getGoodsNumber());
		} catch (Exception e) {
			e.printStackTrace();
		}
		String shopCartOrderCode = sb.toString();
		logger.debug("Shopcart Settle goods then generate shopCartOrderCode "+shopCartOrderCode);
 		return shopCartOrderCode;
 	}
     
	public int getIndex() {	return index;}
	public void setIndex(int index) {this.index = index;}

	public String getOpertionType() {return opertionType;}
	public void setOpertionType(String opertionType) {this.opertionType = opertionType;}

	public String getDescriptions() {return descriptions;}
	public void setDescriptions(String descriptions) {this.descriptions = descriptions;}

	public String getMark() {return mark;}
	public void setMark(String mark) {this.mark = mark;}
	
}


