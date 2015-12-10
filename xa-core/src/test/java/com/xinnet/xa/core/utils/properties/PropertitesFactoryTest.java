package com.xinnet.xa.core.utils.properties;

import org.apache.log4j.Logger;
import org.junit.Test;

public class PropertitesFactoryTest {

	private static final Logger logger = Logger.getLogger(PropertitesFactoryTest.class);
	
	private String path="/config.properties";
	
	@Test
	public void testGetPropertyByPathAndKey() {
		logger.info( PropertitesFactory.getInstance().getPropertyByPathAndKey(path, "HBase.ServerPort") );
	}

}
