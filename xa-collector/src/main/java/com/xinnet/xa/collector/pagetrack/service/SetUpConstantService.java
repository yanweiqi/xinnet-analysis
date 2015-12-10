package com.xinnet.xa.collector.pagetrack.service;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.xinnet.xa.collector.saletrack.service.impl.ManagerThreadGroupService;
import com.xinnet.xa.collector.util.CollectorConstant;
import com.xinnet.xa.core.service.ISetUpConstantService;
import com.xinnet.xa.core.vo.CompConstantData;


/**
 * 功能描述：通过初始化的注册器，启动工作线程组 
 */
@Service
public class SetUpConstantService implements ISetUpConstantService{
	
	@Autowired
	private ManagerCollectorService managerCollectorService;
	
	@Autowired
	private ManagerThreadGroupService managerThreadGroupService;

	@Override
	public void setConstanst(String result) {
		 if(StringUtils.isNotBlank(result)){
			 CompConstantData data = JSON.parseObject(result, CompConstantData.class);
			 if(StringUtils.isNotBlank(data.getTypes())){
				 String[] typeArray = data.getTypes().split(",");
				 List<String> typeList = Arrays.asList(typeArray);
				 CollectorConstant.TRANSFORM_TYPES.clear();
				 CollectorConstant.TRANSFORM_TYPES.addAll(typeList);
			 }
			 if(data.getSaveThreadNum() >=1 ){
				 CollectorConstant.SAVE_THREAD_NUM = data.getSaveThreadNum();
			 }
			 if(data.getManageListSize() > 10 ){
				 CollectorConstant.MANAGE_MESSAGE_SIZE = data.getManageListSize();
			 }
			 if(CollectorConstant.SAVE_THREAD_RUM.get()){
				 int threadNum = managerCollectorService.saveThreadCount();
				 int num = CollectorConstant.SAVE_THREAD_NUM - threadNum;
				 int absNum = Math.abs(num);
				 if(absNum>0){
					 for(int i=0;i<absNum;i++){
						 if(num > 0){
							 managerCollectorService.addSaveThread();
						 }else{
							 managerCollectorService.lessenSaveThread();
						 }
					 }
				 }
			 }else{
				 managerCollectorService.startSaveThread();
				 managerThreadGroupService.startDoJob();
			 }
		 }
		
	}

}
