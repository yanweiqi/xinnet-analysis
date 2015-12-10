package com.xinnet.xa.collector.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;

import com.xinnet.xa.collector.saletrack.vo.UserGoodsOrderComparator;
import com.xinnet.xa.core.Constant;
import com.xinnet.xa.core.utils.properties.PropertitesFactory;
import com.xinnet.xa.core.vo.UserGoodsOrderDetailVo;

public class CollectorConstant {
	public static AtomicLong count = new AtomicLong();
	public final static ThreadGroup MESSAGE_COLLECTOR_THREAD_GROUP = new ThreadGroup("collectorThreadGroup");//用于获取与管理线程状态
	public final static ArrayBlockingQueue<Object> DATA_QUEUE= new ArrayBlockingQueue<Object>(1000);
	public static PriorityBlockingQueue<UserGoodsOrderDetailVo> DATA_BLOCKINGQUEUE = new PriorityBlockingQueue<UserGoodsOrderDetailVo>(1000, new UserGoodsOrderComparator()) ;
	public static int MANAGE_MESSAGE_SIZE = 100;
	public final static List<String> TRANSFORM_TYPES = new ArrayList<String>();
	public static int SAVE_THREAD_NUM=1;
	public final static String IP_DATA_PATH= PropertitesFactory.getInstance().getPropertyByPathAndKey(Constant.PROPERTITIES_PATH,"ip.data.path");
	public final static String TAOBO_IP_URL=PropertitesFactory.getInstance().getPropertyByPathAndKey(Constant.PROPERTITIES_PATH,"taobao.ip.url");
	public final static AtomicBoolean SAVE_THREAD_RUM= new AtomicBoolean(false);
	public final static String DATA_BLOCKINGQUEUE_TRACKDATE_KEY = "trackdata_key";
	public final static String DATA_BLOCKINGQUEUE_USERGOODSORDER_KEY = "usergoodsorder_key";
	
	
	public static Map<String, BlockingQueue<?>> DATA_MAP_BLOCKINGQUEUE = new ConcurrentHashMap<String, BlockingQueue<?>>(){
		private static final long serialVersionUID = 8544571051723337862L;
		{
		  put("trackdata_key",DATA_QUEUE);
		  put("usergoodsorder_key",DATA_BLOCKINGQUEUE);
		}
	};
	

    
}

