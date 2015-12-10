package com.xinnet.xa.collector.pagetrack.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.xinnet.xa.BaseSpringJunitUtil;

public class ManagerServiceTest extends BaseSpringJunitUtil {
	@Autowired
	ManagerCollectorService managerCollectorService;
	
	@Test
	public void testThreadGroup() throws InterruptedException{
		System.out.println(managerCollectorService.saveThreadCount());
		System.out.println(managerCollectorService.lessenSaveThread());
		Thread.sleep(1000*60);
		System.out.println(managerCollectorService.saveThreadCount());
	}

}
