package com.xinnet.xa.collector.saletrack.jms;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import com.alibaba.fastjson.JSON;
import com.xinnet.xa.BaseSpringJunitUtil;
import com.xinnet.xa.core.vo.UserGoodsOrderDetailVo;

public class GoodsBuyAnalystMessageListenerTest extends BaseSpringJunitUtil{

	private Logger logger = Logger.getLogger(GoodsBuyAnalystMessageListenerTest.class);
	
	private UserGoodsOrderDetailVo userGoodsOrderDetailVo = null;
	
	@Test
	public void testOnMessage() {
		
		logger.info("start");
		
		try {
			Thread.sleep(1000*60*5);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	
	@Before
	public void init(){
		String msg = "{\"addShopCartDateTime\":\"\",\"businessOperationType\":\"1\",\"favorableCode\":\"\",\"goodsBuyLong\":\"5.0\",\"goodsBuyTimes\":\"\",\"goodsBuyType\":\"新开\",\"goodsCode\":\"001\",\"goodsMoney\":\"2000.0\",\"goodsName\":\"香港云主机\",\"goodsNumber\":\"10\",\"goodsOrderCode\":\"\",\"goodsOrderStatus\":\"\",\"goodsServiceCode\":\"\",\"hyCode\":\"hy439009\",\"orderPayBillNumber\":\"\",\"orderPayType\":\"\",\"orderSuccessPayDateTime\":\"\",\"shopCartCookieId\":\"\",\"shopCartOrderCode\":\"\",\"shopCartOrderTotalMoney\":\"\",\"shopCartSettleDateTime\":\"\",\"userCookieId\":\"\"}";
		//logger.info("result:"+msg);
		userGoodsOrderDetailVo = new UserGoodsOrderDetailVo();
		userGoodsOrderDetailVo =  JSON.parseObject(msg, UserGoodsOrderDetailVo.class);
	}

}
