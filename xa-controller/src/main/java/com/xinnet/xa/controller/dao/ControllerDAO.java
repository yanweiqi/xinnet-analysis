package com.xinnet.xa.controller.dao;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


public class ControllerDAO {
	
	private JdbcTemplate jdbcTemplate;
	
	public void select(){
		String sql = "SELECT * FROM SITE";
		List<Map<String,Object>> list = jdbcTemplate.queryForList(sql);
		if(!list.isEmpty()){
			for(Map<String,Object> map :list){
				Set<Entry<String,Object>> entrySet = map.entrySet();
				for(Entry<String,Object> entry: entrySet){
					System.out.println(entry.getKey()+entry.getValue());
				}
			}
		}
		System.out.println(list.size());
	}
	
 
}
