package com.xinnet.xa.core.utils.convert;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

public class BeanUtils {

	private static Logger log = Logger.getLogger(BeanUtils.class);
	private static final String SET = "set";
	private static final String GET = "get";

	/**
	 * Bean转换为Map
	 * 
	 * @param <T>
	 * @param pojo
	 * @return Map
	 */
	public static <T> Map<String, Object> converBean2Map(T pojo) {
		Map<String, Object> rtnMap = new HashMap<String, Object>();
		Class<? extends Object> classType = pojo.getClass();
		// bean方法
		Method[] methods = classType.getDeclaredMethods();
		try {
			// 获取所有get方法
			for (Method method : methods) {
				String methodName = method.getName();
				if (methodName.startsWith(GET)) {
					// 获取属性名
					String filedName = methodName.substring(GET.length());
					StringBuffer sb = new StringBuffer();
					sb.append(filedName.substring(0, 1).toLowerCase()).append(
							filedName.substring(1));
					// Map数据摄值
					rtnMap.put(sb.toString(), method.invoke(pojo));
				}
			}
		} catch (IllegalArgumentException e) {
			log.error(e.getMessage(), e);
		} catch (IllegalAccessException e) {
			log.error(e.getMessage(), e);
		} catch (InvocationTargetException e) {
			log.error(e.getMessage(), e);
		}
		return rtnMap;
	}

	/**
	 * Map转换为Bean
	 * 
	 * @param <T>
	 * @param pojo
	 * @param map
	 * @return T
	 */
	public static <T> T convertMap2Bean(T pojo, Map<String, String> map) {
		Class<? extends Object> classType = pojo.getClass();
		Iterator<String> it = map.keySet().iterator();
		try {
			// 遍历结果集数据列
			while (it.hasNext()) {
				// 获取属性名
				String fieldName = it.next().toLowerCase();
				// 属性名首字母大写
				String stringLetter = fieldName.substring(0, 1).toUpperCase();
				// 生成get/set方法名
				String setName = SET + stringLetter + fieldName.substring(1);
				String getName = GET + stringLetter + fieldName.substring(1);
				// 方法名反射获取get/set方法
				Method getMethod = classType.getMethod(getName, new Class[] {});
				Method setMethod = classType.getMethod(setName,
						new Class[] { getMethod.getReturnType() });
				// 通过方法获取参数类型
				Class<? extends Object> fieldType = setMethod
						.getParameterTypes()[0];
				// 获取数据并做数据类型转换
				Object value = formatValue(map.get(fieldName), fieldType);
				// 赋值操作
				setMethod.invoke(pojo, new Object[] { value });
			}
		} catch (IllegalArgumentException e) {
			log.error(e.getMessage(), e);
		} catch (IllegalAccessException e) {
			log.error(e.getMessage(), e);
		} catch (InvocationTargetException e) {
			log.error(e.getMessage(), e);
		} catch (SecurityException e) {
			log.error(e.getMessage(), e);
		} catch (NoSuchMethodException e) {
			log.error(e.getMessage(), e);
		}
		return pojo;
	}

	/**
	 * bean转化成字符串
	 * 
	 * @param bean
	 * @return beanStr
	 */
	public static String convertBean2Str(Object bean) {
		StringBuffer sb = new StringBuffer();
		// 获取beanName名,格式:类名首字母小写
		String beanName = bean.getClass().getSimpleName();
		sb.append(beanName.substring(0, 1).toLowerCase()).append(beanName.substring(1));
		return convertBean2Str(bean, sb.toString());
	}

	/**
	 * bean转化成字符串
	 * @param bean
	 * @param beanName
	 * @return beanStr
	 */
	public static String convertBean2Str(Object bean, String beanName) {
		StringBuffer str = new StringBuffer();
		Class<? extends Object> type = bean.getClass();
		try {
			BeanInfo beanInfo = Introspector.getBeanInfo(type);
			PropertyDescriptor[] propertyDescriptors = beanInfo
					.getPropertyDescriptors();
			for (int i = 0; i < propertyDescriptors.length; i++) {
				PropertyDescriptor descriptor = propertyDescriptors[i];
				String propertyName = descriptor.getName();
				if (!propertyName.equals("class")) {
					Method readMethod = descriptor.getReadMethod();
					Object result = readMethod.invoke(bean, new Object[0]);
					if (result != null && StringUtils.trim(result.toString()).length() != 0){
						str.append("&")
						.append(beanName)
						.append(".")
						.append(propertyName)
						.append("=")
						.append(result);
					}
				}
			}
		}
		catch (Exception e){
			log.error(e.getMessage(), e);
		}
		return str.indexOf("&") != -1 ? str.substring(1) : str.toString();
	}

	/**
	 * 将map中数据与bean中属性类型一致
	 * <功能详细描述>
	 * @param fieldValue
	 *            属性值
	 * 
	 * @param fieldType
	 *            属性类型
	 * 
	 * @return formatValue 格式化数据
	 */
	private static Object formatValue(String fieldValue,Class<? extends Object> fieldType){
		Object value = null;
		if (fieldType == Integer.class || "int".equals(fieldType.getName())) {
			if (fieldValue != null){
				value = Integer.parseInt(fieldValue);
			}
		}

		else if (fieldType == Float.class || "float".equals(fieldType.getName())) {
			if (fieldValue != null){
				value = Float.parseFloat(fieldValue);
			}
		}

		else if (fieldType == Double.class || "double".equals(fieldType.getName()))	{
			if (fieldValue != null){
				value = Double.parseDouble(fieldValue);
			}
		}
		else if (fieldType == Date.class || fieldType == java.util.Date.class){
			if (fieldValue != null){
				value = Date.valueOf(fieldValue);
			}
		} else {
			value = fieldValue;
		}
		return value;
	}

}
