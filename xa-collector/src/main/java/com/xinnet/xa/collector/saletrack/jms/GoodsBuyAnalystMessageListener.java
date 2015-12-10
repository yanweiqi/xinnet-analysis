package com.xinnet.xa.collector.saletrack.jms;

import java.util.concurrent.PriorityBlockingQueue;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.xinnet.xa.collector.saletrack.service.impl.ManagerThreadGroupService;
import com.xinnet.xa.collector.util.CollectorConstant;
import com.xinnet.xa.core.vo.UserGoodsOrderDetailVo;

/**
 * 功能描述：收集商品购买队列消息
 * @author 闫伟旗[yanweiqi@xinnet.com]
 * @since 2014-6-5
 * @update:[变更日期YYYY-MM-DD][更改人姓名][变更描述]
 */
public class GoodsBuyAnalystMessageListener implements MessageListener {

	private Logger logger = Logger.getLogger(GoodsBuyAnalystMessageListener.class);
	private final static String usergoodsorder_key = CollectorConstant.DATA_BLOCKINGQUEUE_USERGOODSORDER_KEY;
	
	@Autowired
	private ManagerThreadGroupService managerThreadGroupService;
	
	
	@SuppressWarnings("unchecked")
	@Override
	public void onMessage(Message message) {
		TextMessage textMessage = (TextMessage) message;
		try {
			String result = textMessage.getText();
			logger.info("result:"+result);
			UserGoodsOrderDetailVo vo =  JSON.parseObject(result, UserGoodsOrderDetailVo.class);
			((PriorityBlockingQueue<UserGoodsOrderDetailVo>) CollectorConstant.DATA_MAP_BLOCKINGQUEUE.get(usergoodsorder_key)).put(vo);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

}
