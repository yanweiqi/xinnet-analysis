package com.xinnet.xa.collector.pagetrack.dao.hbase;

import java.io.IOException;

import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.MasterNotRunningException;
import org.apache.hadoop.hbase.ZooKeeperConnectionException;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.hbase.client.HTableInterface;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.log4j.Logger;

import com.xinnet.xa.collector.util.hbase.HbaseTool;

public class HbaseDAO {

  private static final Logger logger = Logger.getLogger(HbaseDAO.class);
   
  /**
   * 功能描述：创建一张表
   * @param tableName
   * @param columnFamily
   * @throws Exception
   * @author 闫伟旗[yanweiqi@xinnet.com]
   * @since 2014-6-17
   * @update:[变更日期YYYY-MM-DD][更改人姓名][变更描述]
   */
  public void createTable(String tableName,String columnFamily) throws Exception {
      HBaseAdmin admin = new HBaseAdmin(HbaseTool.getInstance().getConfiguration());
      if (admin.tableExists(tableName)) {
          logger.info("table [" + tableName + "] Exists!!!");
      }
      else{
          HTableDescriptor tableDesc = new HTableDescriptor(tableName);
          HColumnDescriptor hColumnDescriptor = new HColumnDescriptor(columnFamily);
          hColumnDescriptor.setMaxVersions(1);
          tableDesc.addFamily(hColumnDescriptor);
          admin.createTable(tableDesc);
          logger.info("create table ["+tableName+"] ok");
      }
  }

  /**
   * 功能描述：删除表
   * @author 闫伟旗[yanweiqi@xinnet.com]
 * @throws IOException 
   * @since 2014-6-17
   * @update:[变更日期YYYY-MM-DD][更改人姓名][变更描述]
   */
  public void delTable(String tableName) throws IOException{
	  try {
		HBaseAdmin admin = new HBaseAdmin(HbaseTool.getInstance().getConfiguration());
		if(admin.tableExists(tableName)){
		   admin.disableTable(tableName);
		   admin.deleteTable(tableName);
		}
	} catch (MasterNotRunningException e) {
		e.printStackTrace();
	} catch (ZooKeeperConnectionException e) {
		e.printStackTrace();
	}
  }
  
  /**
   * 功能描述：获取表对象
   * @param  String tableName
   * @return HTableInterface 
   * @author 闫伟旗[yanweiqi@xinnet.com]
   * @since 2014-6-17
   * @update:[变更日期YYYY-MM-DD][更改人姓名][变更描述]
   */
  public HTableInterface getHTable(String tableName){
	  HTableInterface table = HbaseTool.getInstance().getPool().getTable(tableName);
      return table;
  }
  
  /**
   * 功能描述：
   * @param tableName
   * @param row
   * @return Result 
   * @author 闫伟旗[yanweiqi@xinnet.com]
   * @since 2014-6-17
   * @update:[变更日期YYYY-MM-DD][更改人姓名][变更描述]
   */
  public Result getResult(String tableName,String row){
      Result result = null;
      HTableInterface table = this.getHTable(tableName);
      Get get = new Get(Bytes.toBytes(row));
      try {
          result = table.get(get);
      }
      catch (IOException e) {
          e.printStackTrace();
      }
      return result;
  }
  
}
