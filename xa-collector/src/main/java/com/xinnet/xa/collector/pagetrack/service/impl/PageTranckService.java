package com.xinnet.xa.collector.pagetrack.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.xinnet.xa.collector.general.service.IBusinessService;
import com.xinnet.xa.collector.pagetrack.dao.CollectorDao;
import com.xinnet.xa.collector.pagetrack.service.ManagerCollectorService;
import com.xinnet.xa.collector.util.CollectorConstant;
import com.xinnet.xa.core.Constant;
import com.xinnet.xa.core.service.ExceptionMessageService;
import com.xinnet.xa.core.vo.TrackData;

@Service
public class PageTranckService implements IBusinessService<TrackData> {
	private Logger logger = Logger.getLogger(PageTranckService.class);
	private Logger errorPageViewDataLog = Logger
			.getLogger("errorPageViewDataLog");
	private Logger errorTransfromDataLog = Logger
			.getLogger("errorTransfromDataDataLog");
	private List<TrackData> dataList = new ArrayList<TrackData>(
			CollectorConstant.MANAGE_MESSAGE_SIZE);
	private List<TrackData> transformDataList = new ArrayList<TrackData>(
			CollectorConstant.MANAGE_MESSAGE_SIZE);

	@Autowired
	private CollectorDao collectorDao;
	@Autowired
	private ExceptionMessageService exceptionMessageService;
	@Autowired
	private ManagerCollectorService managerCollectorService;

	@Override
	public void processingData(Set<TrackData> list) {
		for (TrackData data : list) {
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

			}
		}
		save(dataList, transformDataList);
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

	public void save(List<TrackData> dataList, List<TrackData> transformDataList) {
		if (!dataList.isEmpty()) {
			try {
				collectorDao.batchUpdate(dataList);
				logger.info("save pageview success");
			} catch (Exception e) {
				this.dbConnErrorProcess(e, dataList, true);
			}

			dataList.clear();

		}
		if (!transformDataList.isEmpty()) {
			try {
				collectorDao.batchInsterTransfrom(transformDataList);
				logger.info("save transfromdata success");
			} catch (Exception e) {
				this.dbConnErrorProcess(e, transformDataList, false);
			}
			transformDataList.clear();

		}

	}

	private void saveErrorDate(List<TrackData> dataList, boolean pageView) {
		for (TrackData data : dataList) {
			try {
				if (pageView) {
					collectorDao.insertPageView(data);
				} else {
					collectorDao.insterTransfrom(data);
				}

			} catch (Exception e) {
				writeData(data, pageView);
				logger.error(e.getMessage(), e);
			}
		}
	}

	private boolean isDBConnectionException(Exception e) {
		return e.getMessage().indexOf(Constant.DB_CONNECTION_EXCEPTION) >= 0;

	}

	private void dbConnErrorProcess(Exception e, List<TrackData> dataList,
			boolean pageView) {
		if (isDBConnectionException(e)) {
			managerCollectorService.stopJMSCollector();
			for (TrackData data : dataList) {
				this.writeData(data, pageView);

			}
			logger.error(e.getMessage(), e);
		} else {
			saveErrorDate(dataList, pageView);
		}
		exceptionMessageService.sendException(e);
	}

	private void writeData(TrackData data, boolean pageView) {
		if (pageView) {
			errorPageViewDataLog.info(JSON.toJSONString(data));
		} else {
			errorTransfromDataLog.info(JSON.toJSONString(data));
		}
	}

}
