package com.xinnet.xa.core.utils.properties;

import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 属性文件的工具类
 * @author 闫伟旗[yanweiqi@xinnet.com]
 * @version 
 * @since 2014-6-6
 */
public class PropertitesFactory {
    private  final Log log = LogFactory.getLog(getClass());

    private static ConcurrentHashMap<String, Properties> propertitesHashMap;
    public  static PropertitesFactory instance = null;

    public static PropertitesFactory getInstance(){
        if(instance == null){
             instance = new PropertitesFactory();
        }
        return instance;
    }

    private PropertitesFactory(){

    	if(null == propertitesHashMap){
    		propertitesHashMap = new  ConcurrentHashMap<String, Properties> ();	
    	}
    }

    private  Properties getProperties(String path){
    	Properties properties = propertitesHashMap.get(path);
    	if(null == properties){
          try {
		      properties = new Properties();
		      properties.load(getClass().getResourceAsStream(path));
		      propertitesHashMap.put(path, properties);
		      return properties;
		  } catch (IOException e) {
		      log.error("load system constants file error!");
		      properties = null;
		  }	
    	}
    	return properties;
    }
    
    /**
     * 功能描述：根据propertites路径和属性值获取Vaule
     * @param path
     * @param key
     * @return String 
     * @author 闫伟旗[yanweiqi@xinnet.com]
     * @since 2014-6-6
     * @update:[变更日期YYYY-MM-DD][更改人姓名][变更描述]
     */
    public String getPropertyByPathAndKey(String path,String key){
    	Properties p = getProperties(path);
    	return p.getProperty(key);
    }
}
