package com.xinnet.xa.jms.service;

import com.alibaba.dubbo.common.utils.ConcurrentHashSet;

/**
 * 
 * @author 闫伟旗[yanweiqi@xinnet.com]
 * @version 
 * @since 2014-6-4
 */
public interface IMessageService {

	/**
	 * 功能描述：发送消息
	 * @param destination 目的地
	 * @param json 消息体
	 * @author 闫伟旗[yanweiqi@xinnet.com]
	 * @since 2014-6-4
	 * @update:[变更日期YYYY-MM-DD][更改人姓名][变更描述]
	 */
	public void send(String destination,final String json);
	
	public void sendObject(String destination,final Object data);
	
	/**
	 * 功能描述：接收消息
	 * @return  String 消息体
	 * @author 闫伟旗[yanweiqi@xinnet.com]
	 * @since 2014-6-4
	 * @update:[变更日期YYYY-MM-DD][更改人姓名][变更描述]
	 */
	public ConcurrentHashSet<String> receive(String queuename)throws InterruptedException;
}
