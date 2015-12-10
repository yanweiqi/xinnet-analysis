package com.xinnet.xa.receive.service;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.xinnet.xa.core.service.ISetUpConstantService;
import com.xinnet.xa.core.vo.CompConstantData;
import com.xinnet.xa.utils.ReceiveContant;

@Service
public class SetUpConstantService implements ISetUpConstantService {

	@Override
	public void setConstanst(String result) {
		ReceiveContant.run.set(false);
		if (StringUtils.isNotBlank(result)) {
			CompConstantData data = JSON.parseObject(result, CompConstantData.class);
			if (StringUtils.isNotBlank(data.getQueues())) {
				String[] queueArray = data.getQueues().split(",");
				List<String> queueList = Arrays.asList(queueArray);
				ReceiveContant.QUEUES.clear();
				ReceiveContant.QUEUES.addAll(queueList);
			}
		}
		ReceiveContant.run.set(true);
	}
}
