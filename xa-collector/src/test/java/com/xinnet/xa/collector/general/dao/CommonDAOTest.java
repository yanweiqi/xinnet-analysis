package com.xinnet.xa.collector.general.dao;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.xinnet.xa.BaseSpringJunitUtil;

public class CommonDAOTest extends BaseSpringJunitUtil{
	
	@Autowired
	private CommonDAO commonDAO;

	@Test
	public void testExecSQLScript() {
		String sql = "CREATE TABLE `base_url_info_test` ( "+
					 "`id` int(11) NOT NULL AUTO_INCREMENT,"+
					 "`url` varchar(255) DEFAULT NULL,"+
					 "`url_detail_id` int(11) DEFAULT NULL,"+
					 "`search_type` int(11) DEFAULT NULL COMMENT '1唯一，2类',"+
					 "`priority` int(11) DEFAULT '0' COMMENT '优先级随数字增大',"+
					 "PRIMARY KEY (`id`)"+
					 ") ENGINE=InnoDB AUTO_INCREMENT=43 DEFAULT CHARSET=utf8;";
		commonDAO.execSQLScript(sql);
	}

}
