package com.xinnet.xa.analyzer.dao;

import org.apache.commons.lang.time.StopWatch;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.xinnet.xa.analyzer.common.AnalyzerConstant;
import com.xinnet.xa.analyzer.common.AnalyzerConstant.TimeType;

@Repository
public class AnalyzerDao {
	protected Logger logger = Logger.getLogger(getClass());

	@Autowired
	protected JdbcTemplate jdbcTemplate;

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void update(String sql, Object... args) {
		StopWatch watch = new StopWatch();
		watch.start();
		jdbcTemplate.update(sql, args);
		watch.stop();
		logger.info(sql + " --run time:" + watch.getTime());
	}

	public void update(String sql) {
		StopWatch watch = new StopWatch();
		watch.start();
		jdbcTemplate.update(sql);
		watch.stop();
		logger.info(sql + " --run time:" + watch.getTime());
	}

	public void updateDateSql(String sql, TimeType timeType) {
		sql = sql.replace(AnalyzerConstant.WHERE_TIME_TYPE,
				timeType.getColumnName());
		update(sql);
	}

	public void updateDateSql(String sql, TimeType timeType, Object... args) {
		sql = sql.replace(AnalyzerConstant.WHERE_TIME_TYPE,
				timeType.getColumnName());
		update(sql, args);
	}

	public void deleteDataByDay(String tableName, String day) {
		StringBuilder sb = new StringBuilder();
		sb.append("delete from ").append(tableName).append(" where date=?");
		update(sb.toString(), day);
		logger.info("delete " + tableName + " " + day + " ok");
	}

	public void truncateTable(String tableName) {
		StringBuilder sb = new StringBuilder();
		sb.append("truncate table ").append(tableName);
		update(sb.toString());

	}
	
	protected String replaceTimeSql(String sql, TimeType timeType){
		return sql.replace(AnalyzerConstant.WHERE_TIME_TYPE,
				timeType.getColumnName());
	}

}
