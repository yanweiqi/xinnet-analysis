package com.xinnet.xa.core.dao;

import org.springframework.jdbc.core.JdbcTemplate;


public class BaseDao  {
	
	protected JdbcTemplate jdbcTemplate;
	
//	 public <T> List<T>  queryList(String sql){
//		return jdbcTemplate.query(sql, new RowMapper<T>(){
//
//			@Override
//			public T mapRow(ResultSet rs, int rowNum) throws SQLException {
//				// TODO Auto-generated method stub
//				return createMapRow(rs, rowNum);
//			}
//			 
//		 });
//	 }
//	 
//	 protected <T> T createMapRow(ResultSet rs, int rowNum) {
//		 return null;
//		
//	}
	
	public void update(String sql,Object... args){
		jdbcTemplate.update(sql,  args);
	}

}
