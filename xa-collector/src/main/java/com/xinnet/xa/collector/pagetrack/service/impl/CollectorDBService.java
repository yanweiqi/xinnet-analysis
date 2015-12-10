package com.xinnet.xa.collector.pagetrack.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.xinnet.xa.collector.pagetrack.dao.CollectorDao;
import com.xinnet.xa.collector.pagetrack.service.ManagerCollectorService;
import com.xinnet.xa.core.Constant;
import com.xinnet.xa.core.service.ExceptionMessageService;
import com.xinnet.xa.core.vo.TrackData;

@Service("collectorService")
public class CollectorDBService extends AbstractCollectorService {

	private Logger errorPageViewDataLog = Logger
			.getLogger("errorPageViewDataLog");
	private Logger errorTransfromDataLog = Logger
			.getLogger("errorTransfromDataDataLog");
	@Autowired
	private CollectorDao collectorDao;
	@Autowired
	private ExceptionMessageService exceptionMessageService;
	@Autowired
	private ManagerCollectorService managerCollectorService;

	public CollectorDao getCollectorDao() {
		return collectorDao;
	}

	public void setCollectorDao(CollectorDao collectorDao) {
		this.collectorDao = collectorDao;
	}

	@Override
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
