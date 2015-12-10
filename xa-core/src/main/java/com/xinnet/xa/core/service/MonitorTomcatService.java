package com.xinnet.xa.core.service;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.xinnet.xa.core.utils.TomcatUtil;
import com.xinnet.xa.core.vo.TomcatMonitorData;

@Service
public class MonitorTomcatService {
	private Logger logger = Logger.getLogger(MonitorTomcatService.class);

	public TomcatMonitorData getTomcatData() {
		TomcatMonitorData data = new TomcatMonitorData();
		try {
			TomcatUtil.MonitorRunTime(data);
			TomcatUtil.MonitorThreadPool(data);
			TomcatUtil.MonitorMemory(data, TomcatUtil.TOMCAT_MEMORY_OBJECTNAME,
					TomcatUtil.MEM_HEAP_ATTR);
			TomcatUtil.MonitorMemory(data, TomcatUtil.TOMCAT_MEMORY_OBJECTNAME,
					TomcatUtil.MEM_NONHEAP_ATTR);
//			TomcatUtil.MonitorMemory(data,
//					TomcatUtil.TOMCAT_MEMORY_POOL_OBJECTNAME,
//					TomcatUtil.MEM_PERM_GEN);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		logger.info(data);
		return data;
	}

}
