package com.xinnet.xa.collector.pagetrack.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.xinnet.xa.collector.pagetrack.service.ICollectorService;
import com.xinnet.xa.collector.util.CollectorConstant;
import com.xinnet.xa.core.vo.TrackData;

public abstract class AbstractCollectorService implements ICollectorService {
	Logger logger = Logger.getLogger(getClass());

	public void startWork() {
		for (int i = 1; i <= CollectorConstant.SAVE_THREAD_NUM; i++) {
			this.addSaveThread("save_" + i + "_thread");
		}
		CollectorConstant.SAVE_THREAD_RUM.set(true);
	}

	public void addSaveThread(String threadName) {
		new SaveThread(CollectorConstant.MESSAGE_COLLECTOR_THREAD_GROUP,threadName).start();
	}

	/**
	 * 功能描述：持久化队列工作线程
	 */
	public class SaveThread extends Thread {
		private boolean run = true;
		private List<TrackData> dataList = new ArrayList<TrackData>(CollectorConstant.MANAGE_MESSAGE_SIZE);
		private List<TrackData> transformDataList = new ArrayList<TrackData>(CollectorConstant.MANAGE_MESSAGE_SIZE);

		public SaveThread(ThreadGroup g, String name) {
			super(g, name);
			logger.info(name + " start");
		}

		@Override
		public void run() {
			try {
				work();
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}

			logger.info(this.getName() + " stop");
		}

		private void work() throws Exception {
			while (run) {
				TrackData data = (TrackData) CollectorConstant.DATA_QUEUE.poll();
				if (data != null) {
					dataList.add(data);
					String type = data.getOperationType();
					if (StringUtils.isNotBlank(type)
							&& CollectorConstant.TRANSFORM_TYPES.contains(type)
							&& StringUtils.isNotBlank(data.getOperationData())) {
						if (data.multiple()) {
							List<TrackData> transDataObjects = getTransDataObjects(data);
							transformDataList.addAll(transDataObjects);
						} else {
							data.analystTransformDatas();
							transformDataList.add(data);
						}
					}
					if (dataList.size() >= CollectorConstant.MANAGE_MESSAGE_SIZE) {
						save(dataList, transformDataList);

					}
				} else {
					if (dataList.size() > 0 || transformDataList.size() > 0) {
						save(dataList, transformDataList);
					}
					logger.info(this.getName() + " start sleep");
					Thread.sleep(30 * 1000);
					logger.info(this.getName() + " end sleep");
				}
			}
		}

		public void threadStop() {
			run = false;
		}

		private List<TrackData> getTransDataObjects(TrackData data) {
			List<TrackData> transDataObjects = new ArrayList<TrackData>();
			String transDatas = data.getOperationData();
			String[] transArray = transDatas.split("\\|\\|");
			for (String transData : transArray) {
				try {
					TrackData dataClone = (TrackData) BeanUtils.cloneBean(data);
					dataClone.setOperationData(transData);
					dataClone.analystTransformDatas();
					transDataObjects.add(dataClone);
				} catch (Exception e) {
					logger.error(e.getMessage(), e);
				}
			}
			return transDataObjects;
		}
	}

}
