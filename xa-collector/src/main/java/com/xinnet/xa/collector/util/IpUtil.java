package com.xinnet.xa.collector.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.xinnet.xa.collector.pagetrack.vo.IpProvinceRow;
import com.xinnet.xa.collector.pagetrack.vo.RangeRow;



public class IpUtil {
	public static final String SPLITER = "\\|";
	//
	private static Logger logger = Logger.getLogger(IpUtil.class);
	private static TreeMap<Long, RangeRow<Long, String>> cache = new TreeMap<Long, RangeRow<Long, String>>();
	static {
		load();
	}

	public static String getValue(Long key) {
		//
		RangeRow<Long, String> row = getRow(key);
		if (row == null) {
			return null;
		}

		//
		Long end = row.getEnd();
		if (end == null) {
			return null;
		}
		if (end.compareTo(key) > 0) {
			return row.getValue();
		}

		return null;
	}

	public static RangeRow<Long, String> getRow(Long key) {
		if (key == null) {
			return null;
		}
		//
		Entry<Long, RangeRow<Long, String>> entry = cache.floorEntry(key);
		if (entry == null || entry.getValue() == null) {
			return null;
		}
		//
		return entry.getValue();
	}

	public static void putAll(Map<Long, RangeRow<Long, String>> map) {
		cache.putAll(map);
	}

	public static RangeRow<Long, String> put(RangeRow<Long, String> row) {
		return cache.put(row.getBegin(), row);
	}

	public static RangeRow<Long, String> remove(Long key) {
		return cache.remove(key);
	}

	public static void clear() {
		cache.clear();
	}

	public static TreeMap<Long, RangeRow<Long, String>> getCache() {
		return cache;
	}

	public static TreeMap<Long, RangeRow<Long, String>> setCache(
			TreeMap<Long, RangeRow<Long, String>> newCache) {
		if (newCache == null) {
			throw new NullPointerException();
		}
		//
		TreeMap<Long, RangeRow<Long, String>> before = cache;
		cache = newCache;
		//
		return before;
	}

	public static void load() {
		TreeMap<Long, RangeRow<Long, String>> map = new TreeMap<Long, RangeRow<Long, String>>();
		BufferedReader file = null;
		try {
			file = new BufferedReader(new FileReader(
					CollectorConstant.IP_DATA_PATH));
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
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			try {
				file.close();
			} catch (IOException e) {
				logger.error(e.getMessage(), e);
			}
		}
	}

	public static String getProvince(String ip) {
		String result = "unknown";
		if (StringUtils.isNotBlank(ip)) {
			if (ip.indexOf(",") > 0) {
				String[] ips = ip.split(",");
				ip = ips[ips.length - 1];
			}
			ip = ip.trim();

			if (isboolIP(ip)) {
				//
				long intIp = convertIpToLong(ip);
				//
				result = getValue(intIp);
				if (StringUtils.isBlank(result)) {
					result = GetIpUtil.getIpAreaByTaoBao(ip);
				}
			}
		}
		return result;
	}

	public static long convertIpToLong(String ip) {
		String[] checkIp = ip.split("\\.", 4);
		long intIp = 0;

		for (int i = 3, j = 0; i >= 0 && j <= 3; i--, j++) {
			intIp += Integer.parseInt(checkIp[j]) * Math.pow(256, i);
		}
		return intIp;
	}

	public static boolean isboolIP(String ipAddress) {
		String ip = "(2[5][0-5]|2[0-4]\\d|1\\d{2}|\\d{1,2})\\.(25[0-5]|2[0-4]\\d|1\\d{2}|\\d{1,2})\\.(25[0-5]|2[0-4]\\d|1\\d{2}|\\d{1,2})\\.(25[0-5]|2[0-4]\\d|1\\d{2}|\\d{1,2})";
		Pattern pattern = Pattern.compile(ip);
		Matcher matcher = pattern.matcher(ipAddress);
		return matcher.matches();
	}

	public static void main(String[] args) {
		System.out.println(IpUtil.getProvince("59.115.157.95"));
	}

}
