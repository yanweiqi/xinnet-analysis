package com.xinnet.xa.collector.general.service;

import org.apache.log4j.Logger;


/**
 * 功能描述：执行【某某】任务的工作线程
 */
public abstract class ThreadWorker extends Thread{
	
	Logger logger = Logger.getLogger(getClass());
   
	abstract void doWork();
	 
	public ThreadWorker(ThreadGroup g, String name) {
		super(g, name);
		logger.info(name + " start");
	}
}
