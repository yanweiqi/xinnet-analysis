package com.xinnet.xa.controller.service;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.xinnet.xa.controller.common.ControllerConstant;
import com.xinnet.xa.controller.dao.CompExceptionMessageDao;
import com.xinnet.xa.controller.dao.CompMonitorDao;
import com.xinnet.xa.controller.dao.ComponentDao;
import com.xinnet.xa.controller.dao.RuleDao;
import com.xinnet.xa.controller.dao.TomcatMonitorDao;
import com.xinnet.xa.controller.model.CompExceptionMessage;
import com.xinnet.xa.controller.model.CompMonitor;
import com.xinnet.xa.controller.model.Component;
import com.xinnet.xa.controller.model.Rule;
import com.xinnet.xa.controller.model.TomcatMonitor;
import com.xinnet.xa.core.utils.DateUtil;
import com.xinnet.xa.core.utils.HttpClientUtil;
import com.xinnet.xa.core.utils.SendMailUtil;
import com.xinnet.xa.core.utils.Utils;
import com.xinnet.xa.core.vo.CompMonitorData;
import com.xinnet.xa.core.vo.MailParams;
import com.xinnet.xa.core.vo.TomcatMonitorData;

@Service
public class MonitorService implements InitializingBean{
	private Logger logger = Logger.getLogger(MonitorService.class);
	@Autowired
	private ComponentDao componentDao;
	@Autowired
	private RuleDao ruleDao;
	@Autowired
	private SendMailUtil sendMailUtil;
	@Autowired
	private TomcatMonitorDao tomcatMonitorDao;
	@Autowired
	private CompMonitorDao compMonitorDao;
	@Autowired
	private CompExceptionMessageDao compExceptionMessageDao;

	public void startMonitor() {
		new MonitorTomcatThread().start();
	}

	public void sendMonitorUrl(Component component, String subUrl) {
		String url = component.createCompUrl() + subUrl;
		String result = "";
		try {
			result = HttpClientUtil.get(url, "utf-8", null);
			logger.info(result);
			analyResult(result, component, subUrl);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			setCompErrorState(component);
			sendErrorMessageMail(component.getId().getIp(), component.getId()
					.getPort(), component.getType(), e.getMessage(),
					Utils.getExceptionStacks(e));
		}

	}

	public void analyResult(String result, Component component, String subUrl)
			throws IllegalAccessException, InvocationTargetException {
		if (StringUtils.isNotBlank(result)) {
			if (subUrl.equals(ControllerConstant.MONITOR_TOMCAT_URL)) {
				analyTomcatResult(result, component);
			}
			if (subUrl.equals(ControllerConstant.MONITOR_COMP_URL)) {
				analyCompResult(result, component);
			}
		}
	}

	public void analyTomcatResult(String result, Component component)
			throws IllegalAccessException, InvocationTargetException {
		TomcatMonitorData data = JSON.parseObject(result,
				TomcatMonitorData.class);
		TomcatMonitor tomcatMonitor = new TomcatMonitor();
		BeanUtils.copyProperties(tomcatMonitor, data);
		tomcatMonitor.setIp(component.getId().getIp());
		tomcatMonitor.setPort(component.getId().getPort());
		tomcatMonitorDao.save(tomcatMonitor);
	}

	public void analyCompResult(String result, Component component)
			throws IllegalAccessException, InvocationTargetException {
		CompMonitorData data = JSON.parseObject(result, CompMonitorData.class);
		CompMonitor cm = new CompMonitor();
		BeanUtils.copyProperties(cm, data);
		cm.setIp(component.getId().getIp());
		cm.setPort(component.getId().getPort());
		compMonitorDao.save(cm);
	}

	public void setCompErrorState(Component component) {
		component.setState(ControllerConstant.ComponentState.ERROR.getValue());
		componentDao.save(component);
	}

	public void saveErrorMessage(String ip, int port, String compType,
			String message, String stacks) {
		CompExceptionMessage compExceptionMessage = new CompExceptionMessage();
		compExceptionMessage.setIp(ip);
		compExceptionMessage.setPort(port);
		compExceptionMessage.setCompType(compType);
		compExceptionMessage.setMessage(message);
		compExceptionMessageDao.save(compExceptionMessage);
		sendErrorMessageMail(ip, port, compType, message, stacks);
	}

	public void sendErrorMessageMail(String ip, int port, String compType,
			String message, String stacks) {
		MailParams mailParams = new MailParams();
		mailParams.setSubject(ControllerConstant.MAIL_ERROR_SUBJECT);
		Map<String, String> params = new HashMap<String, String>();
		params.put("ip", ip);
		params.put("port", String.valueOf(port));
		params.put("type", compType);
		params.put("errorMessage", message);
		params.put("errorStack", stacks);
		String text = sendMailUtil.getHtmlTextFromFreeMarker("errorMail.ftl",
				params);
		mailParams.setBody(text);
		mailParams.setHtml(true);
		mailParams.setTo(ControllerConstant.MAIL_TO);
		try {
			sendMailUtil.sendMail(mailParams);
		} catch (Exception t) {
			logger.error(t.getMessage(), t);
		}
	}

	class MonitorTomcatThread extends Thread {

		@Override
		public void run() {
			while (true) {
				monitor();
			}
		}

		private void monitor() {

			Rule monitorRule = ruleDao.getRuleByName("monitorTime");
			int time = Integer.valueOf(monitorRule.getRuleValue());
			try {
				Thread.sleep(time * DateUtil.MILLIS_PER_MINUTE);
			} catch (InterruptedException e) {
				logger.error(e.getMessage(), e);
			}
			List<Component> components = componentDao
					.getComponentsByState(ControllerConstant.ComponentState.NORMAL
							.getValue());
			for (Component component : components) {
				sendMonitorUrl(component, ControllerConstant.MONITOR_TOMCAT_URL);
				sendMonitorUrl(component, ControllerConstant.MONITOR_COMP_URL);
			}

		}

	}

	@Override
	public void afterPropertiesSet() throws Exception {
		startMonitor();
		
	}

}
