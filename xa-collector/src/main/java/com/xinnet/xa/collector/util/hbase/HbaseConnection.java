package com.xinnet.xa.collector.util.hbase;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.HConnection;
import org.apache.hadoop.hbase.client.HConnectionManager;
import org.apache.hadoop.hbase.client.HTableInterface;
import org.apache.log4j.Logger;

import com.xinnet.xa.core.utils.properties.PropertitesFactory;

/**
 * 
 * 类名称：HbaseTool     
 * 创建时间：2013-5-10 下午06:28:00 
 * @update:[变更日期YYYY-MM-DD][更改人姓名][变更描述]
 */
public class HbaseConnection {
	
	private static  Logger logger = Logger.getLogger(HbaseConnection.class);
	
	private static HbaseConnection instance = null;
	private static Configuration config     = null; 
	private static HConnection  connection  = null; 
	
	public static HbaseConnection getInstance() {
		 if(instance == null){
			instance = new HbaseConnection(); 
		 }
		return instance;
	}
	
	public Configuration getConfiguration(){
		Configuration cfg = new Configuration();
		cfg.set("hbase.zookeeper.quorum", PropertitesFactory.getInstance().getPropertyByPathAndKey("/config.properties","HBase.ServerIP"));
		cfg.set("HBase.zookeeper.property.clientPort", PropertitesFactory.getInstance().getPropertyByPathAndKey("/config.properties","HBase.ServerPort"));
		return HBaseConfiguration.create(cfg);
	}
	
	/**
	 *  HbaseTool 实例 
	 */
	public HbaseConnection(){
		config = getConfiguration();
		try {
			connection = HConnectionManager.createConnection(config);
		} catch (IOException e) {
		    logger.info("连接hbase集群失败!\n"+e.getMessage());
		}
	}
	
	/**
	 * 功能描述：关闭
	 * @method:closeTable 
	 * @param table
	 * @since 2013-5-10 下午07:03:13
	 * @update:[变更日期YYYY-MM-DD][更改人姓名][变更描述]
	 */
    public void closeTable(HTableInterface table){
        try {
            if(table!=null) table.close();
        } 
        catch (IOException e) {
                e.printStackTrace();
        }

    }
	/**
	 * 功能描述： 返回表对象
	 * @method:getHTable 
	 * @param tableName
	 * @return:HTableInterface
	 * @throws IOException 
	 * @since 2013-5-10 下午07:31:19
	 * @update:[变更日期YYYY-MM-DD][更改人姓名][变更描述]
	 */
	public HTableInterface getHTable(String tableName) throws IOException{
		return connection.getTable(tableName);
	}
	
	/**
	 * 功能描述： 返回连接池对象
	 * @method:getPool 
	 * @return
	 * @return:HTablePool
	 * @since 2013-5-15 下午04:23:55
	 * @update:[变更日期YYYY-MM-DD][更改人姓名][变更描述]
	 */
    public HConnection getConnection() {
		return connection;
	}
}
