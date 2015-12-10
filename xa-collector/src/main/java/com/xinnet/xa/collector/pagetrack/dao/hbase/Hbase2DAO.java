package com.xinnet.xa.collector.pagetrack.dao.hbase;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.MasterNotRunningException;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.ZooKeeperConnectionException;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.hbase.client.HConnection;
import org.apache.hadoop.hbase.client.HConnectionManager;
import org.apache.hadoop.hbase.client.HTableInterface;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.log4j.Logger;

import com.xinnet.xa.collector.util.hbase.HbaseConnection;
import com.xinnet.xa.collector.util.hbase.RowData;
import com.xinnet.xa.core.Constant;

public class Hbase2DAO {

	private static final Logger logger = Logger.getLogger(Hbase2DAO.class);
//	private static GenericObjectPool<HConnection> connectionPool = new GenericObjectPool<HConnection>(new HbaseConnectionPoolFactory());
//	public static HTablePool pool = new HTablePool(HbaseConnectionPoolFactory.hbaseConfig, 10);
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
		HBaseAdmin admin = new HBaseAdmin(HbaseConnection.getInstance().getConnection());
		if (admin.tableExists(tableName)) {
			logger.info("table [" + tableName + "] Exists!!!");
		}
		else{
			HTableDescriptor tableDesc = new HTableDescriptor(TableName.valueOf(tableName));
			HColumnDescriptor hColumnDescriptor = new HColumnDescriptor(columnFamily);
			hColumnDescriptor.setMaxVersions(1);
			tableDesc.addFamily(hColumnDescriptor);
			admin.createTable(tableDesc);
			logger.info("create table ["+tableName+"] ok");
	    }
		HbaseConnection.getInstance().getConnection().close();
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
			HBaseAdmin admin = new HBaseAdmin(HbaseConnection.getInstance().getConnection());
			if(admin.tableExists(tableName)){
				admin.disableTable(tableName);
				admin.deleteTable(tableName);
			}
		} catch (MasterNotRunningException e) {
			e.printStackTrace();
		} catch (ZooKeeperConnectionException e) {
			e.printStackTrace();
		}
		finally{
			HbaseConnection.getInstance().getConnection().close();
		}
	}
	
	/**
	 * 功能描述：单条数据存入
	 * @param tableName
	 * @param row
	 * @param columnFamily
	 * @param column
	 * @param data
	 * @throws IOException
	 * @author 闫伟旗[yanweiqi@xinnet.com]
	 * @since 2014-6-18
	 * @update:[变更日期YYYY-MM-DD][更改人姓名][变更描述]
	 */
	public   void save(String tableName,String row,String columnFamily,String column,String data) throws Exception{
		HTableInterface table = null;
		HConnection hc = null;
		try{
			 
			table = HbaseConnection.getInstance().getConnection().getTable(tableName);
			//c = HConnectionManager.createConnection(Constant.hbaseConfig);
			//table = pool.getTable(tableName);
		     
			Put put = new Put(Bytes.toBytes(row));
			put.add(Bytes.toBytes(columnFamily), Bytes.toBytes(column), Bytes.toBytes(data));
			table.put(put);
		}
		catch(Exception e){
			throw e;
		}
		finally{
			//pool.putTable(table);
			HbaseConnection.getInstance().getConnection().close();
		}
	}
  
	/**
	 * 功能描述：批量存入
	 * @param tableName
	 * @param Map 
	 * @param Class 
	 * @throws IOException
	 * @author 闫伟旗[yanweiqi@xinnet.com]
	 * @since 2014-6-18
	 * @update:[变更日期YYYY-MM-DD][更改人姓名][变更描述]
	 */
	public void save(String tableName,Map<String,RowData> map,Class<?> clazz) throws IOException{
		HTableInterface table = null;
		try{
			table = HbaseConnection.getInstance().getConnection().getTable(tableName);
			//table = pool.getTable(tableName);
			List<Put> list = new ArrayList<Put>();
			for (Entry<String, RowData> entry : map.entrySet()) {
				Put p = new Put(Bytes.toBytes(entry.getKey()));
				RowData rd = entry.getValue();
				Map<String,Object> m = rd.getColumn();
				Field[] fields = clazz.getDeclaredFields();
				for(Field f:fields){
					String field = f.getName();
					String v = StringUtils.isBlank(m.get(field).toString())?"": m.get(field).toString();
					p.add(Bytes.toBytes(rd.getColumnFamily()),Bytes.toBytes(field),Bytes.toBytes(v));
				}
			    list.add(p);
			}
			table.put(list);
		}
		catch(IOException e){
			throw e;
		}
		finally{
			if(table != null) table.close();
			HbaseConnection.getInstance().getConnection().close();	
			//pool.putTable(table);
		}
	}
    
	/**
	 * 功能描述：根据rowKey获取Result结果集
	 * @param tableName
	 * @param row
	 * @return Result 
	 * @author 闫伟旗[yanweiqi@xinnet.com]
	 * @throws IOException 
	 * @since 2014-6-17
	 * @update:[变更日期YYYY-MM-DD][更改人姓名][变更描述]
	 */
	public Result getResult(HTableInterface table,String tableName,String rowKey) throws IOException{
		Result result = null;
		Get get = new Get(Bytes.toBytes(rowKey));
		try {
			result = table.get(get);
		}
		catch (IOException e) {
			throw e; 
		}
		return result;
	}
	
    /**
     * 功能描述：跟RowKey获取列
     * @param tableName
     * @param columnFamily
     * @param row
     * @return Set
     * @author 闫伟旗[yanweiqi@xinnet.com]
     * @throws IOException 
     * @since 2014-6-19
     * @update:[变更日期YYYY-MM-DD][更改人姓名][变更描述]
     */
    public Map<String,Object> getColumnByRowKey(final String tableName,final String columnFamily,final String rowKey) throws IOException{
        HTableInterface table = null;
        Map<String,Object> map = new HashMap<String,Object>();
        try {
        	table = HbaseConnection.getInstance().getConnection().getTable(tableName);
            Result result = getResult(table,tableName, rowKey);
            Map<byte[], byte[]> familyMap = result.getFamilyMap(Bytes.toBytes(columnFamily));
            Set<Map.Entry<byte[], byte[]>> fSet = familyMap.entrySet();
            Iterator<Map.Entry<byte[], byte[]>> iterator = fSet.iterator();
            while (iterator.hasNext()) {
                Map.Entry<byte[], byte[]> wordRow = (Map.Entry<byte[], byte[]>) iterator.next();
                map.put(Bytes.toString(wordRow.getKey()), Bytes.toString(wordRow.getValue()));
            }
        }
        catch (IOException e) {
            throw e;
        }
        finally{
        	if(null != table) table.close();
        	HbaseConnection.getInstance().getConnection().close();
        }
        return map;
    }
    
    /**
     * 功能描述：根据表名返回全表信息
     * @param tableName
     * @param columnFamily
     * @return Map<String,Map<String,Object>>
     * @throws IOException
     * @author 闫伟旗[yanweiqi@xinnet.com]
     * @since 2014-6-19
     * @update:[变更日期YYYY-MM-DD][更改人姓名][变更描述]
     */
    public Map<String,Map<String,Object>> scan(String tableName,String columnFamily) throws IOException{
    	HTableInterface table = null;
    	Map<String,Map<String,Object>> m1 = new TreeMap<String, Map<String,Object>>();
    	try{
	    	Scan scan = new Scan();
	    	table = HbaseConnection.getInstance().getConnection().getTable(tableName);
	    	ResultScanner rs = table.getScanner(scan);
	    	for (Result r : rs) {
	            Map<byte[], byte[]> familyMap = r.getFamilyMap(Bytes.toBytes(columnFamily));
	            String rowKey = Bytes.toString(r.getRow());
	            Set<Map.Entry<byte[], byte[]>> fSet = familyMap.entrySet();
	            Iterator<Map.Entry<byte[], byte[]>> iterator = fSet.iterator();
	            Map<String,Object> m2 = new HashMap<String, Object>();
	            while (iterator.hasNext()) {
	                Map.Entry<byte[], byte[]> wordRow = (Map.Entry<byte[], byte[]>) iterator.next();
	                m2.put(Bytes.toString(wordRow.getKey()), Bytes.toString(wordRow.getValue()));
	            }
	            m1.put(rowKey, m2);
			}
    	}
    	catch(IOException e){
    		throw e;
    	}
    	finally{
    		if(null != table) table.close();
    		HbaseConnection.getInstance().getConnection().close();
    	}
    	return m1;
    }
    
    public static void main(String[] args) throws IOException {
       while (true){
        logger.info("987654321");
        logger.info("123456789");
       }
    }
    
}

