package com.xinnet.xa.collector.pagetrack.dao.hbase.dao;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.time.StopWatch;
import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Before;
import org.junit.Test;

import com.xinnet.xa.collector.pagetrack.dao.hbase.Hbase2DAO;
import com.xinnet.xa.collector.util.hbase.RowData;
import com.xinnet.xa.core.entity.User;
import com.xinnet.xa.core.utils.convert.BeanUtils;


public class Hbase2DAOTest {
	
	private static Logger log = Logger.getLogger(Hbase2DAOTest.class);
	private Hbase2DAO hbaseDAO;
	private static String TABLENAME  = "users"; 
	private static String INFO_FAM   = "info";
	
	@Before
	public void init(){
		hbaseDAO = new Hbase2DAO(); 
	}

	@Test
	public void testCreateTable() throws Exception{
		hbaseDAO.createTable("pageveiw", INFO_FAM);
	}
	
	@Test
	public void testDelTable() throws IOException{
		hbaseDAO.delTable(TABLENAME);
	}
	
	@Test
	public void testPut() throws IOException{
		StopWatch watch = new StopWatch();
		Map<String,RowData> map = new HashMap<String, RowData>();
		
		String path = this.getClass().getClassLoader().getResource("propernames").getPath();
		File file = new File(path);
		List<String> usernames = FileUtils.readLines(file);
		AtomicInteger id = new AtomicInteger();
		for (String s : usernames) {
			Integer i =  id.getAndIncrement();
			RowData rowData = new RowData();
			rowData.setColumnFamily(INFO_FAM);
			User u = new User();
			u.setEmail(s+"@xinnet.com");
			u.setName(s);
			u.setPassword("123456");
			u.setUserId(String.valueOf(i));
			Map<String,Object>  m = new HashMap<String,Object>(BeanUtils.converBean2Map(u));
			rowData.setColumn(m);
			map.put(String.valueOf(i), rowData);
		}
		log.info(usernames.size());
		try {
			watch.start();
			hbaseDAO.save(TABLENAME, map,User.class);
			watch.stop();
			log.info("存入Hbase所需时间为："+watch.getTime()/1000 +"秒");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testGetResult() throws IOException{	
		Map<String, Map<String, Object>> m = hbaseDAO.scan(TABLENAME,INFO_FAM);
		for (Entry<String, Map<String, Object>> e: m.entrySet()) {
			log.info(e.getKey()+":"+e.getValue().toString());
		}
	}
	
	@Test
	public void testGetColumnByRowKey() throws IOException{
		Map<String,Object> map = hbaseDAO.getColumnByRowKey(TABLENAME, INFO_FAM, "1");
	    log.info(map);	
	}
	
	
	@Test
	public void testField() throws JsonGenerationException, JsonMappingException, IOException{
        User u = new User();
        u.setEmail("yanweiqi@xinnet.com");
        u.setName("闫伟旗");
        u.setPassword("123456");
        u.setUserId("1");
        User u2 = new User();
        //Map<String,Object> m = BeanUtils.converBean2Map(u2);
        
        //log.info(m.toString());
        ObjectMapper mapper = new ObjectMapper();
        StringWriter sw = new StringWriter();
        mapper.writeValue(sw, u);
        log.info(sw.toString());
	}
}

