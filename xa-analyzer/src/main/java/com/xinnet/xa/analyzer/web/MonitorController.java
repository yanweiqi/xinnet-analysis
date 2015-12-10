package com.xinnet.xa.analyzer.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xinnet.xa.core.Constant;
import com.xinnet.xa.core.vo.CompMonitorData;

@Controller
public class MonitorController {
	
	@RequestMapping("/monitor.do")
	public @ResponseBody CompMonitorData monitor(){
		CompMonitorData data = new CompMonitorData();
		data.setState(Constant.COMP_STATE_OK);
		return data;
		
	}
	

}
