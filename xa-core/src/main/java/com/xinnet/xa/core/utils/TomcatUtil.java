package com.xinnet.xa.core.utils;

import java.io.IOException;
import java.lang.management.MemoryUsage;
import java.lang.management.RuntimeMXBean;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.management.AttributeNotFoundException;
import javax.management.DynamicMBean;
import javax.management.InstanceNotFoundException;
import javax.management.MBeanException;
import javax.management.MBeanServer;
import javax.management.MBeanServerFactory;
import javax.management.MBeanServerInvocationHandler;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectInstance;
import javax.management.ObjectName;
import javax.management.ReflectionException;
import javax.management.openmbean.CompositeDataSupport;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.xinnet.xa.core.Constant;
import com.xinnet.xa.core.vo.TomcatMonitorData;

public class TomcatUtil {
	private static Logger logger = Logger.getLogger(TomcatUtil.class);
	public final static String TOMCAT_RUNTIME_OBJECTNAME = "java.lang:type=Runtime";
	public final static String TOMCAT_MEMORY_OBJECTNAME = "java.lang:type=Memory";
	public final static String TOMCAT_MEMORY_POOL_OBJECTNAME = "java.lang:type=MemoryPool,name=Perm Gen";
	public final static String MEM_HEAP_ATTR = "HeapMemoryUsage";
	public final static String MEM_NONHEAP_ATTR = "NonHeapMemoryUsage";
	public final static String MEM_PERM_GEN = "Usage";
	public static MBeanServer mBeanServer = null;
	static {
		ArrayList<MBeanServer> mBeanServers = MBeanServerFactory
				.findMBeanServer(null);
		if (CollectionUtils.isNotEmpty(mBeanServers)) {
			mBeanServer = mBeanServers.get(0);
		}
	}

	public static String getPort() throws Exception {
		String port = "";
		Set<ObjectInstance> names = mBeanServer.queryMBeans(null, null);
		if (CollectionUtils.isNotEmpty(names)) {
			for (ObjectInstance oi : names) {
				String name = oi.getObjectName().toString();
				int index = name.indexOf(Constant.PORT_OBJECT_NAME);
				if (index > 0) {
					port = name.substring(
							index + Constant.PORT_OBJECT_NAME.length(),
							name.length() - 1);
					break;
				}
			}
		}
        if(StringUtils.isBlank(port)) port = "8080";
		Constant.PORT = Integer.valueOf(port);

		return port;
	}

	public static void MonitorThreadPool(TomcatMonitorData data)
			throws MalformedObjectNameException, NullPointerException,
			AttributeNotFoundException, MBeanException, ReflectionException {
		String threadPoolObjectName = "Catalina:type=ThreadPool,name=\"http-bio-"
				+ Constant.PORT + "\"";
		DynamicMBean threadPoolMxBean = MBeanServerInvocationHandler
				.newProxyInstance(mBeanServer, new ObjectName(
						threadPoolObjectName), DynamicMBean.class, true);
		data.setMaxThreads((Integer) threadPoolMxBean
				.getAttribute("maxThreads"));
		data.setCurrentThreadCount((Integer) threadPoolMxBean
				.getAttribute("currentThreadCount"));
		data.setCurrentThreadsBusy((Integer) threadPoolMxBean
				.getAttribute("currentThreadsBusy"));
	}

	public static void MonitorRunTime(TomcatMonitorData data) throws Exception {
		RuntimeMXBean runtimeMxBean = MBeanServerInvocationHandler
				.newProxyInstance(mBeanServer, new ObjectName(
						TOMCAT_RUNTIME_OBJECTNAME), RuntimeMXBean.class, true);
		data.setStartTime(DateUtil.formatDateTime(new Date(runtimeMxBean
				.getStartTime())));
		data.setContinuousWorkingTime(runtimeMxBean.getUptime());
	}

	public static void MonitorMemory(TomcatMonitorData data, String objectName,
			String attr) throws AttributeNotFoundException,
			InstanceNotFoundException, MalformedObjectNameException,
			MBeanException, ReflectionException, NullPointerException,
			IOException {
		MemoryUsage memoryUsage = MemoryUsage
				.from((CompositeDataSupport) mBeanServer.getAttribute(
						new ObjectName(objectName), attr));
		long max = memoryUsage.getMax();
		long committed = memoryUsage.getCommitted();
		long used = memoryUsage.getUsed();
		double usage = (double) used / committed;
		if (attr.equals(MEM_HEAP_ATTR)) {
			data.setMaxHeap(max);
			data.setCurrentAssignmentHeap(committed);
			data.setUsedHeap(used);
			data.setHeapUsage(usage);
		} else if (attr.equals(MEM_NONHEAP_ATTR)) {
			data.setMaxNonHeap(max);
			data.setCurrentAssignmentNonHeap(committed);
			data.setUsedNonHeap(used);
			data.setNonheapUsage(usage);
		} else if (attr.equals(MEM_PERM_GEN)) {
			data.setPermGen(max);
			data.setCurrentAssignmentPermGen(committed);
			data.setUsedPermGen(used);
			data.setPermGenUsage(usage);
		}
	}

	public static String getLocalIp() {
		String ip = "unknow";
		try {
			ip = isWindowsOS() ? getWindowsLocalIp() : getLinuxLocalIp();
			Constant.IP = ip;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return ip;
	}

	private static String getLinuxLocalIp() throws Exception {
		Enumeration<NetworkInterface> allNetInterfaces = NetworkInterface
				.getNetworkInterfaces();
		InetAddress ip = null;
		boolean run = true;
		while (allNetInterfaces.hasMoreElements()&& run) {
			NetworkInterface netInterface = allNetInterfaces.nextElement();
			logger.info(netInterface.getName());
			Enumeration<InetAddress> addresses = netInterface
					.getInetAddresses();
			while (addresses.hasMoreElements()) {
				ip = addresses.nextElement();
				if (ip != null && ip instanceof Inet4Address && isTnner(ip.getHostAddress())) {
					logger.info("本机的IP = " + ip.getHostAddress());
					run = false;
					break;
				}
			}
		}
		return ip.getHostAddress();
	}

	private static boolean isTnner(String ip) {
		String reg = "(10|172|192)\\.([0-1][0-9]{0,2}|[2][0-5]{0,2}|[3-9][0-9]{0,1})\\.([0-1][0-9]{0,2}|[2][0-5]{0,2}|[3-9][0-9]{0,1})\\.([0-1][0-9]{0,2}|[2][0-5]{0,2}|[3-9][0-9]{0,1})";
		Pattern p = Pattern.compile(reg);
		Matcher matcher = p.matcher(ip);
		return matcher.find();
	}

	private static String getWindowsLocalIp() throws Exception {
		InetAddress ip = InetAddress.getLocalHost();
		return ip.getHostAddress();
	}

	private static boolean isWindowsOS() {
		boolean isWindowsOS = false;
		String osName = System.getProperty("os.name");
		if (osName.toLowerCase().indexOf("windows") > -1) {
			isWindowsOS = true;
		}
		return isWindowsOS;
	}
}
