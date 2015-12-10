package com.xinnet.xa.analyzer.common;

import java.util.concurrent.ArrayBlockingQueue;

import com.xinnet.xa.analyzer.vo.RunParam;
import com.xinnet.xa.core.Constant;
import com.xinnet.xa.core.utils.properties.PropertitesFactory;

public class AnalyzerConstant {
	public final static String SCHEDULING_PLATFROM_URL=PropertitesFactory.getInstance().getPropertyByPathAndKey(Constant.PROPERTITIES_PATH,"scheduling_platfrom_url");
	public final static String ORDER_DATA_URL=PropertitesFactory.getInstance().getPropertyByPathAndKey(Constant.PROPERTITIES_PATH,"order_data_url");
    public final static String START_TIME=" 00:00:00";
    public final static String END_TIME=" 23:59:59";
    public final static String REPORT_FILE_PATH=PropertitesFactory.getInstance().getPropertyByPathAndKey(Constant.PROPERTITIES_PATH,"report_file_path");
    public final static String REPORT_TITLE="注册数据分析报告";
    public static final String MAIL_TO=PropertitesFactory.getInstance().getPropertyByPathAndKey(Constant.PROPERTITIES_PATH,"mailTo");
    public static final String MAIL_CC=PropertitesFactory.getInstance().getPropertyByPathAndKey(Constant.PROPERTITIES_PATH,"mailCC");
    public static final int Batch_size=100;
    public static final String REGISTER_URL="http://www.xinnet.com/user/user.do?method=toregister";
    public static final int AREA_ID_UNKNOW=34;
    public static final int BUY_TYPE_RENEW=2;
    public static final int BUY_TYPE_OPEING_NEW=1;
    public static final int BUY_TYPE_RETURN_PREMIUM=3;
    public static final int BUY_TYPE_OTHER=0;
    public static final int GOODS_TYPE_SHOPING_CAR=1;
    public static final int GOODS_TYPE_HAVE_TO_PAY=2;
    public static final String WHERE_TIME_TYPE="{time_type}" ;
    public static enum TimeType{
    	DAY(1,"date"),HOUR(2,"time_range");
    	private String columnName;
    	private int type;

		private TimeType(int type,String columnName) {
			this.columnName = columnName;
			this.type=type;
		}
		
		

		public int getType() {
			return type;
		}



		public void setType(int type) {
			this.type = type;
		}



		public String getColumnName() {
			return columnName;
		}

		public void setColumnName(String columnName) {
			this.columnName = columnName;
		}
    	
    }
    public static enum OrderPayType{
    	BUY_TYPE_OTHER(0,"(buy_type is null or buy_type='')"),BUY_TYPE_OPEING_OF_NEW(1,"buy_type='新开'"),
    	BUY_TYPE_RENEW(2,"buy_type='续费'"),BUY_TYPE_RETURN_PREMIUM(3,"buy_type='退费'");
    	private int type;
    	private String condition;
		private OrderPayType(int type, String condition) {
			this.type = type;
			this.condition = condition;
		}
		public int getType() {
			return type;
		}
		public void setType(int type) {
			this.type = type;
		}
		public String getCondition() {
			return condition;
		}
		public void setCondition(String condition) {
			this.condition = condition;
		}
    	
    }
    public static enum OrderMatchType{
    	SUPER_GOODS(1,"t.super_goods = m.name"),GOODS_CLASS(2,"t.goods_class_name = m.name "),PRODUCT_NAME(3,"locate(m.name,t.product_name)>0 ");
    	private int type;
    	private String condition;
		private OrderMatchType(int type, String condition) {
			this.type = type;
			this.condition = condition;
		}
		public int getType() {
			return type;
		}
		public void setType(int type) {
			this.type = type;
		}
		public String getCondition() {
			return condition;
		}
		public void setCondition(String condition) {
			this.condition = condition;
		}
    	
    }
    public static enum ReportType{
    	PAY_CHANNEL_USER_INFO("投放渠道注册用户信息.csv","来源,计划,单元词,关键字,搜索词,会员号,注册时间","source,campaign,groupset,keyword,searchkw,hycode,register_date","select r.source,r.campaign,r.groupset,r.keyword,r.searchkw,r.hycode,r.register_date  from analytics_register_detail r,base_channel c where r.channel_id = c.id and c.channel_type_id =1 and r.date=? "),
    	PAY_CHANNEL_REGISTER_RANKING("投放渠道注册数量排名.csv","来源,计划,单元词,关键字,搜索词,注册量","source,campaign,groupset,keyword,searchkw,register_num","select r.source,r.campaign,r.groupset,r.keyword,r.searchkw,count(r.id) as register_num from analytics_register_detail r,base_channel c where r.channel_id = c.id and c.channel_type_id =1 and r.date=? group by r.source,r.campaign,r.groupset,r.keyword,r.searchkw order by count(r.id) desc"),
    	SOURCE_RANKING("注册来源排名.csv","来源,注册量","source,register_num","select source,count(id) as register_num from analytics_register_detail where date=? group by source order by count(id) desc"),
     	FREE_CHANNEL_USER_INFO("免费渠道注册用户信息.csv","来源,计划,单元词,关键字,搜索词,会员号,注册时间","source,campaign,groupset,keyword,searchkw,hycode,register_date","select r.source,r.campaign,r.groupset,r.keyword,r.searchkw,r.hycode,r.register_date  from analytics_register_detail r,base_channel c where r.channel_id = c.id and c.channel_type_id <>1 and r.date=? "),
    	FREE_CHANNEL_REGISTER_RANKING("免费渠道注册数量排名.csv","来源,计划,单元词,关键字,搜索词,注册量","source,campaign,groupset,keyword,searchkw,register_num","select r.source,r.campaign,r.groupset,r.keyword,r.searchkw,count(r.id) as register_num from analytics_register_detail r,base_channel c where r.channel_id = c.id and c.channel_type_id <>1 and r.date=? group by r.source,r.campaign,r.groupset,r.keyword,r.searchkw order by count(r.id) desc")
    	;
    	
    	private String csvFileName;
    	private String csvCloumnNames;
    	private String dbCloumnNames;
    	private String dbSql;
		private ReportType(String csvFileName, String csvCloumnNames,
				String dbCloumnNames, String dbSql) {
			this.csvFileName = csvFileName;
			this.csvCloumnNames = csvCloumnNames;
			this.dbCloumnNames = dbCloumnNames;
			this.dbSql = dbSql;
		}
		public String getCsvFileName() {
			return csvFileName;
		}
		public void setCsvFileName(String csvFileName) {
			this.csvFileName = csvFileName;
		}
		public String getCsvCloumnNames() {
			return csvCloumnNames;
		}
		public void setCsvCloumnNames(String csvCloumnNames) {
			this.csvCloumnNames = csvCloumnNames;
		}
		public String getDbCloumnNames() {
			return dbCloumnNames;
		}
		public void setDbCloumnNames(String dbCloumnNames) {
			this.dbCloumnNames = dbCloumnNames;
		}
		public String getDbSql() {
			return dbSql;
		}
		public void setDbSql(String dbSql) {
			this.dbSql = dbSql;
		}
    	
    }
    
    public static ArrayBlockingQueue<RunParam> ANALYSIS_QUEUE= new ArrayBlockingQueue<RunParam>(50);
     
}
