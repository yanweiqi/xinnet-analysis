package com.xinnet.xa.controller.common;

import java.util.concurrent.ConcurrentSkipListSet;

import org.apache.activemq.broker.jmx.QueueViewMBean;

import com.xinnet.xa.controller.model.IpAndPortTransferId;
import com.xinnet.xa.core.Constant;
import com.xinnet.xa.core.utils.properties.PropertitesFactory;

public class ControllerConstant {
	public final static ConcurrentSkipListSet<QueueViewMBean> QUEUE_VIEW_MEANS = new ConcurrentSkipListSet<QueueViewMBean>();
	public final static IpAndPortTransferId DEFAULT_ID=  new IpAndPortTransferId();
	static{
		DEFAULT_ID.setIp("default");
		DEFAULT_ID.setPort(0);
	}
	public final static String MONITOR_TOMCAT_URL="/tomcatMonitor.do";
	public final static String MONITOR_COMP_URL="/monitor.do";
	public final static String MAIL_ERROR_SUBJECT="XA系统异常";
	public static final String MAIL_TO=PropertitesFactory.getInstance().getPropertyByPathAndKey(Constant.PROPERTITIES_PATH,"mailTo");
	public static enum ComponentState{
		NORMAL(1,"normal"),ERROR(2,"error");
		private int value;
		private String description;
		ComponentState(int value,String description){
			this.value=value;
			this.description=description;
		}
		public int getValue() {
			return value;
		}
		public void setValue(int value) {
			this.value = value;
		}
		public String getDescription() {
			return description;
		}
		public void setDescription(String description) {
			this.description = description;
		}
		
	}
}
