package com.xinnet.xa.receive.service;

import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import com.xinnet.xa.utils.ReceiveContant;

@Service("receiveService")
public class ReceiveService {
	private Logger logger = Logger.getLogger(ReceiveService.class);

	@Autowired
	private ThreadPoolTaskExecutor reciveExecutor;

	@Autowired
	private MessageService messageService;

	public void sendMessage(final Object data) {

		Runnable worker = new Runnable() {
			@Override
			public void run() {
				while (!ReceiveContant.run.get()) {
					try {
						Thread.sleep(1000 * 10);
					} catch (InterruptedException e) {
						logger.error(e.getMessage(), e);
					}
				}
				String queueName = getQueueName();
				messageService.sendObject(queueName, data);
				ReceiveContant.RECE_COUNT.incrementAndGet();
			}
		};
		reciveExecutor.execute(worker);
	}

	/**
	 * 功能描述：发送商品订单购买信息到MQ队列
	 * @author 闫伟旗[yanweiqi@xinnet.com]
	 * @since 2014-6-5
	 * @update:[变更日期YYYY-MM-DD][更改人姓名][变更描述]
	 */
	public void sendMessageToMQ(final Map<String, Object> map){
		
		for (final Entry<String, Object> entry : map.entrySet()) {
			Runnable worker = new Runnable() {
				public void run() {
					messageService.sendObject(entry.getKey().toString(), entry.getValue());
				}
			};
			reciveExecutor.execute(worker);
		}
	}
	
	
	private String getQueueName() {
		int queueNum = ReceiveContant.QUEUES.size();
		if (queueNum == 1) return ReceiveContant.QUEUES.get(0);
		if (ReceiveContant.RECE_COUNT.get() >= Integer.MAX_VALUE) ReceiveContant.RECE_COUNT.set(0);
		int num = ReceiveContant.RECE_COUNT.get() % queueNum;
		return ReceiveContant.QUEUES.get(num);
	}
}
