package com.xinnet.xa.controller.service;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.xinnet.xa.controller.BaseSpringJunitUtil;
import com.xinnet.xa.controller.dao.ComponentDao;
import com.xinnet.xa.controller.model.Component;

public class CommponentDaoTest extends BaseSpringJunitUtil {
	@Autowired
	ComponentDao componentDao;
	
	@Test
	public void testFind(){
		List<Component> components =componentDao.getComponentsByState(1);
		for(Component c : components){
			System.out.println(c);
		}
		 
	}
	
	 

}
