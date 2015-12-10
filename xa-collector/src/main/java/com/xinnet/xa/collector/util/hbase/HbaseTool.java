package com.xinnet.xa.collector.util.hbase;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.HTableInterface;
import org.apache.hadoop.hbase.client.HTablePool;

import com.xinnet.xa.core.utils.properties.PropertitesFactory;

/**
 * 
 * 类名称：HbaseTool     
 * 创建时间：2013-5-10 下午06:28:00 
 * @update:[变更日期YYYY-MM-DD][更改人姓名][变更描述]
 */
public class HbaseTool {
	
	private static HbaseTool instance = null;
	private static Configuration config = null; 
	private static HTablePool pool = null; 

	private static  int Pool_Size = 10;
	
	public static HbaseTool getInstance() {
		 if(instance == null){
			instance = new HbaseTool(); 
		 }
		return instance;
	}
	
	public Configuration getConfiguration(){
		Configuration cfg = new Configuration();
		cfg.set("hbase.zookeeper.quorum", PropertitesFactory.getInstance().getPropertyByPathAndKey("/config.properties","HBase.ServerIP"));
		cfg.set("HBase.zookeeper.property.clientPort", PropertitesFactory.getInstance().getPropertyByPathAndKey("/config.properties","HBase.ServerPort"));
		cfg.set("zookeeper.znode.parent", "/hbase-unsecure");
		return HBaseConfiguration.create(cfg);
	}
	
	/**
	 *  HbaseTool 实例 
	 */
	public HbaseTool(){
		config = HBaseConfiguration.create();
		pool = new HTablePool(config,Pool_Size);//设定表的池大小		
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
	 * @since 2013-5-10 下午07:31:19
	 * @update:[变更日期YYYY-MM-DD][更改人姓名][变更描述]
	 */
	public HTableInterface getHTable(String tableName){
		return pool.getTable(tableName);
	}
	
	/**
	 * 功能描述： 返回连接池对象
	 * @method:getPool 
	 * @return
	 * @return:HTablePool
	 * @since 2013-5-15 下午04:23:55
	 * @update:[变更日期YYYY-MM-DD][更改人姓名][变更描述]
	 */
    public HTablePool getPool() {
		return pool;
	}
}
