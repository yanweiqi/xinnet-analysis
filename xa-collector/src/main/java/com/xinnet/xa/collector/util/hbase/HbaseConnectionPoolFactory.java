package com.xinnet.xa.collector.util.hbase;

import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.HConnection;
import org.apache.hadoop.hbase.client.HConnectionManager;

import com.xinnet.xa.core.utils.properties.PropertitesFactory;

public class HbaseConnectionPoolFactory extends BasePooledObjectFactory<HConnection> {
	public final static Configuration hbaseConfig;
	static{
		Configuration cfg = new Configuration();
		cfg.set("hbase.zookeeper.quorum", PropertitesFactory.getInstance().getPropertyByPathAndKey("/config.properties","HBase.ServerIP"));
		cfg.set("HBase.zookeeper.property.clientPort", PropertitesFactory.getInstance().getPropertyByPathAndKey("/config.properties","HBase.ServerPort"));
		hbaseConfig= HBaseConfiguration.create(cfg);
	}
	@Override
	public HConnection create() throws Exception {
		 
		return HConnectionManager.createConnection(hbaseConfig);
	}

	@Override
	public PooledObject<HConnection> wrap(HConnection obj) {
		return new DefaultPooledObject<HConnection>(obj);
	}

	 
	
	

}
