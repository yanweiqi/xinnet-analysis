package com.xinnet.xa.collector.pagetrack.dao;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.xinnet.xa.BaseSpringJunitUtil;
import com.xinnet.xa.core.vo.TrackData;

public class CollectorDaoTest extends BaseSpringJunitUtil {
	@Autowired
	CollectorDao collectorDao;

	@Test
	public void testInsterTransfromDatas() {
		List<TrackData> list = collectorDao.selectError();
		int length = list.size();
		int size = length/1000;
		for(int i=0;i<size;i++){
			int start = i*1000;
			int end = start+999;
			List<TrackData> subList = list.subList(start, end);
			collectorDao.updateErrorData(subList);
			System.out.println("update success");
		}
		
		int start = size *1000;
		int end = start + length%1000-1;
		List<TrackData> subList = list.subList(start, end);
		collectorDao.updateErrorData(subList);
		System.out.println("update success");
	}

}
