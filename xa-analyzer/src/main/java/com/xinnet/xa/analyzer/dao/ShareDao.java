package com.xinnet.xa.analyzer.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.xinnet.share.backflow.vo.BackflowData;
import com.xinnet.xa.analyzer.common.SqlByTime;

@Repository
public class ShareDao extends AnalyzerDao {

	public List<BackflowData> findShareUv(String startTime,String endTime) {
		
		List<BackflowData> list = jdbcTemplate.query(SqlByTime.FIND_SHARE_TOOL_UV,
				new RowMapper<BackflowData>() {

					@Override
					public BackflowData mapRow(ResultSet rs, int rowNum)
							throws SQLException {
						BackflowData sd = new BackflowData();
						int shareTypeId = getShareTypeId(rs.getString("name"));
						sd.setShareTypeId(shareTypeId);
						sd.setShareToolName(rs.getString("campaign"));
						sd.setShareToolId(rs.getString("group_set"));
						sd.setShareHycode(rs.getString("keyword"));
						sd.setArea(rs.getString("area"));
						sd.setIp(rs.getString("ip"));
						sd.setAccessTime(rs.getString("action_time"));
						return sd;
					}

				},startTime,endTime);

		return list;
	}

	public List<BackflowData> findShareRegister(String startTime,String endTime) {
		
		List<BackflowData> list = jdbcTemplate.query(SqlByTime.FIND_SHARE_REGISTER,new RowMapper<BackflowData>() {

					@Override
					public BackflowData mapRow(ResultSet rs, int rowNum)
							throws SQLException {
						BackflowData sed = new BackflowData();
						int shareTypeId = getShareTypeId(rs.getString("name"));
						sed.setShareTypeId(shareTypeId);
						sed.setShareToolName(rs.getString("campaign"));
						sed.setShareToolId(rs.getString("groupset"));
						sed.setShareHycode(rs.getString("keyword"));
						sed.setCode(rs.getString("hycode"));
						sed.setAccessTime(rs.getString("register_date"));
						return sed;
					}

				}, startTime,endTime);
		return list;
	}
	
	public List<BackflowData> findShareOrder(String startTime,String endTime){
		
		List<BackflowData> list = jdbcTemplate.query(SqlByTime.FIND_SHARE_ORDER, new RowMapper<BackflowData>(){

			@Override
			public BackflowData mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				BackflowData sod = new BackflowData();
				int shareTypeId = getShareTypeId(rs.getString("name"));
				sod.setShareTypeId(shareTypeId);
				sod.setShareToolName(rs.getString("campaign"));
				sod.setShareToolId(rs.getString("group_set"));
				sod.setShareHycode(rs.getString("keyword"));
				sod.setCode(rs.getString("order_code"));
				sod.setAccessTime(rs.getString("operate_time"));
				return sod;
			}},startTime,endTime);
		return list;
	}
	
	private int getShareTypeId(String shareTypeName){
		if(shareTypeName.equals("微博")){
			return 1;
		}
		if(shareTypeName.equals("微信")){
			return 2;
		}
		return 0;
	}

}
