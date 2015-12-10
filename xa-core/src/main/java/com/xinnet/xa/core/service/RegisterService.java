package com.xinnet.xa.core.service;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.xinnet.xa.core.Constant;
import com.xinnet.xa.core.utils.HttpClientUtil;
import com.xinnet.xa.core.utils.TomcatUtil;
import com.xinnet.xa.core.vo.CompData;

/**
 * 功能描述：实现初始化注册到控制器
 */
@Service
public class RegisterService implements InitializingBean {
	private Logger logger = Logger.getLogger(RegisterService.class);
	
	@Autowired
	ISetUpConstantService setUpConstantService;
	
	@Autowired
	MonitorTomcatService monitorTomcatService;

	@Override
	public void afterPropertiesSet() throws Exception {
		if (Constant.IS_REGISTER) registerCLCT();
		logger.info("register CLCT success");
	}

	public void registerCLCT() throws Exception {
		String result = "";
		if (StringUtils.isNotBlank(Constant.CLCT_URL)) {
			CompData data = new CompData();
			data.setIp(TomcatUtil.getLocalIp());
			data.setPort(Integer.valueOf(TomcatUtil.getPort()));
			data.setType(Constant.COMP_TYPE);
			String jsonData = JSON.toJSONString(data);
			logger.info(jsonData);
			// monitorTomcatService.getTomcatData();
			Map<String, String> params = new HashMap<String, String>();
			params.put("compData", jsonData);
			result = HttpClientUtil.post(Constant.CLCT_URL, params, "utf-8");
		}
		setUpConstantService.setConstanst(result);
	}

}
