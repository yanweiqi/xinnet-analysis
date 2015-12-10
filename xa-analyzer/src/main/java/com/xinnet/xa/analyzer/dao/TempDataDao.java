package com.xinnet.xa.analyzer.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.lang.time.StopWatch;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.xinnet.xa.analyzer.common.SqlByTime;
import com.xinnet.xa.analyzer.vo.SourceKeyword;
import com.xinnet.xa.analyzer.vo.UrlInfo;

@Repository
public class TempDataDao extends AnalyzerDao{
	
	
	public List<SourceKeyword> getTempSEOSourceKeywords(){
		 StopWatch watch = new StopWatch();
		 watch.start();
		List<SourceKeyword> list = jdbcTemplate.query(SqlByTime.SELECT_TEMP_SOURCE_SEARCH_KEYWORDS, new RowMapper<SourceKeyword>(){

			@Override
			public SourceKeyword mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				SourceKeyword sk = new SourceKeyword();
				sk.setId(rs.getInt("id"));
				sk.setKeyword(rs.getString("searchkw"));
				return sk;
			}
			
		});
		watch.stop();
		logger.info(SqlByTime.SELECT_TEMP_SOURCE_SEARCH_KEYWORDS+" --run time:"+watch.getTime());
		return list;
	
	}
	
	public void batchUpdateTempSEOProductLine(final List<SourceKeyword> list){
		 StopWatch watch = new StopWatch();
		 watch.start();
		jdbcTemplate.batchUpdate(SqlByTime.UPDATE_TEMP_USER_COOKIES_ORGANIC_PRODUCT_LINE, new BatchPreparedStatementSetter() {

			@Override
			public void setValues(PreparedStatement ps, int i)
					throws SQLException {
				SourceKeyword sk = list.get(i);
				ps.setInt(1, sk.getProductLineId());
				ps.setInt(2, sk.getId());;
			}

			@Override
			public int getBatchSize() {
				// TODO Auto-generated method stub
				return list.size();
			}
			
		});
		watch.stop();
		logger.info(SqlByTime.UPDATE_TEMP_USER_COOKIES_ORGANIC_PRODUCT_LINE+" --run time:"+watch.getTime());
	}
	
	public List<UrlInfo> getInfoFuzzySearchData(){
		 StopWatch watch = new StopWatch();
		 watch.start();
		List<UrlInfo> list = jdbcTemplate.query(SqlByTime.SELECT_URL_INFO_FUZZY_SEARCH_DATA, new RowMapper<UrlInfo>(){

			@Override
			public UrlInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
				UrlInfo ui = new UrlInfo();
				ui.setUrl(rs.getString("url"));
				ui.setDetailId(rs.getInt("url_detail_id"));
				ui.setPriority(rs.getInt("priority"));
				return ui;
			}
			
		});
		watch.stop();
		logger.info(SqlByTime.SELECT_URL_INFO_FUZZY_SEARCH_DATA+" --run time:"+watch.getTime());
		return list;
	}
	
	

}
