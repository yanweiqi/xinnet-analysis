package com.xinnet.xa.receive.service;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.xinnet.xa.core.service.ExceptionMessageService;

/**
 * @author 闫伟旗[yanweiqi@xinnet.com]
 * @version 
 * @since 2014-5-30
 */

@Service("messageService")
public class MessageService {

    private static Logger logger = LoggerFactory.getLogger(MessageService.class);
 
    @Autowired
    private ExceptionMessageService exceptionMessageService;
    
    @Autowired
    private JmsTemplate jmsTemplate;
    
    public void send(String destination,final String json){
    	//destination="queue0";
        logger.info("消息队列："+destination+",消息体="+json);
        try {
            jmsTemplate.send(destination, new MessageCreator() {
                 @Override
                 public Message createMessage(Session session) throws JMSException {
                     TextMessage textMessage = session.createTextMessage(json);
                     return textMessage;
                 }
            });
            logger.info("消息队列："+destination+",消息体="+json+",成功到达。");
        }
        catch (Exception e) {    
            logger.info("消息队列："+destination+",消息体="+json+",失败。");
            logger.error("消息发送异常！"+e.getMessage(),e);
            exceptionMessageService.sendException(e);
        }
    }

	public void sendObject(String destination, Object data) {
		String json ="";
		try {
			 json = JSON.toJSONString(data);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} 
		if(StringUtils.isNotBlank(json)){
			this.send(destination, json);
		}
	}
}

