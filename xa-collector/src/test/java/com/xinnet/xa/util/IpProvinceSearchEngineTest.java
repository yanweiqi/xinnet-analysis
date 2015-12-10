package com.xinnet.xa.util;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.xinnet.xa.collector.util.IpProvinceSearchEngine;

@RunWith(SpringJUnit4ClassRunner.class)  
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public class IpProvinceSearchEngineTest {
	
	@Autowired
	private IpProvinceSearchEngine ipProvinceSearchEngine;
	
	@Test
	public void getProvince(){
		String city = ipProvinceSearchEngine.getProvince("10.10.195.245");
		System.out.println(city);
		
	}

}
