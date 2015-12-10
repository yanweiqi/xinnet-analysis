package com.xinnet.xa.collector.saletrack.service.impl;

import org.apache.commons.lang.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xinnet.xa.collector.general.service.JobThreadSchedul;
import com.xinnet.xa.collector.util.CollectorConstant;
import com.xinnet.xa.core.vo.UserGoodsOrderDetailVo;

/**
 * 功能描述：通过线程组来管理【消息持久化】工作线程
 * @author yanweiqi
 */
@Service("managerThreadGroupService")
public class ManagerThreadGroupService {
		
	@Autowired
	private UserGoodsOrderService userGoodsOrderService;
	
	private final static String blockingQueue_Key = CollectorConstant.DATA_BLOCKINGQUEUE_USERGOODSORDER_KEY;
	private final static ThreadGroup thread_group = CollectorConstant.MESSAGE_COLLECTOR_THREAD_GROUP;

	/**
	 * 功能描述：获取线程组中处于活动状态的线程数量
	 * @return int 
	 */
	public int getThreadActiveCount() {
		return thread_group.activeCount();
	}
	
	/**
	 * 功能描述：减少线程组中的工作线程【每次递减一个线程】
	 * @return int
	 */
	public int decrementThreadWorker(){
		Thread[]  threadWorks = new Thread[this.getThreadActiveCount()];
		thread_group.enumerate(threadWorks);
		if(ArrayUtils.isNotEmpty(threadWorks)){
			JobThreadSchedul<?> t =  (JobThreadSchedul<?>) threadWorks[0];
			t.interrupt();
		}
		return getThreadActiveCount();
	}
	
    /**
     * 功能描述：递增线程组中的工作线程【每次增加一个工作线程】
     * @return int
     */
	public int incrementThreadWorker(JobThreadSchedul<?> t){
		t.start();
		try {
			Thread.sleep(1000*10);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return getThreadActiveCount();
	}
	
	/**
	 * 功能描述：启动Job,线程组开始工作
	 */
	public void startDoJob(){
		for (int i=1; i<=CollectorConstant.SAVE_THREAD_NUM; i++) {
			JobThreadSchedul<UserGoodsOrderDetailVo> job = new JobThreadSchedul<UserGoodsOrderDetailVo>(thread_group,"UserGoodsOrderJobWork"+i,userGoodsOrderService,blockingQueue_Key);
			job.start();
		}
	}
	
}
