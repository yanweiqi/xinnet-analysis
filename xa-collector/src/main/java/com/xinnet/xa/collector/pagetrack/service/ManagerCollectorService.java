package com.xinnet.xa.collector.pagetrack.service;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.commons.lang.ArrayUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.listener.DefaultMessageListenerContainer;
import org.springframework.stereotype.Service;

import com.xinnet.xa.collector.pagetrack.service.impl.AbstractCollectorService.SaveThread;
import com.xinnet.xa.collector.util.CollectorConstant;
import com.xinnet.xa.core.Constant;
import com.xinnet.xa.core.vo.CompMonitorData;

@Service
public class ManagerCollectorService {
	private Logger logger = Logger.getLogger(ManagerCollectorService.class);
	// private Logger ramLogger = Logger.getLogger("RAMDataLog");
	private Lock lock = new ReentrantLock();
	
	@Autowired                              
	private DefaultMessageListenerContainer advancedQueueContainer;
	
	@Autowired
	private DefaultMessageListenerContainer goodsBuyQueueContainer;
	
	@Autowired
	private ICollectorService collectorService;

	public void stopJMSCollector() {
		try {
			lock.lock();
			if (advancedQueueContainer.isRunning()) {
				advancedQueueContainer.stop();
				logger.info("advancedQueueContainer jms customer stop");
			}
			
			if(goodsBuyQueueContainer.isRunning()){
				goodsBuyQueueContainer.stop();
				logger.info("goodsBuyQueueContainer jms customer stop" );
			}

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			lock.unlock();
		}

	}

	public void startJMSCollector() {
		try {
			lock.lock();
			if (!advancedQueueContainer.isRunning()) {
				advancedQueueContainer.start();
				logger.info("advancedQueueContainer jms customer start");
			}
			
			if (!goodsBuyQueueContainer.isRunning()) {
				goodsBuyQueueContainer.start();
				logger.info("goodsBuyQueueContainer jms customer start");
			}

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			lock.unlock();
		}
	}

	public boolean isRunning() {
		return advancedQueueContainer.isRunning();
	}

	/**
	 * 功能描述：获取线程组中线程数量
	 * @return int 
	 */
	public int saveThreadCount() {
		return CollectorConstant.MESSAGE_COLLECTOR_THREAD_GROUP.activeCount();
	}

	/**
	 * 功能描述：减少线程组中工作线程
	 * @return int
	 */
	public int lessenSaveThread() {
		int count = saveThreadCount();
		Thread[] saveThreads = new Thread[count];
		CollectorConstant.MESSAGE_COLLECTOR_THREAD_GROUP.enumerate(saveThreads); 
		if (ArrayUtils.isNotEmpty(saveThreads)) {
			SaveThread st = (SaveThread) saveThreads[0];
			st.threadStop();
		}
		return saveThreadCount();
	}

	/**
	 * 功能描述：获取阻塞队列大小
	 * @return int
	 */
	public int getQueueCount() {
		return CollectorConstant.DATA_QUEUE.size();
	}

	/**
	 * 功能描述：增加一个工作线程
	 * @return
	 */
	public int addSaveThread() {
		int count = saveThreadCount();
		collectorService.addSaveThread("save_" + (count + 1) + "_thread");
		return saveThreadCount();
	}

	/**
	 * 功能描述：启动收集器持久化队列信息
	 */
	public void startSaveThread() {
		collectorService.startWork();
	}

	// public void writeRAMData() {
	// stopJMSCollector();
	// int count = this.saveThreadCount();
	// while (count > 0) {
	// lessenSaveThread();
	// count--;
	// }
	//
	// if (CollectorConstant.DATA_QUEUE.size() > 0) {
	// while (true) {
	// TrackData data = CollectorConstant.DATA_QUEUE.poll();
	// if (data == null) {
	// break;
	// } else {
	// ramLogger.info(JSON.toJSON(data));
	// }
	// }
	// }
	// logger.info("writeRAMData end");
	// }

	public CompMonitorData getmMonitorData() {
		CompMonitorData data = new CompMonitorData();
		data.setState(Constant.COMP_STATE_OK);
		data.setCollectorQueueCount(getQueueCount());
		int count = saveThreadCount();
		Thread[] saveThreads = new Thread[count];
		CollectorConstant.MESSAGE_COLLECTOR_THREAD_GROUP.enumerate(saveThreads);
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < count; i++) {
			sb.append(saveThreads[i].getName()).append(":").append(saveThreads[i].isAlive());
			if (i < count - 1) sb.append(";");
		}
		data.setCollectorThreadStates(sb.toString());
		return data;

	}

}
