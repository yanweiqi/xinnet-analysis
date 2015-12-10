package com.xinnet.xa.collector.pagetrack.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.xinnet.xa.core.vo.TrackData;

@Repository("collectorDao")
public class CollectorDao {
	public final static String INSERT_PAGE_SQL="insert into PageView(ActionTime,UID,TenantID,Terminal,IP,Area,Site,Source,PreviousPage,CurrentPage,Medium,Campaign,GroupSet,Keyword,SearchKW,SearchPKW,PageType,Action,FTime,LTime,VTime,TransData,JsSessionId,TimeRange,pageNum,NewVisitor) "+
            "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
	public final static String INSERT_TRANS_SQL="insert into transfromdata(ActionTime,UID,TenantID,Terminal,IP,Area,Site,PreviousPage,CurrentPage,JsSessionId,PageType,pageNum";
	
	public final static String ERROR_DATA_SQL = "select ActionTime,UID,Source,CurrentPage,Medium,Campaign,GroupSet,Keyword from PageView where Source ='DIRECT' and Medium is not null and ActionTime like '%08-15%'";
	
	public final static String UPDATA_ERROR_DATA_SQL="update PageView set CurrentPage=?,Source=? where ActionTime=? and UID=?";
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public void batchUpdate(final List<TrackData> list){
		jdbcTemplate.batchUpdate(INSERT_PAGE_SQL, new BatchPreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				TrackData data = list.get(i);
				ps.setString(1, data.getActionTime());
			    ps.setString(2, data.getUserId());
			    ps.setString(3, data.getTenantID());
				ps.setString(4, data.getTerminal());
				ps.setString(5, data.getIp());
				ps.setString(6, data.getArea());
				ps.setString(7, data.getDomain());
				ps.setString(8, data.getSource());
				ps.setString(9, data.getPrevUrl());
				ps.setString(10, data.getCurrentUrl());
				ps.setString(11, data.getUtm_medium());
				ps.setString(12, data.getUtm_campaign());
				ps.setString(13, data.getUtm_content());
				ps.setString(14, data.getUtm_term());
				ps.setString(15, data.getSearchKW());
				ps.setString(16, data.getSearchPKW());
				ps.setString(17, data.getOperationType());
				ps.setString(18, data.getAction());
				ps.setString(19, data.getFtime());
				ps.setString(20, data.getLtime());
				ps.setLong(21, data.getVtime());
				ps.setString(22, data.getOperationData());
				ps.setString(23, data.getJsSessionId());
				ps.setString(24, data.getTimeRange());
				ps.setInt(25, data.getPageNumber());
				ps.setInt(26, data.getNewVisitor());
			}
			
			@Override
			public int getBatchSize() {
				// TODO Auto-generated method stub
				return list.size();
			}
		});
		
	}
	
	public void insertPageView(TrackData data){
		Object[] objects = { data.getActionTime(), data.getUserId(),
				data.getTenantID(), data.getTerminal(), data.getIp(),
				data.getArea(), data.getDomain(), data.getSource(),
				data.getPrevUrl(), data.getCurrentUrl(),
				data.getUtm_medium(), data.getUtm_campaign(),
				data.getUtm_content(), data.getUtm_term(),
				data.getSearchKW(), data.getSearchPKW(),
				data.getOperationType(), data.getAction(),
				data.getFtime(), data.getLtime(), data.getVtime(),
				data.getOperationData(), data.getJsSessionId(),
				data.getTimeRange(),data.getPageNumber(),data.getNewVisitor() };
		jdbcTemplate.update(INSERT_PAGE_SQL, objects);
	}
	
	public void updateErrorData(final List<TrackData> list){
		jdbcTemplate.batchUpdate(UPDATA_ERROR_DATA_SQL, new BatchPreparedStatementSetter() {

			@Override
			public void setValues(PreparedStatement ps, int i)
					throws SQLException {
				TrackData data = list.get(i);
				ps.setString(1, data.getCurrentUrl());
				ps.setString(2, data.getUtm_source());
				ps.setString(3, data.getActionTime());
				ps.setString(4, data.getUserId());
				
			}

			@Override
			public int getBatchSize() {
				// TODO Auto-generated method stub
				return list.size();
			}
			
		});
	}
	
	public List<TrackData> selectError(){
		List<TrackData> lists =jdbcTemplate.query(ERROR_DATA_SQL, new RowMapper<TrackData>(){

			@Override
			public TrackData mapRow(ResultSet rs, int rowNum) throws SQLException {
				TrackData data = new TrackData();
				data.setActionTime(rs.getString("ActionTime"));
				data.setUserId(rs.getString("UID"));
				data.setSource(rs.getString("Source"));
				data.setCurrentUrl(rs.getString("CurrentPage"));
				data.setUtm_medium(rs.getString("Medium"));
				data.setUtm_campaign(rs.getString("Campaign"));
				data.setUtm_content(rs.getString("GroupSet"));
				data.setUtm_term(rs.getString("Keyword"));
				try {
					data.createUtm();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return data;
			}
			
		});
		return lists;
	}
	
	public void batchInsterTransfrom(List<TrackData> dataList){
		int length = dataList.size();
		String[] sqls = new String[length];
		for(int i=0;i<length;i++){
			sqls[i]=createTransfromSql(dataList.get(i));
		}
		jdbcTemplate.batchUpdate(sqls);
	}
	
	public void insterTransfrom(TrackData data){
		String sql = this.createTransfromSql(data);
		jdbcTemplate.update(sql);
	}
	
	
	private String  createTransfromSql(TrackData data){
		StringBuilder sb = new StringBuilder(INSERT_TRANS_SQL);
		StringBuilder valueSb= new StringBuilder(" values(");
		String[] attrs = {data.getActionTime(),data.getUserId(),data.getTenantID(),data.getTerminal(),data.getIp(),data.getArea(),data.getDomain(),data.getPrevUrl(),data.getCurrentUrl(),data.getJsSessionId(),data.getOperationType()};
		this.addValueSql(valueSb, attrs);
		valueSb.append(",").append(data.getPageNumber());
		String[] transfromDatas = data.getTransformDatas();
		int length = transfromDatas.length;
		for(int i=0;i<length;i++){
			sb.append(",Data").append(i);
			valueSb.append(",'").append(transfromDatas[i]).append("'");
			 
		}
		sb.append(")").append(valueSb).append(")");
		return sb.toString();
	}
	
	private void addValueSql(StringBuilder valueSb,String[] attrs){
		int length = attrs.length;
		for(int i=0;i<length;i++){
			if(i>0){
				valueSb.append(",");
			}
			valueSb.append("'").append(attrs[i]).append("'");
		}
		
	}
	
}
