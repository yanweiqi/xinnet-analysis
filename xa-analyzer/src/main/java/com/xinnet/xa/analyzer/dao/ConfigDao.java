package com.xinnet.xa.analyzer.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.xinnet.xa.analyzer.common.SqlByTime;
import com.xinnet.xa.analyzer.vo.SEOKeyword;
import com.xinnet.xa.analyzer.vo.SEOSourceType;

@Repository
public class ConfigDao extends AnalyzerDao{
	
	public List<SEOKeyword> getSEOKeywords(){
		List<SEOKeyword> list =jdbcTemplate.query(SqlByTime.SELECT_SEO_SEARCH_KEYWORDS,new RowMapper<SEOKeyword>(){

			@Override
			public SEOKeyword mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				SEOKeyword sk = new SEOKeyword();
				sk.setValue(rs.getString("name"));
				sk.setProductLineId(rs.getInt("product_line_id"));
				return sk;
			}
			
		});
		return list;
	}
	
	public List<SEOSourceType> getSEOSourceType(){
		List<SEOSourceType> list =jdbcTemplate.query(SqlByTime.SELECT_SEO_CHANNEL_SOURCE_TYPE,new RowMapper<SEOSourceType>(){

			@Override
			public SEOSourceType mapRow(ResultSet rs, int arg1)
					throws SQLException {
				SEOSourceType sst = new SEOSourceType();
				sst.setSoruceTypeId(rs.getInt("id"));
				sst.setName(rs.getString("name"));
				sst.setSource(rs.getString("source"));
				sst.setMedium(rs.getString("medium"));
				sst.setChannelId(rs.getInt("channel_id"));
				return sst;
			}
			
		});
		return list;
	}
	
 
	

}
