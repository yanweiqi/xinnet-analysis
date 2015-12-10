package com.xinnet.xa.collector.general.service;

import static org.junit.Assert.fail;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.xinnet.xa.BaseSpringJunitUtil;
import com.xinnet.xa.collector.saletrack.service.impl.ManagerThreadGroupService;

public class ManagerThreadGroupServiceTest extends BaseSpringJunitUtil{

	@Autowired
	private ManagerThreadGroupService managerThreadGroupService;
		
	
	@Test
	public void testGetThreadActiveCount() {
		fail("Not yet implemented");
	}

	@Test
	public void testDecrementThreadWorker() {
		fail("Not yet implemented");
	}

	@Test
	public void testIncrementThreadWorker() {
		fail("Not yet implemented");
	}

	@Test
	public void testStartDoJob() {
		managerThreadGroupService.startDoJob();
		try {
			Thread.sleep(1000*60*30);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
