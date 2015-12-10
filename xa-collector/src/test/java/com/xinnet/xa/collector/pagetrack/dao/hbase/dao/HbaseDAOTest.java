package com.xinnet.xa.collector.pagetrack.dao.hbase.dao;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import com.xinnet.xa.collector.pagetrack.dao.hbase.HbaseDAO;


public class HbaseDAOTest {
	
	private HbaseDAO hbaseDAO;
	
	@Before
	public void init(){
		hbaseDAO = new HbaseDAO(); 
	}

	@Test
	public void testCreateTable() throws Exception{
		hbaseDAO.createTable("users", "fm");
	}
	
	@Test
	public void testDelTable() throws IOException{
		hbaseDAO.delTable("users");
	}
	
	
}
