package com.xinnet.xa.controller.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xinnet.xa.core.service.ISetUpConstantService;

@Service
public class SetUpConstantService implements ISetUpConstantService {
	@Autowired
	private MonitorService monitorService;

	@Override
	public void setConstanst(String result) {
		monitorService.startMonitor();

	}

}
