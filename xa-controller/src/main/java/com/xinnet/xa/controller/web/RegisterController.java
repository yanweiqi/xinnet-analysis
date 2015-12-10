package com.xinnet.xa.controller.web;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.xinnet.xa.controller.model.CompConstants;
import com.xinnet.xa.controller.service.ControllerService;
import com.xinnet.xa.controller.service.MonitorService;
import com.xinnet.xa.core.vo.CompConstantData;
import com.xinnet.xa.core.vo.CompData;

@Controller
public class RegisterController {
	private Logger logger = Logger.getLogger(RegisterController.class);
	@Autowired
	private ControllerService controllerService;
	@Autowired
	private MonitorService monitorService;

	@RequestMapping("register.do")
	public @ResponseBody
	CompConstantData register(String compData) {
		logger.info(compData);
		CompConstantData constantData = new CompConstantData();
		if (StringUtils.isNotBlank(compData)) {
			try {
				CompData data = JSON.parseObject(compData, CompData.class);
				controllerService.saveComponent(data);
				CompConstants constant = controllerService.getConstants(data);
				BeanUtils.copyProperties(constantData, constant);
				
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
		}
		logger.info(constantData);
		return constantData;
	}
	
	@RequestMapping("/sendError.do")
	public @ResponseBody String sendError(String ip,int port,String compType,String message,String stacks){
		monitorService.saveErrorMessage(ip, port, compType, message, stacks);
		return "success send mail";
	}

}
