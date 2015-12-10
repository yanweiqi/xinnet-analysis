package com.xinnet.xa.collector.general.service;

import java.util.LinkedHashSet;
import java.util.Set;

import com.xinnet.xa.collector.util.CollectorConstant;


/**
 * 功能描述：【某某】工作任务调度器，调度线程工作
 * @author yanweiqi
 */
public class JobThreadSchedul<T> extends ThreadWorker {
		
		private Set<T> setStoreContainer = new LinkedHashSet<T>();
		private IBusinessService<T> businessService;
		private String blockingQueueKey;

		public JobThreadSchedul(ThreadGroup g, String name,IBusinessService<T> businessService,String blockingQueueKey) {
			super(g, name);
			this.businessService = businessService;
			this.blockingQueueKey = blockingQueueKey;
		}

		@Override
		protected void doWork() {
			while(true){
				if(CollectorConstant.DATA_MAP_BLOCKINGQUEUE.get(blockingQueueKey).size() > 0){
					@SuppressWarnings("unchecked")
					T data = (T)  CollectorConstant.DATA_MAP_BLOCKINGQUEUE.get(blockingQueueKey).poll();
					if(null != data){
						setStoreContainer.add(data);
						if(setStoreContainer.size() >= CollectorConstant.MANAGE_MESSAGE_SIZE){ 
							logger.info("DATA_BLOCKINGQUEUE remain size "+CollectorConstant.DATA_MAP_BLOCKINGQUEUE.get(blockingQueueKey).size());
							try {
								businessService.processingData(setStoreContainer);
							} catch (Exception e) {
							  logger.error("current ERROR is ignore.",e);	
							  continue;
							}
							finally{
								setStoreContainer.clear();
							}
						}
						else {
							continue;
						}
					}
				}
				else{
					if(setStoreContainer.size() > 0){
						logger.info("DATA_BLOCKINGQUEUE remain size "+CollectorConstant.DATA_MAP_BLOCKINGQUEUE.get(blockingQueueKey).size());
						try {
							businessService.processingData(setStoreContainer);
						} catch (Exception e) {	
							logger.error("current ERROR is ignore.",e);
							continue;
						}
						finally{
							setStoreContainer.clear();
						}
					}
					else{
						//队列全部消耗完毕，存储容器已经全部入库，线程进入休眠2ms。
						logger.info("DATA_BLOCKINGQUEUE consumed complate,SetStoreContainer saved complate.Ready sleep 2ms.");
						try {
							Thread.sleep(1000*2);
						} catch (InterruptedException e) {
							logger.error("InterruptedException current ERROR is ignore.",e);
							continue;
						}						
					}
				}				
			}	
		}
		
		@Override
		public void run() {
			try {
				doWork();
			} catch (Exception e) {
				logger.error("工作任务调度器执行失败", e);
			}
			logger.info(this.getName() + " start.");
		}
    
}
