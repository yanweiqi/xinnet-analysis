package com.xinnet.xa.core.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xinnet.xa.core.service.MonitorTomcatService;
import com.xinnet.xa.core.vo.TomcatMonitorData;

@Controller
public class TomcatMonitorController {
	@Autowired
	private MonitorTomcatService monitorTomcatService;

	@RequestMapping("/tomcatMonitor.do")
	public @ResponseBody TomcatMonitorData monitor() {
		TomcatMonitorData data = monitorTomcatService.getTomcatData();
		return data;
	}

}
