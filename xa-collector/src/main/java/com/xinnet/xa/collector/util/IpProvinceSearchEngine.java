package com.xinnet.xa.collector.util;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.TreeMap;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.xinnet.xa.collector.pagetrack.vo.IpProvinceRow;
import com.xinnet.xa.collector.pagetrack.vo.RangeRow;
import com.xinnet.xa.collector.pagetrack.vo.RangeSearchEngine;

public class IpProvinceSearchEngine extends RangeSearchEngine<Long, String>{
	public static final String SPLITER = "\\|";
	//
	private Logger logger = Logger.getLogger(getClass());
	
	public IpProvinceSearchEngine(){
		try {
			load();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	/**
	 * @Title: load
	 * @Description: 载入Ip库文件
	 * @author Administrator
	 * @param dataFile
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @return void 返回类型
	 */
	public void load() throws FileNotFoundException, IOException {
		TreeMap<Long, RangeRow<Long, String>> map = new TreeMap<Long, RangeRow<Long, String>>();
		BufferedReader file = null;
		try {
			 file = new BufferedReader(new FileReader(CollectorConstant.IP_DATA_PATH));
			//
			String content = null;
			while ((content = file.readLine()) != null) {
				String[] parts = content.split(SPLITER);
				if (parts.length < 3) {
					logger.warn("parse line failed, " + content);
					continue;
				}
				//
				 
					IpProvinceRow row = new IpProvinceRow();
					row.setBegin(Long.valueOf(parts[0]));
					row.setEnd(Long.valueOf(parts[1]));
					row.setValue(parts[2]);
					//
					if (parts.length > 3) {
						row.setCity(parts[3]);
					}
					//
					map.put(row.getBegin(), row);
				 
			}
			//
			setCache(map);
		} finally {
			try {
				file.close();
			} catch (IOException e) {
			}
		}
	}

	/**
	 * @Title: getProvince
	 * @Description: 获取省份信息
	 * @author Administrator
	 * @param @param ip
	 * @param @return 设定文件
	 * @return String 返回类型
	 */
	public String getProvince(String ip) {
		//
		long intIp = convertIpToLong(ip);
		//
		String result = getValue(intIp);
		if(StringUtils.isBlank(result)){
			result=GetIpUtil.getIpAreaByTaoBao(ip);
		}
		return result;
	}

	/**
	 * @Title: convertIpToLong
	 * @Description: 转换Ip为256进制整数
	 * @author Kolor
	 * @param ip
	 * @return long
	 */
	public static long convertIpToLong(String ip) {
		String[] checkIp = ip.split("\\.", 4);
		long intIp = 0;

		for (int i = 3, j = 0; i >= 0 && j <= 3; i--, j++) {
			intIp += Integer.parseInt(checkIp[j]) * Math.pow(256, i);
		}
		return intIp;
	}

	 

}
