package com.xinnet.xa.receive.web;

import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Maps;
import com.xinnet.xa.core.Constant;
import com.xinnet.xa.core.utils.DateUtil;
import com.xinnet.xa.core.utils.XaWebUtils;
import com.xinnet.xa.core.vo.CompMonitorData;
import com.xinnet.xa.core.vo.TrackData;
import com.xinnet.xa.core.vo.UserGoodsOrderDetailVo;
import com.xinnet.xa.receive.service.ReceiveService;
import com.xinnet.xa.utils.ReceiveContant;

@Controller
public class BrowserReceiveController {

	private static Logger logger = Logger.getLogger(BrowserReceiveController.class);

	@Autowired
	private ReceiveService receiveService;

	/**
	 * 功能描述：对客户的浏览器行为跟踪
	 * 
	 * @author 闫伟旗[yanweiqi@xinnet.com]
	 * @since 2014-6-5
	 * @update:[变更日期YYYY-MM-DD][更改人姓名][变更描述]
	 */
	@RequestMapping(value = "/track.do")
	public @ResponseBody String track(TrackData data, HttpServletRequest baseRequest,
			HttpServletResponse response) {
		data.setActionTime(DateUtil.formatDateTime(new Date()));
		data.setTerminal(baseRequest.getHeader("User-Agent"));
		String ip = XaWebUtils.getRemortIP(baseRequest);
		data.setIp(ip);
		logger.info(data);
		receiveService.sendMessage(data);
		response.setDateHeader("Expires", 0);
		response.addHeader("Cache-Control", "no-cache");// 浏览器和缓存服务器都不应该缓存页面信息
		response.addHeader("Cache-Control", "no-store");// 请求和响应的信息都不应该被存储在对方的磁盘系统中；
		response.addHeader("Cache-Control", "must-revalidate");
		return "success";
	}
	
	/**
	 * 功能描述：用户购买行为跟踪
	 * @author 闫伟旗[yanweiqi@xinnet.com]
	 * @since 2014-6-5
	 * @update:[变更日期YYYY-MM-DD][更改人姓名][变更描述]
	 */
	@RequestMapping(value = "/trackUserBuyGoods.do")
	public @ResponseBody String trackUserBuyGoods(UserGoodsOrderDetailVo userGoodsOrder,
			                                      HttpServletRequest request,	
			                                      HttpServletResponse response){
		Map<String, Object> paraMap = Maps.newHashMap();
		logger.info(userGoodsOrder);
		String queueName = "goodsBuyQueue";
		paraMap.put(queueName, userGoodsOrder);
		receiveService.sendMessageToMQ(paraMap);
		paraMap.clear();
		return "success";
	}
	
	
	@RequestMapping(value="/monitor.do")
	public @ResponseBody CompMonitorData  monitor(){
		CompMonitorData data = new CompMonitorData();
		data.setState(Constant.COMP_STATE_OK);
		data.setReceiveCount(ReceiveContant.RECE_COUNT.get());
		return data;
	}
    
	
}
