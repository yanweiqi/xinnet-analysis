package com.xinnet.xa.core.service;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.xinnet.xa.core.Constant;
import com.xinnet.xa.core.utils.HttpClientUtil;
import com.xinnet.xa.core.utils.Utils;

@Service
public class ExceptionMessageService {
	private Logger logger = Logger.getLogger(ExceptionMessageService.class);
	
	public void sendException(Exception e){
		new SendExceptionThread(e).start();
	}
	
	class SendExceptionThread extends Thread{
		
		private Exception e;

		public SendExceptionThread(Exception e) {
			super();
			this.e = e;
		}

		@Override
		public void run() {
			Map<String,String> params = new HashMap<String, String>();
			params.put("ip", Constant.IP);
			params.put("port", String.valueOf(Constant.PORT));
			params.put("compType", Constant.COMP_TYPE);
			params.put("message", e.getMessage());
			params.put("stacks", Utils.getExceptionStacks(e));
			try {
				HttpClientUtil.post(Constant.CLCT_SEND_EXCEPTION_URL, params, "utf-8");
			} catch (Exception e1) {
				logger.error(e1.getMessage(), e1);
			}
		}
		
		
	}

}
