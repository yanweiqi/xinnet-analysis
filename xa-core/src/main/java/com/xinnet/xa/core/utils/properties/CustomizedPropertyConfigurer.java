package com.xinnet.xa.core.utils.properties;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;



public class CustomizedPropertyConfigurer extends PropertyPlaceholderConfigurer {

	private static Map<String, Object> ctxPropertiesMap;
	
    @Override  
    protected void processProperties(ConfigurableListableBeanFactory beanFactory,Properties props)throws BeansException {  
  
        super.processProperties(beanFactory, props);  
   
        ctxPropertiesMap = new HashMap<String, Object>();  
        for (Object key : props.keySet()) {  
            String keyStr = key.toString();  
            String value = props.getProperty(keyStr);  
            ctxPropertiesMap.put(keyStr, value);  
        }  
    }
    
    public  Object getContextProperty(String name) {  
        return ctxPropertiesMap.get(name);  
    }
}
