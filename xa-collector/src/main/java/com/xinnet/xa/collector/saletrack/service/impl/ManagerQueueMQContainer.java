package com.xinnet.xa.collector.saletrack.service.impl;

import org.apache.log4j.Logger;
import org.springframework.jms.listener.DefaultMessageListenerContainer;
import org.springframework.stereotype.Service;

import com.xinnet.xa.collector.pagetrack.service.ManagerCollectorService;

/**
 * 功能描述：管理MQ队列容器
 */
@Service
public class ManagerQueueMQContainer {

	private Logger logger = Logger.getLogger(ManagerCollectorService.class);
		
	public void stopJMSCollector(DefaultMessageListenerContainer dmlc) {
		dmlc.stop();
		logger.info("jms MQContainer stop");
	}

	public void startJMSCollector(DefaultMessageListenerContainer dmlc) {
		dmlc.start();
		logger.info("jms MQContainer start");
	}
	
	public boolean isRunning(DefaultMessageListenerContainer dmlc) {
		return dmlc.isRunning();
	}
}
