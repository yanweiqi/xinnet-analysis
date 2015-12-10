package com.xinnet.xa.collector.pagetrack.jms;

import java.util.concurrent.ArrayBlockingQueue;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;
import com.xinnet.xa.collector.util.CollectorConstant;
import com.xinnet.xa.collector.util.IpUtil;
import com.xinnet.xa.core.vo.TrackData;

public class AnalystMessageListener implements MessageListener {
	private Logger logger = Logger.getLogger(AnalystMessageListener.class);
	
	private final static String trackData_key = CollectorConstant.DATA_BLOCKINGQUEUE_TRACKDATE_KEY;
	
	@SuppressWarnings("unchecked")
	@Override
	public void onMessage(Message message) {
		TextMessage textMessage = (TextMessage) message;
		try {
			String result = textMessage.getText();
			logger.info("result:"+result);
			TrackData data =  JSON.parseObject(result, TrackData.class);
			data.analystData(CollectorConstant.TRANSFORM_TYPES);
			String ip = data.getIp();
			ip = ip.length()>30?ip.substring(0, 25):ip;
			data.setIp(ip);
			String area = IpUtil.getProvince(data.getIp());
			data.setArea(area);
			((ArrayBlockingQueue<TrackData>) CollectorConstant.DATA_MAP_BLOCKINGQUEUE.get(trackData_key)).put(data);;
			logger.info(data);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

	}

}
