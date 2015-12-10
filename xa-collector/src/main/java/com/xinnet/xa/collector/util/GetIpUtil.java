package com.xinnet.xa.collector.util;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;
import com.xinnet.xa.collector.pagetrack.vo.TaobaoIpDTO;
import com.xinnet.xa.core.utils.HttpClientUtil;

public class GetIpUtil {
	private static Logger logger = Logger.getLogger(GetIpUtil.class);
	
	public static String getIpAreaByTaoBao(String ip) {
		logger.info("start taobao query ip:"+ip);
		String url = CollectorConstant.TAOBO_IP_URL+"?ip="+ip;
		TaobaoIpDTO ipDto = null;
		 
		try {
			String result = HttpClientUtil.get(url, "utf-8", null);
			ipDto = JSON.parseObject(result,TaobaoIpDTO.class);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		if(ipDto==null)
			return "";
		return ipDto.getData().getXaCounty();
		 
	  
	 
	}
	
	public static void main(String[] args) {
		System.out.println(getIpAreaByTaoBao("14.136.195.245"));
	}
	

}
