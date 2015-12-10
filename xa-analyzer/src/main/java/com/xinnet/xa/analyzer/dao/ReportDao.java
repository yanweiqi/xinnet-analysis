package com.xinnet.xa.analyzer.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
@Repository
public class ReportDao extends AnalyzerDao{
	
	public List<String[]> getReportData(String sql,final String[] columnNames,Object... args){
		List<String[]> dataList = jdbcTemplate.query(sql, new RowMapper<String[]>(){

			@Override
			public String[] mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				int length = columnNames.length;
				 String[] columns = new String[length];
				 for(int i=0;i<length;i++){
					 columns[i]=rs.getString(columnNames[i]);
				 }
				 return columns;
			}
			
		}, args);
		return dataList;
	}

}
