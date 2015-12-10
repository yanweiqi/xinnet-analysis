package com.xinnet.xa.collector.saletrack.service.impl;

import java.util.LinkedHashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.xinnet.xa.BaseSpringJunitUtil;
import com.xinnet.xa.collector.saletrack.entity.UserGoodsOrderPo;

public class UserGoodsOrderServiceTest extends BaseSpringJunitUtil{

	@Autowired
	private UserGoodsOrderService userGoodsOrderService;
	
	private Set<UserGoodsOrderPo> userGoodsOrders = new LinkedHashSet<UserGoodsOrderPo>();
	
	@Before
	public void init(){
//		for(int i=0; i<=10;i++){
//			UserGoodsOrderDetailVo userGoodsOrderPo = new UserGoodsOrderDetailVo();
//			userGoodsOrderPo.setGoodsName("云主机");
//			userGoodsOrderPo.setGoodsBuyLong("1.00");
//			userGoodsOrderPo.setGoodsMoney("2000.00");
//			userGoodsOrderPo.setAddShopCartDateTime(new Date().toString());
//			userGoodsOrders.add(userGoodsOrderPo);
		
//		}	
	}
	
	@Test
	public void testBatchInsert() throws Exception{
		userGoodsOrderService.batchInsert(userGoodsOrders);
	}
	
	@Test
	public void testBatchUpdate() throws Exception{
		userGoodsOrders = new LinkedHashSet<UserGoodsOrderPo>();
		for(int i=0; i<=1;i++){
			UserGoodsOrderPo userGoodsOrderPo = new UserGoodsOrderPo();
			userGoodsOrderPo.setBusinessOperationType("3");
			userGoodsOrderPo.setShopCartCookieId("28bccde8-ce2c-490f-a06a-7158654dfc8f");
			userGoodsOrderPo.setShopCartGoodsCookieId("2C72B63CFC804B259DF83B4C927CF1F1");
			userGoodsOrderPo.setGoodsName("尊享I型");
			userGoodsOrderPo.setHyCode("hy439009");
			userGoodsOrders.add(userGoodsOrderPo);
		}
		userGoodsOrderService.batchUpdate(userGoodsOrders);
	}
	
	@Test
	public void find(){
		
	}
	
}
