package com.xinnet.xa.collector.general.dao;

import java.util.List;
import java.util.Map;

/**
 * 类名称：ICommonDAO  
 * 类描述： commonDAO基类
 * 创建人：YWQ  
 */
public interface ICommonDAO {

	/**
	 * 功能描述： 支持原生的SQL查询，带参,顺序参数。
	 * @param sql
	 * @param params
	 * @return:List<T>
	 */
	public <T> List<T> findByNativeSQL(String sql,Object[] params);
		
	/**
	 * 功能描述： 支持原生的SQL查询，带参,命名参数。
	 * @param sql
	 * @param params
	 * @return:List<T>
	 */
	public <T> List<T> findByNativeSQL(String sql,Map<String,Object> params);
	
	
	/**
	 * 功能描述：支持原生的SQL脚本
	 * @param sql
	 */
	public void execSQLScript(String sql);
	
	
}
