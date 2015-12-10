package com.xinnet.xa.analyzer.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.lang.time.StopWatch;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.stereotype.Repository;

import com.xinnet.xa.analyzer.common.SqlByTime;
import com.xinnet.xa.analyzer.vo.OrderDetail;

@Repository
public class OrderDataDao extends AnalyzerDao{
	
	public void batchInsertData(final List<OrderDetail> list){
		 StopWatch watch = new StopWatch();
		 watch.start();
		jdbcTemplate.batchUpdate(SqlByTime.INSERT_ORDER_DETAIL,new BatchPreparedStatementSetter() {

			@Override
			public int getBatchSize() {
				// TODO Auto-generated method stub
				return list.size();
			}

			@Override
			public void setValues(PreparedStatement ps, int i)
					throws SQLException {
//				operate_time,hycode,order_code,total_money,product_name,super_goods,buy_type,is_refund
				OrderDetail od = list.get(i);
				ps.setString(1, od.getOperationTime());
				ps.setString(2, od.getAgentCode());
				ps.setString(3, od.getOrderCode());
			    ps.setDouble(4, od.getTotalMoney());
				ps.setString(5, od.getGoodsName());
				ps.setString(6, od.getSuperGoodsName());
				ps.setString(7, od.getBuyType());
				ps.setString(8, od.getIsRefund());
				ps.setString(9, od.getGoodsClassName());
				
			}
			
		});
		watch.stop();
		logger.info(SqlByTime.INSERT_ORDER_DETAIL+" --run time:"+watch.getTime());
	}

}
