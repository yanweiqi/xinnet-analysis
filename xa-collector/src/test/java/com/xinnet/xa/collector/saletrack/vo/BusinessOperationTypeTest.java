package com.xinnet.xa.collector.saletrack.vo;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;
import org.junit.Test;

public class BusinessOperationTypeTest {

	private final static Logger logger = Logger.getLogger(BusinessOperationTypeTest.class);
	@Test
	public void testGenerateShopCartOrderCode() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyMMddHHmmss");
		String datetime = sdf.format(new Date()); 
		int init = 100000;
		String[] hycodes  = new String[200];
		for (int i = 0; i < 200; i++) {
			hycodes[i] = "hy"+(init+i);
			Math.abs(hycodes[i].hashCode()); 
			logger.info(datetime+Math.abs(hycodes[i].hashCode()));
		}
		
		
	}

}
