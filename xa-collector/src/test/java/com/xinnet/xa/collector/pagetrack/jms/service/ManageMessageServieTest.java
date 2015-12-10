package com.xinnet.xa.collector.pagetrack.jms.service;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.listener.DefaultMessageListenerContainer;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)  
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public class ManageMessageServieTest {
 
	@Autowired
	DefaultMessageListenerContainer advancedQueueContainer;
	 
	@Test
	public void testStartWorker() throws InterruptedException {
		 Thread.sleep(1000*60);
		 advancedQueueContainer.stop();
		 System.out.println("stop");
	     Thread.sleep(1000*60*2);
	     advancedQueueContainer.start();
	     System.out.println("start");
	     Thread.sleep(1000*60*20);
	}

}
