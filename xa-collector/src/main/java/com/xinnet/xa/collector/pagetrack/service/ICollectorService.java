package com.xinnet.xa.collector.pagetrack.service;

import java.util.List;

import com.xinnet.xa.core.vo.TrackData;

/**
 * 功能描述：持久化消息队列，依靠线程组来启动后保存。
 */
public interface ICollectorService {
    /**
     * 功能描述：从MQ获取消息保存
     * @param dataList 
     * @param transformDataList
     */
	public void save(List<TrackData> dataList,List<TrackData> transformDataList);
	
	/**
	 * 功能描述：为线程组添加工作线程
	 * @param threadName
	 */
	public void addSaveThread(String threadName);
	
	
	/**
	 * 功能描述：线程组启动，保存MQ消息
	 */
	public void startWork();
}
