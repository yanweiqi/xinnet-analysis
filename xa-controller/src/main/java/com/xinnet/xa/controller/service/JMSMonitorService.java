package com.xinnet.xa.controller.service;

import java.io.IOException;
import java.util.Set;

import javax.management.MBeanServerConnection;
import javax.management.MBeanServerInvocationHandler;
import javax.management.ObjectInstance;
import javax.management.ObjectName;

import org.apache.activemq.broker.jmx.BrokerViewMBean;
import org.apache.activemq.broker.jmx.QueueViewMBean;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.xinnet.xa.controller.common.ControllerConstant;

@Service("jmsMonitorService")
@Lazy
public class JMSMonitorService {
	private Logger logger = Logger.getLogger(JMSMonitorService.class);
	@Autowired
	private BrokerViewMBean remoteJmsMbean;
	@Autowired 
	private MBeanServerConnection jmsMBeanServerClient;

	 
	
	public void initQueueViewMBean(){
		ControllerConstant.QUEUE_VIEW_MEANS.clear();
		ObjectName[] queueObjectNames = remoteJmsMbean.getQueues();
		logger.info("queue size:"+queueObjectNames.length);
		for(ObjectName queueName: queueObjectNames){
			QueueViewMBean queueMBean = (QueueViewMBean) MBeanServerInvocationHandler
					.newProxyInstance(jmsMBeanServerClient, queueName,
							QueueViewMBean.class, true);
//			ControllerConstant.QUEUE_VIEW_MEANS.add(queueMBean);
		}
	}
	
	public void allMbean() throws IOException{
		Set<ObjectInstance> mbeanSet = jmsMBeanServerClient.queryMBeans(null, null);
		for(ObjectInstance oi : mbeanSet){
			 System.out.println(oi.toString());
		 }
	}
	

}
