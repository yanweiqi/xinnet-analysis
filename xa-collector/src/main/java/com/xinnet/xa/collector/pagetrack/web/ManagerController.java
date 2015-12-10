package com.xinnet.xa.collector.pagetrack.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xinnet.xa.collector.pagetrack.service.ManagerCollectorService;
import com.xinnet.xa.core.vo.CompMonitorData;

@Controller
public class ManagerController {
	@Autowired
	private ManagerCollectorService managerCollectorService;

	@RequestMapping("/stopJms.do")
	public @ResponseBody
	String stopJms() {
		managerCollectorService.stopJMSCollector();
		return "success";
	}
	
	@RequestMapping("/getSaveThreadCount.do")
	public @ResponseBody
	String getSaveThreadCount() {
		int count = managerCollectorService.saveThreadCount();
		return ""+count;
	}
	
	@RequestMapping("/addSaveThread.do")
	public @ResponseBody
	String addSaveThread() {
		int count = managerCollectorService.addSaveThread();
		return ""+count;
	}
	
	@RequestMapping("/delSaveThread.do")
	public @ResponseBody
	String delSaveThread() {
		int count = managerCollectorService.lessenSaveThread();
		return ""+count;
	}
	
	@RequestMapping("/monitor.do")
	public @ResponseBody CompMonitorData monitor(){
		return managerCollectorService.getmMonitorData();
	}

}
