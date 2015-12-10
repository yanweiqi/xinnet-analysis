package com.xinnet.xa.receive.service;

import static org.junit.Assert.fail;

import java.util.Date;
import java.util.Map;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.collect.Maps;
import com.xinnet.xa.core.vo.UserGoodsOrderDetailVo;
import com.xinnet.xa.receive.BaseSpringJunitUtil;

public class ReceiveServiceTest extends BaseSpringJunitUtil{
	
	@Autowired
	private ReceiveService receiveService;

	@Test
	public void testSendMessage() {
		fail("Not yet implemented");
	}

	@Test
	public void testSendMessageToMQ() {
		Map<String, Object> paraMap = Maps.newHashMap();
		int i = 0;
		while(true) {
			UserGoodsOrderDetailVo u = new  UserGoodsOrderDetailVo();
			u.setGoodsName("云主机"+i);
			u.setGoodsBuyLong("1.00");
			u.setGoodsMoney("2000.00");
			u.setAddShopCartDateTime(new Date().toString());
			paraMap.put("goodsBuyQueue", u);
			receiveService.sendMessageToMQ(paraMap);
			paraMap.clear();
			i++;
			try {
				Thread.sleep(1000*2);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		
	}

}
