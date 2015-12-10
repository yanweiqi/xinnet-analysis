package com.xinnet.xa.analyzer.common;

public class SqlByTime {
	public final static String ANA_PAGE_VIEW="analytics_page_view_{month}";
	public final static String ANA_USER_COOKIES="analytics_user_cookies";
	public final static String ANA_PV="analytics_pv";
	public final static String ANA_HY_SOURCE="base_hy_source";
	public final static String ANA_ORDER_DETAIL="analytics_order_detail";
	public final static String ANA_ORDER_COUNT="analytics_order_count";
	public final static String ANA_ORDER_COUNT_URL="analytics_order_count_by_url_back";
	public final static String ANA_REGISTER_DETAIL="analytics_register_detail";
	public final static String ANA_REGISTER_COUNT="analytics_register_count";
	public final static String ANA_STATISTICS_DATA="analytics_statistics_data";
	public final static String ANA_PV_BY_URL="analytics_pv_by_url";
	public final static String ANA_USER_COOKIES_BY_URL="analytics_user_cookies_by_url";
	public final static String ANA_USER_COOKIES_STATE="analytics_user_cookies_state";
	public final static String ANA_USER_REGISTER_FAILED="analytics_registration_failed_detail";
	public final static String ANA_USER_REGISTER_BY_URL="analytics_register_count_by_url";
	public final static String TEMP_PAGE_VIEW="temp_page_view_day";
	public final static String TEMP_USER_COOKEID="temp_user_cookies_data";
	public final static String ANA_GOODS_DETAIL_BY_URL="analytics_user_goods_order_detail_by_url";
	public final static String ANA_SHOPCAT_COUNT_BY_URL="analytics_shopcart_count_by_url";
	
	/**
	 * common sql
	 */
	 public final static String UPDATE_URL_TYPE="update {table_name} t inner join (select i.id, i.url,i.url_detail_id  from base_url_info i where i.search_type=1) d on t.{url_column_name}=d.url "+
             "set t.url_detail_id=d.url_detail_id where t.url_detail_id is null and  t."+AnalyzerConstant.WHERE_TIME_TYPE+"=?";
	 public final static String UPDATE_URL_DATA= "update {table_name} set url_detail_id=? where {url_column_name} like ? and url_detail_id is null  and "+AnalyzerConstant.WHERE_TIME_TYPE+"=?";
	 public final static String UPDATE_OTHER_URL_TYPE="update {table_name} set url_detail_id=35  where url_detail_id is null and "+AnalyzerConstant.WHERE_TIME_TYPE+"=?";
	 
	 public final static String UPDATE_AREA_ID="update {table_name} t inner join base_area a on LOCATE(a.area_name,t.area)>0 set t.area_id=a.id where t.area_id is null and "+AnalyzerConstant.WHERE_TIME_TYPE+"=?";
     public final static String UPDATE_AREA_ID_OTHER="update {table_name}  set area_id=33 where area is not null and area not in('unknown','未分配或者内网IP') and area_id is null and "+AnalyzerConstant.WHERE_TIME_TYPE+"=?";
     public final static String UPDATE_AREA_ID_UNKNOW="update {table_name} set area_id=34 where area_id is null and "+AnalyzerConstant.WHERE_TIME_TYPE+"=?";
     
     public final static String DELETE_TABLE_BY_TIME="delete from {table_name} where "+AnalyzerConstant.WHERE_TIME_TYPE+"=?";
     
     public final static String UPDATE_DAY_DATE="update {table_name} set date=date({url_column_name}),time_range=date_format({url_column_name},'%Y%m%d%H') where date is null";
/**
 * tempSql	
 */
	public final static String INSERT_TEMP_PAGE_VIEW__DAY_DATA_BY_PAGETYPE="insert into temp_page_view_day (id,cookies_id,current_page,tenant_id,source,terminal,medium,campaign,group_set,keyword,searchkw,ip,area,action_time,js_session_id,page_num,page_type,vtime,previous_page,access_type) select Id,UID,CurrentPage,TenantID,Source,Terminal,Medium,Campaign,GroupSet,Keyword,SearchKW,IP,Area,ActionTime,JsSessionId,pageNum,PageType,VTime,PreviousPage,NewVisitor from PageView where PageType<>'V' and ActionTime between ? and ?";
	public final static String INSERT_TEMP_PAGE_VIEW__DAY_DATA_BY_VTIME="insert into temp_page_view_day (id,cookies_id,current_page,tenant_id,source,terminal,medium,campaign,group_set,keyword,searchkw,ip,area,action_time,js_session_id,page_num,page_type,vtime,previous_page,access_type) select Id,UID,CurrentPage,TenantID,Source,Terminal,Medium,Campaign,GroupSet,Keyword,SearchKW,IP,Area,ActionTime,JsSessionId,pageNum,PageType,VTime,PreviousPage,NewVisitor from PageView where PageType='V' and ActionTime between ? and ?";
	public final static String UPDATE_TEMP_PAGE_VIEW_DAY_VTIME="update temp_page_view_day t inner join (select cookies_id,js_session_id,page_num,sum(vtime) as vtime from temp_page_view_day where page_type='V' group by cookies_id,js_session_id,page_num) d on t.cookies_id=d.cookies_id and t.js_session_id=d.js_session_id and t.page_num=d.page_num set t.vtime = d.vtime where t.page_type<>'V'";
	public final static String DEL_TEMP_PAGE_VIEW_DAY_VTIME_PAGE="delete from temp_page_view_day where page_type='V'";
	public final static String FORMAT_TEMP_PAGE_VIEW__DAY="update temp_page_view_day set medium='cpc' where medium like '%cpc%'";
	
    public final static String INSERT_TEMP_USER_COOKIES_BASE= "insert into temp_user_cookies_data (id,tenant_id,cookies_id,source,medium,campaign,group_set,keyword,searchkw,area,ip,channel_id,source_type_id,date,time_range,action_time,terminal) ";
	public final static String INSERT_TEMP_USER_COOKIES_SELECT_BASE="select id,tenant_id,cookies_id,source,medium,campaign,group_set,keyword,searchkw,area,ip from ";
	public final static String INSERT_TEMP_USER_COOKIES_SUB_SELECT_BASE="select t.id,t.tenant_id,t.cookies_id,t.source,t.medium,t.campaign,t.group_set,t.keyword,t.searchkw,t.area,t.ip from ";
	public final static String INSERT_TEMP_USER_COOKIES_LEFT_OUT="left outer join temp_user_cookies_data p on t.cookies_id = p.cookies_id where p.cookies_id is null";
	
	public final static String INSERT_TEMP_USER_COOKIES_SEM=INSERT_TEMP_USER_COOKIES_BASE +"select t.id,t.tenant_id,t.cookies_id,v.source_name,t.medium,t.campaign,t.group_set,t.keyword,t.searchkw,t.area,t.ip,v.channel_id,v.source_id,t.date,t.time_range,t.action_time,t.terminal from temp_page_view_day t, view_base_config_info v where t.source =v.utm_source and t.medium=v.medium and t.tenant_id=v.tenant_id  and (v.channel_type_name='付费渠道' or v.medium='fenxiang') group by t.cookies_id order by t.action_time";
    public final static String INSERT_TEMP_USER_COOKIES_SEO=INSERT_TEMP_USER_COOKIES_BASE+" select t.id,t.tenant_id,t.cookies_id,?,?,t.campaign,t.group_set,t.keyword,t.searchkw,t.area,t.ip,?,?,t.date,t.time_range,t.action_time,t.terminal from (select id,tenant_id,cookies_id,source,medium,campaign,group_set,keyword,searchkw,area,ip,date,time_range,action_time,terminal from temp_page_view_day where source like ? group by cookies_id order by action_time) t "+INSERT_TEMP_USER_COOKIES_LEFT_OUT;
	public final static String INSERT_TEMP_USER_COOKIES_EDM=INSERT_TEMP_USER_COOKIES_BASE+" select t.id,t.tenant_id,t.cookies_id,t.source,t.medium,t.campaign,t.group_set,t.keyword,t.searchkw,t.area,t.ip,t.channel_id,t.source_type_id,t.date,t.time_range,t.action_time,t.terminal  from (select v.id,v.tenant_id,v.cookies_id,CONCAT(v.source,' / ',st.medium) as source,st.medium,v.campaign,v.group_set,v.keyword,v.searchkw,v.area,v.ip,st.channel_id,st.id as source_type_id,v.date,v.time_range,v.action_time,v.terminal  from temp_page_view_day v, base_source_type st,base_channel c where v.medium=st.medium and v.tenant_id=st.tenant_id and c.id =st.channel_id and c.name='EDM' group by v.cookies_id order by v.action_time) t "+INSERT_TEMP_USER_COOKIES_LEFT_OUT;
	public final static String INSERT_TEMP_USER_COOKIES_DIRECT=INSERT_TEMP_USER_COOKIES_BASE+" select t.id,t.tenant_id,t.cookies_id,t.source,t.medium,t.campaign,t.group_set,t.keyword,t.searchkw,t.area,t.ip,t.channel_id,t.source_type_id,t.date,t.time_range,t.action_time,t.terminal  from (select v.id,v.tenant_id,v.cookies_id,st.name as source,st.medium,v.campaign,v.group_set,v.keyword,v.searchkw,v.area,v.ip,st.channel_id,st.id as source_type_id,v.date,v.time_range,v.action_time,v.terminal from temp_page_view_day v, base_source_type st,base_channel c where v.source=st.source  and v.tenant_id=st.tenant_id and c.id =st.channel_id and v.source='DIRECT' group by v.cookies_id order by v.action_time) t "+INSERT_TEMP_USER_COOKIES_LEFT_OUT;
	public final static String INSERT_TEMP_USER_COOKIES_CPS=INSERT_TEMP_USER_COOKIES_BASE+" select t.id,t.tenant_id,t.cookies_id,t.source,t.medium,t.campaign,t.group_set,t.keyword,t.searchkw,t.area,t.ip,t.channel_id,t.source_type_id,t.date,t.time_range,t.action_time,t.terminal  from (select v.id,v.tenant_id,v.cookies_id,st.name as source,st.medium,v.campaign,v.group_set,v.keyword,v.searchkw,v.area,v.ip,st.channel_id,st.id as source_type_id,v.date,v.time_range,v.action_time,v.terminal from temp_page_view_day v, base_source_type st,base_channel c where v.source=st.source and v.tenant_id=st.tenant_id  and c.id =st.channel_id and c.name='CPS' group by v.cookies_id order by v.action_time) t "+INSERT_TEMP_USER_COOKIES_LEFT_OUT;
	public final static String INSERT_TEMP_USER_COOKIES_REFERRAL=INSERT_TEMP_USER_COOKIES_BASE+" select t.id,t.tenant_id,t.cookies_id,t.source,t.medium,t.campaign,t.group_set,t.keyword,t.searchkw,t.area,t.ip,t.channel_id,t.source_type_id,t.date,t.time_range,t.action_time,t.terminal  from (select v.id,v.tenant_id,v.cookies_id,CONCAT(v.source,' / ',st.medium) as source,st.medium,v.campaign,v.group_set,v.keyword,v.searchkw,v.area,v.ip,st.channel_id,st.id as source_type_id,v.date,v.time_range,v.action_time,v.terminal from temp_page_view_day v, base_source_type st,base_channel c,base_tenant_info i where i.id=v.tenant_id and v.tenant_id=st.tenant_id and c.id =st.channel_id and c.name='引荐' and v.source is not null and v.source<>'unknow' and v.source<>'DIRECT' and LOCATE(i.domain,v.source)=0  group by v.cookies_id order by v.action_time) t "+INSERT_TEMP_USER_COOKIES_LEFT_OUT;
	public final static String INSERT_TEMP_USER_COOKIES_NA=INSERT_TEMP_USER_COOKIES_BASE+" select t.id,t.tenant_id,t.cookies_id,t.source,t.medium,t.campaign,t.group_set,t.keyword,t.searchkw,t.area,t.ip,t.channel_id,t.source_type_id,t.date,t.time_range,t.action_time,t.terminal  from (select v.id,v.tenant_id,v.cookies_id,'(direct) / (na)' as source,'(na)' as medium,v.campaign,v.group_set,v.keyword,v.searchkw,v.area,v.ip,7 as channel_id,19 as source_type_id,v.date,v.time_range,v.action_time,v.terminal from temp_page_view_day v   group by v.cookies_id order by v.action_time) t "+INSERT_TEMP_USER_COOKIES_LEFT_OUT;
	
	public final static String UPDATE_TEMP_USER_COOKIES_BY_BEFOR_HOUR= "update temp_user_cookies_data t inner join analytics_user_cookies a on t.cookies_id=a.cookies_id set t.source=a.source,t.medium=a.medium,t.campaign=a.campaign,t.group_set=a.group_set,t.keyword=a.keyword,t.searchkw=a.searchkw,t.channel_id=a.channel_id,t.product_line_id=a.product_line_id,t.source_type_id =a.source_type_id,t.area_id=a.area_id,t.access_type=a.access_type where a.time_range=?";
	 
	 public final static String UPDATE_TEMP_USER_COOKIES_SEM_EDM_PRODUCT_LINE= "update temp_user_cookies_data t inner join base_sem_edm_campaign d on t.channel_id =d.channel_id and t.campaign=d.campaign_name set t.product_line_id=d.product_line_id";
	 public final static String UPDATE_TEMP_USER_COOKIES_PRODUCT_LINE_OTHER = "update temp_user_cookies_data t inner join base_product_line p set t.product_line_id=p.id where t.product_line_id is null and p.product_line_name='无法识别'";
	 public final static String SELECT_TEMP_SOURCE_SEARCH_KEYWORDS = "select id,searchkw from temp_user_cookies_data where searchkw is not null and product_line_id is null and medium in('organic','email')";
	 public final static String UPDATE_TEMP_USER_COOKIES_ORGANIC_PRODUCT_LINE = "update temp_user_cookies_data set product_line_id=? where id=?";
	 
	 public final static String UPDATE_TEMP_USER_COOKIES_ACCESS_TYPE_BY_TEMP_PAGE_VIEW="update temp_user_cookies_data t inner join (select cookies_id from temp_page_view_day where access_type=1 group by cookies_id) p on t.cookies_id=p.cookies_id set access_type=1";
	 public final static String UPDATE_TEMP_USER_COOKIES_ACCESS_TYPE_BY_ANALYTIS_USER_COOKIES="update temp_user_cookies_data t inner join analytics_user_cookies c on t.cookies_id = c.cookies_id set t.access_type=1 where c.access_type=1 and c.date=?";
     public final static String UPDATE_TEMP_USER_COOKIES_ACCESS_TYPE="update temp_user_cookies_data t inner join base_ip_filter f on t.ip=f.ip set access_type=3";
     public final static String UPDATE_TEMP_USER_COOKIES_ACCESS_TYPE_BY_TERMINAL="update temp_user_cookies_data set access_type=3 where terminal like '%JianKongBao%' ";
     public final static String UPDATE_TEMP_DAY_CHANNEL="update temp_page_view_day t inner join temp_user_cookies_data d on t.cookies_id=d.cookies_id set t.channel_id=d.channel_id,t.product_line_id=d.product_line_id,t.source=d.source,t.source_type_id=d.source_type_id,t.area_id=d.area_id,t.access_type=d.access_type,t.campaign=d.campaign,t.keyword=d.keyword";
     public final static String UPDATE_TEMP_DAY_ABNORMAL_DATA="update temp_page_view_day set channel_id=7,product_line_id=10,source_type_id=19 where channel_id is null";
     public final static String UPDATE_TEMP_SOURCE_SEO_KEYWORD="update temp_user_cookies_data set keyword=searchkw where channel_id in (5,6)";
     
     public final static String SELECT_URL_INFO_FUZZY_SEARCH_DATA="select url,url_detail_id,priority from base_url_info where search_type=2";
     
     public final static String INSERT_TEMP_USER_COOKIES_FROM_UV="insert temp_user_cookies_data(id,cookies_id,source,medium,campaign,group_set,keyword,searchkw,channel_id,product_line_id,source_type_id)" +
             "select u.id,u.cookies_id,u.source,u.medium,u.campaign,u.group_set,u.keyword,u.searchkw,u.channel_id,u.product_line_id,u.source_type_id  from analytics_user_cookies u,base_channel c,transfromdata t  where t.UID=u.cookies_id and c.id=u.channel_id and t.PageType='T' and t.ActionTime BETWEEN ? and ? and u.date between ? and ? group by u.cookies_id order by c.channel_type_id,u.action_time";
     
     /**
	  * analytics_page_view
	  */
	 public final static String INSERT_ANALYTICS_PAGE_VIEW ="insert into analytics_page_view_{month}(id,tenant_id,cookies_id,current_page,previous_page,source,terminal,medium,campaign,group_set,keyword,searchkw,ip,area,action_time,channel_id,product_line_id,url_detail_id,source_type_id,date,time_range,js_session_id,vtime,page_num,access_type,page_type,area_id)"
	                                           +" select id,tenant_id,cookies_id,current_page,previous_page,source,terminal,medium,campaign,group_set,keyword,searchkw,ip,area,action_time,channel_id,product_line_id,url_detail_id,source_type_id,date,time_range,js_session_id,vtime,page_num,access_type,page_type,area_id from temp_page_view_day where "+AnalyzerConstant.WHERE_TIME_TYPE+"=?";
	 public final static String CREATE_ANA_PAGE_VIEW="create table if not exists analytics_page_view_{month} like analytics_page_view";
	 /**
	  * analytics_user_cookies
	  */
	 public final static String INSERT_UV_CHANNEL_AND_PRODUCT_LINE="insert into analytics_user_cookies(tenant_id,cookies_id,source,medium,campaign,group_set,keyword,searchkw,area,ip,channel_id,source_type_id,product_line_id,date,time_range,action_time,area_id,access_type,terminal) "+
				" select tenant_id,cookies_id,source,medium,campaign,group_set,keyword,searchkw,area,ip,channel_id,source_type_id,product_line_id,date,time_range,action_time,area_id,access_type,terminal from temp_user_cookies_data where "+AnalyzerConstant.WHERE_TIME_TYPE+"=?";
	 public final static String SELECT_MAX_TIME = "SELECT MAX({column_name}) from analytics_user_cookies";
	 
	 /**
	  * analytics_pv
	  */
	 
	 public final static String INSERT_PV_CHANNEL_AND_PRODUCT_LINE="insert into analytics_pv(tenant_id,channel_id,product_line_id,campaign,group_set,keyword,date,time_range,area_id,access_type,pv) select tenant_id,channel_id,product_line_id,campaign,group_set,keyword,date,time_range,area_id,access_type,count(id)  from temp_page_view_day where "+AnalyzerConstant.WHERE_TIME_TYPE+"=? group by tenant_id,channel_id,product_line_id,campaign,group_set,keyword,date,time_range,area_id,access_type";
	 
	 /**
	  * analytics_pv_by_url
	  */
	 public final static String INSERT_PV_BY_URL = "insert into analytics_pv_by_url(channel_id,campaign,group_set,keyword,url_detail_id,time_range,date,area_id,access_type,pv,residence_time) "+
	                                         "select channel_id,campaign,group_set,keyword,url_detail_id,time_range,date,area_id,access_type,count(id),sum(vtime) from temp_page_view_day where "+AnalyzerConstant.WHERE_TIME_TYPE+"=? group by channel_id,campaign,group_set,keyword,url_detail_id,time_range,date,area_id,access_type";
	 
	 /**
	  * analytics_user_cookies_by_url
	  */
	 
	 public final static String INSERT_UV_BY_URL="insert into analytics_user_cookies_by_url(cookies_id,url_detail_id,channel_id,source_type_id,date,area,ip,time_range,action_time,tenant_id,campaign,group_set,keyword,searchkw,terminal,area_id,access_type,min_page_num,visits_page_number)"+
	                                       "select cookies_id,url_detail_id,channel_id,source_type_id,date,area,ip,time_range,action_time,tenant_id,campaign,group_set,keyword,searchkw,terminal,area_id,access_type,MIN(page_num),count(id) from temp_page_view_day where "+AnalyzerConstant.WHERE_TIME_TYPE+"=? group by cookies_id,url_detail_id,channel_id,source_type_id,time_range,tenant_id,campaign,group_set,keyword,searchkw,area_id,access_type";
	 
	 public final static String UPDATE_UV_BY_URL_PAGE_TYPE="update analytics_user_cookies_by_url t INNER JOIN(SELECT cookies_id,min(min_page_num) as min_page_num FROM analytics_user_cookies_by_url WHERE date=? group by cookies_id) u on t.cookies_id =u.cookies_id and t.min_page_num=u.min_page_num set t.page_type=1 where t."+AnalyzerConstant.WHERE_TIME_TYPE+"=?";
	 
	 public final static String SELECT_VISITS_NUM= "select t.cookies_id,t.url_detail_id,t.channel_id,t.time_range,count(t.js_session_id) as access_count from (select cookies_id,url_detail_id,channel_id,time_range,js_session_id from temp_page_view_day where  "+AnalyzerConstant.WHERE_TIME_TYPE+"=? group by  cookies_id, url_detail_id,channel_id,time_range,js_session_id) t group by t.cookies_id,t.url_detail_id,t.channel_id,t.time_range";
	 public final static String UPDATE_UV_BY_URL_VISITS_NUMBER="update analytics_user_cookies_by_url c inner join ("+SELECT_VISITS_NUM+") d"+
	                                       " on c.cookies_id=d.cookies_id and c.url_detail_id=d.url_detail_id and c.channel_id= d.channel_id and c.time_range=d.time_range set c.visits_number=d.access_count where c."+AnalyzerConstant.WHERE_TIME_TYPE+"=?";
	 
	 public final static String SELECT_OUT_SESSION_ID="(select js_session_id  from temp_page_view_day group by js_session_id having count(id)=1)";
	 public final static String UPDATE_UV_BY_URL_OUT_NUMBER="update analytics_user_cookies_by_url c inner join (select t.cookies_id,t.url_detail_id,t.channel_id,t.time_range,count(t.js_session_id) as access_count from temp_page_view_day t where EXISTS (select 1 from "+SELECT_OUT_SESSION_ID+" v where t.js_session_id=v.js_session_id ) group by t.cookies_id,t.url_detail_id,t.channel_id,t.time_range) d"+
                                              " on c.cookies_id=d.cookies_id and c.url_detail_id=d.url_detail_id and c.channel_id= d.channel_id and c.time_range=d.time_range set c.jump_number=d.access_count where c."+AnalyzerConstant.WHERE_TIME_TYPE+"=?";
	 
	 /**
	  * analytics_user_cookies_state
	  */
	 public final static String INSERT_TEMP_BY_ANALYSIS_PAGE_VIEW="insert into temp_page_view_day (id,cookies_id,vtime,js_session_id,date,access_type) select id,cookies_id,vtime,js_session_id,date,access_type from analytics_page_view_{month} where date=?";
	 public final static String INSERT_COOKIES_STATE_VTIME_AND_PAGE_NUM="insert into analytics_user_cookies_state (cookies_id,access_type,date,residence_time,visits_page_number) select cookies_id,access_type,date,sum(vtime),count(id) from temp_page_view_day group by cookies_id,access_type";
	 public final static String UPDATE_COOKIES_STATE_ACCESS_COUNT="update analytics_user_cookies_state s inner join (select t.cookies_id,count(t.js_session_id) as access_count from (select cookies_id,js_session_id from temp_page_view_day group by cookies_id,js_session_id) t group by t.cookies_id) d on s.cookies_id=d.cookies_id  set s.visits_number=d.access_count where s.date=?";
	 
     
	 /**base_hy_source
	  * 
	  */
	 public final static String INSET_HY_SOURCE = "insert into base_hy_source (hycode,source,terminal,medium,campaign,group_set,keyword,searchkw,ip,area,action_time,transform_id,channel_id,product_line_id,source_type_id,tenant_id,date,time_range)"
				+ " select d.Data0,d.source,d.Terminal,d.medium,d.campaign,d.group_set,d.keyword,d.searchkw,d.Ip,d.Area,d.ActionTime,d.id,d.channel_id,d.product_line_id,d.source_type_id,d.TenantID,date(d.ActionTime),date_format(d.ActionTime,'%Y%m%d%H') from base_hy_source b right outer join "
				+ "(select t.Data0,p.source,t.Terminal,p.medium,p.campaign,p.group_set,p.keyword,p.searchkw,t.Ip,t.Area,f.id,t.ActionTime ,p.channel_id,p.product_line_id,p.source_type_id,t.TenantID  from transfromdata t ,temp_user_cookies_data p,base_transform_type f where t.UID=p.cookies_id and t.PageType = f.name and t.ActionTime between ? and ? group by t.Data0 order by t.ActionTime) d on b.hycode = d.Data0 where b.hycode is null";
	 
	 public final static String UPDATE_HY_DATA_PRODUCT_LINE_BY_ORDER = "update base_hy_source t inner join  analytics_order_detail d "
				+ "on t.hycode = d.hycode and t.date=d.date set t.product_line_id = d.product_line_id where t.product_line_id is null or t.product_line_id =10";
	 
	 public final static String UPDATE_HY_DATA_PRODUCT_LINE_OTHER="update base_hy_source t set t.product_line_id=10 where product_line_id is null";
	 
	 /**
	  * searchKeyword sql
	  */
	 
	 public final static String SELECT_SEO_SEARCH_KEYWORDS = "select name,product_line_id from base_search_keyword";
	 
	 /**
	  * base_source_type
	  */
	 public final static String SELECT_SEO_CHANNEL_SOURCE_TYPE="select st.id,st.name,st.source,st.medium,st.channel_id from base_source_type st, base_channel c where c.id = st.channel_id and c.name='SEO'";
	 
	 /**
	  * analytics_register
	  */
		public final static String INSERT_REGISTER_DETAIL="insert into analytics_register_detail(channel_id,product_line_id,source_type_id,campaign,groupset,keyword,searchkw,hycode,register_date,area,ip,tenant_id,time_range,date,source,area_id) "+
                "select channel_id,product_line_id,source_type_id,campaign,group_set,keyword,searchkw,hycode,action_time,area,ip,tenant_id,time_range,date,source,area_id from base_hy_source where transform_id=1 and "+AnalyzerConstant.WHERE_TIME_TYPE+"=?";
		public final static String UPDATE_REGISTER_DETAIL_ACCESS_TYPE="update analytics_register_detail r inner join analytics_user_cookies u on r.cookies_id=u.cookies_id and r.date=u.date set r.access_type=u.access_type where r."+AnalyzerConstant.WHERE_TIME_TYPE+"=?";
		public final static String UPDATE_REGISTER_DETAIL_COOKIES_ID= "update analytics_register_detail d inner join  (select UID as cookies_id,Data0 as hycode,date(ActionTime) as date,if(LOCATE('shopcart',CurrentPage)=0,1,2) as regist_type,JsSessionId,pageNum from transfromdata where PageType='T' and ActionTime between ? and ?) t on d.hycode=t.hycode and d.date=t.date set d.cookies_id=t.cookies_id ,d.regist_type=t.regist_type,d.js_session_id=t.JsSessionId,d.page_num=t.pageNum";
		public final static String UPDATE_REGISTER_DETAIL_URIL_INFO_ID="update analytics_register_detail d inner join temp_page_view_day t on d.cookies_id=t.cookies_id and d.date=t.date and d.page_num-2=t.page_num set d.url_detail_id=t.url_detail_id,d.request_url=t.current_page where d."+AnalyzerConstant.WHERE_TIME_TYPE+"=?";
		public final static String INSERT_REGISTER_FAIL="insert into analytics_registration_failed_detail(cookies_id,channel_id,area_id,campaign,keyword,js_session_id,page_num,date,time_range,access_type) select t.cookies_id, t.channel_id,t.area_id,t.campaign,t.keyword,t.js_session_id,t.page_num-1,t.date,t.time_range,t.access_type from temp_page_view_day t where t.current_page='"+AnalyzerConstant.REGISTER_URL+"' and "+AnalyzerConstant.WHERE_TIME_TYPE+"=? and not EXISTS(select t.id from temp_page_view_day d where t.cookies_id=d.cookies_id and page_type ='T')";
		public final static String UPDATE_REGISTER_FAILE_URL_INFO_ID="update analytics_registration_failed_detail t inner join temp_page_view_day d on t.cookies_id=d.cookies_id and t.page_num=d.page_num set t.url_detail_id=d.url_detail_id,t.request_url=d.current_page where t."+AnalyzerConstant.WHERE_TIME_TYPE+"=?";
		public final static String SELECT_LANDING_PAGE="SELECT id,cookies_id,MIN(page_num),url_detail_id from analytics_page_view_{month} where date=? group by  cookies_id";
		public final static String UPDATE_REGISTR_DETAIL_LANDING_PAGE_ID="update analytics_register_detail t inner join temp_page_view_day d on t.cookies_id=d.cookies_id set t.url_landing_page_detail_id=d.url_detail_id where t."+AnalyzerConstant.WHERE_TIME_TYPE+"=?";
		public final static String INSERT_INTO_TEMP_MIN_URL_DELTAIL_ID="insert into temp_page_view_day(id,cookies_id,page_num,url_detail_id) "+SELECT_LANDING_PAGE;
	//	public final static String UPDATE_REGISTER_FAIL_ACCESS_TYPE="update analytics_registration_failed_detail r inner join analytics_user_cookies u on r.cookies_id=u.cookies_id and r.date=u.date set r.access_type=u.access_type where r.date=?";
		
		public final static String UPDATE_REGISTER_DEATIL_OTHER_URL_INFO_ID="update analytics_register_detail set url_detail_id=36 where url_detail_id is null and "+AnalyzerConstant.WHERE_TIME_TYPE+"=?";
		
		public final static String INSERT_REGISTER_COUNT="insert into analytics_register_count(channel_id,product_line_id,area_id,campaign,group_set,keyword,time_range,date,number) select channel_id,product_line_id,area_id,campaign,groupset,keyword,time_range,date,count(id) from analytics_register_detail where "+AnalyzerConstant.WHERE_TIME_TYPE+"=? group by channel_id,product_line_id,area_id,campaign,groupset,keyword,time_range,date";
		
		public final static String INSERT_REGISTER_COUNT_BY_URL_SUCCESS="insert into analytics_register_count_by_url(channel_id,campaign,keyword,area_id,url_detail_id,regist_type,date,time_range,number,url_type) select channel_id,campaign,keyword,area_id,url_detail_id,regist_type,date,time_range,count(id),1 from analytics_register_detail where "+AnalyzerConstant.WHERE_TIME_TYPE+"=? group by channel_id,campaign,keyword,area_id,url_detail_id,regist_type,date,time_range";
		public final static String INSERT_REGISTER_COUNT_BY_URL_FAILED="insert into analytics_register_count_by_url(channel_id,campaign,keyword,area_id,url_detail_id,regist_type,date,time_range,number,url_type) select channel_id,campaign,keyword,area_id,url_detail_id,3,date,time_range,count(id),1 from analytics_registration_failed_detail where "+AnalyzerConstant.WHERE_TIME_TYPE+"=? group by channel_id,campaign,keyword,area_id,url_detail_id,date,time_range";
		public final static String INSERT_REGISTER_COUNT_BY_URL_LANDING_PAGE="insert into analytics_register_count_by_url(channel_id,campaign,keyword,area_id,url_detail_id,regist_type,date,time_range,number,url_type) select channel_id,campaign,keyword,area_id,url_landing_page_detail_id,regist_type,date,time_range,count(id),2 from analytics_register_detail where "+AnalyzerConstant.WHERE_TIME_TYPE+"=? group by channel_id,campaign,keyword,area_id,url_landing_page_detail_id,regist_type,date,time_range";
		 /**
		  * analytics_order
		  */
		 public final static String UPDATE_ORDER_DETAIL_MATCH_BASE="update analytics_order_detail t inner join (select name,product_line_id,url_detail_id  from base_order_goods_match where match_type=${type}) m on ${condition} ";
		 public final static String UPDATE_ORDER_DETAIL_SPECIAL_DATE_PRODUCT_LINE= " set t.product_line_id=m.product_line_id where t.product_line_id=10 and t."+AnalyzerConstant.WHERE_TIME_TYPE+"=?";
		 public final static String UPDATE_ORDER_DETAIL_URL_INFO_ID= " set t.url_detail_id=m.url_detail_id where t.url_detail_id is null and t."+AnalyzerConstant.WHERE_TIME_TYPE+"=?";
		 
		 public final static String UPDATE_ORDER_DETAIL_DATE="update analytics_order_detail set date=date(operate_time),time_range=date_format(operate_time,'%Y%m%d%H') where operate_time between ? and ?";
		 
		 public final static String UPDATE_ORDER_DETAIL_AREA_ID_BY_HY_SOURCE="update analytics_order_detail t inner join base_hy_source h on t.hycode=h.hycode set t.area_id=h.area_id where t.area_id is null and t."+AnalyzerConstant.WHERE_TIME_TYPE+"=?";
		 public final static String UPDATE_ORDER_DETAIL_AREA_ID_UNKOWN="update analytics_order_detail set area_id ="+AnalyzerConstant.AREA_ID_UNKNOW+" where area_id is null and "+AnalyzerConstant.WHERE_TIME_TYPE+"=?";
		 
	 	 
		 public final static String UPDATE_ORDER_DETAIL_BY_OLD="UPDATE analytics_order_detail o INNER JOIN (SELECT hycode," +
				 " super_goods, buy_type, is_refund,product_line_id,url_detail_id FROM analytics_order_detail t WHERE " +
				  " t.product_name NOT IN ('快递费','补扣费','退费手续费') GROUP BY t.hycode ORDER BY t.operate_time DESC) p ON o.hycode = p.hycode" +
		         " SET o.product_line_id = p.product_line_id,o.url_detail_id = p.url_detail_id" +
				 " WHERE o.product_name IN ('快递费','补扣费','退费手续费') and "+AnalyzerConstant.WHERE_TIME_TYPE+"=?";
		  
		 public final static String UPDATE_ORDER_DETAIL_OTHER_URL_DETAIL_ID="update analytics_order_detail set url_detail_id=36 where url_detail_id is null and "+AnalyzerConstant.WHERE_TIME_TYPE+"=?";
		 public final static String UPDATE_ORDER_DETAIL_URL_DETAIL_ID_BY_SHOPCAR_URL="UPDATE analytics_order_detail t INNER JOIN (select g.goods_order_code,u.url_detail_id from analytics_user_goods_order_detail_by_url u,analytics_user_goods_order_detail g where u.goods_order_detail_id=g.id and g.shopcart_settle_datetime BETWEEN ? and ?) p "+
		              "on t.order_code = p.goods_order_code set t.url_detail_id=p.url_detail_id where t."+AnalyzerConstant.WHERE_TIME_TYPE+"=?";
		 
		 public final static String UPDATE_ORDER_DETAIL_BUY_TYPE_RENEW="update analytics_order_detail set buy_type='续费' where LOCATE('续费',product_name)>0 and (buy_type is null or buy_type='') and "+AnalyzerConstant.WHERE_TIME_TYPE+"=?";
		 public final static String UPDATE_ORDER_DETAIL_BUY_TYPE_RETURN_PREMIUM="update analytics_order_detail set buy_type='退费' where LOCATE('退费',product_name)>0 and (buy_type is null or buy_type='') and "+AnalyzerConstant.WHERE_TIME_TYPE+"=?";
		 public final static String UPDATE_ORDER_DETAIL_BUY_TYPE_POUNDAGE="update analytics_order_detail set buy_type='新开' where LOCATE('手续费',product_name)>0 and (buy_type is null or buy_type='') and "+AnalyzerConstant.WHERE_TIME_TYPE+"=?";
		 public final static String UPDATE_ORDER_DETAIL_BUY_TYPE_OPEING_OF_NEW="update analytics_order_detail set buy_type='新开' where product_name not in('快递费','补扣费','退费手续费') and (buy_type is null or buy_type='') and "+AnalyzerConstant.WHERE_TIME_TYPE+"=?";
		 
		 public final static String UPDATE_ORDER_DETAIL_PAY_TYPE="update analytics_order_detail set pay_type=${type} where ${condition} and "+AnalyzerConstant.WHERE_TIME_TYPE+"=?";
		 
		 public final static String UPDATE_ORDER_COUNT_BY_URL="insert into analytics_order_count_by_url_back(channel_id,campaign,keyword,area_id,url_detail_id,time_range,date,pay_type,number,money,url_type) "
				                             +"select channel_id,campaign,keyword,area_id,url_detail_id,time_range,date,pay_type,count(id),sum(total_money),1 from analytics_order_detail where "+AnalyzerConstant.WHERE_TIME_TYPE+"=? group by channel_id,campaign,keyword,area_id,url_detail_id,time_range,pay_type";
		 public final static String UPDATE_ORDER_COUNT_BY_URL_BY_LANDING_PAGE="insert into analytics_order_count_by_url_back(channel_id,campaign,keyword,area_id,url_detail_id,time_range,date,pay_type,number,money,url_type) "
                 +"select channel_id,campaign,keyword,area_id,url_landing_page_detail_id,time_range,date,pay_type,count(id),sum(total_money),2 from analytics_order_detail where "+AnalyzerConstant.WHERE_TIME_TYPE+"=? group by channel_id,campaign,keyword,area_id,url_landing_page_detail_id,time_range,pay_type";
		 
		 public final static String UPDATE_ORDER_DETAIL_PRODUCT_LINE_BY_GA_SORUCE="update analytics_order_detail a inner join base_ga_source g on a.hycode=g.hycode "+
	                               "set a.channel_id=g.channel_id,a.product_line_id=g.product_line_id, a.campaign = g.campaign , a.keyword = g.keyword,a.source=g.source,a.source_type_id=g.source_type_id where a."+AnalyzerConstant.WHERE_TIME_TYPE+"=?";
		 public final static String UPDATE_ORDER_DETAIL_PRODUCT_LINE_BY_XA_SORUCE="update analytics_order_detail o inner join base_hy_source h on o.hycode=h.hycode "+
					"set o.channel_id=h.channel_id,o.product_line_id=h.product_line_id, o.campaign = h.campaign ,o.group_set=h.group_set, o.keyword = h.searchkw ,o.source=h.source,o.source_type_id=h.source_type_id where o.channel_id is null and o."+AnalyzerConstant.WHERE_TIME_TYPE+"=?";
		 public final static String UPDATE_ORDER_DETAIL_CHANNEL_NULL="update analytics_order_detail set channel_id =10,source='#N/A',source_type_id=11 where channel_id is null and "+AnalyzerConstant.WHERE_TIME_TYPE+"=?";
		 public final static String UPDATE_ORDER_PRODUCT_LINE_NULL="update analytics_order_detail set product_line_id=10 where product_line_id is null and "+AnalyzerConstant.WHERE_TIME_TYPE+"=?";
		 public final static String INSERT_ORDER_COUNT="insert into analytics_order_count (channel_id,product_line_id,campaign,group_set,keyword,area_id,time_range,date,number,money) select channel_id,product_line_id,campaign,group_set,keyword,area_id,time_range,date,count(id),sum(total_money) from analytics_order_detail where "+AnalyzerConstant.WHERE_TIME_TYPE+"=? group by channel_id,product_line_id,campaign,group_set,keyword,area_id,time_range,date";
		 public final static String INSERT_ORDER_DETAIL="insert into analytics_order_detail(tenant_id,operate_time,hycode,order_code,total_money,product_name,super_goods,buy_type,is_refund,goods_class_name) value ('110',?,?,?,?,?,?,?,?,?)";
		 public final static String UPDATE_ORDER_DETAIL_COOKIES_ID="UPDATE analytics_order_detail o INNER JOIN (SELECT UID,Data0 AS hycode FROM transfromdata WHERE ActionTime BETWEEN ? and ? GROUP BY Data0 ORDER BY ActionTime ) t ON t.hycode = o.hycode SET  o.cookies_id = t.UID WHERE o."+AnalyzerConstant.WHERE_TIME_TYPE+"=?";
		 public final static String UPDATE_ORDER_DETAIL_LANDING_PAGE_URL_DETAIL_ID="update analytics_order_detail t inner join temp_page_view_day d on t.cookies_id=d.cookies_id set t.url_landing_page_detail_id=d.url_detail_id where t."+AnalyzerConstant.WHERE_TIME_TYPE+"=?";
		 /**
		  * analytics_user_goods_order_detail_by_url
		  */
		 
//		 public final static String INSERT_GOODS_ORDER_DETAIL_BY_URL="INSERT into analytics_user_goods_order_detail_by_url (goods_order_detail_id,channel_id,campaign,keyword,area_id,request_url,date,time_range,url_detail_id) "+
//		                                                    "select g.id,p.channel_id,p.campaign,p.keyword,p.area_id,g.add_shopcart_current_url,p.date,date_format(g.add_shopcart_datetime,'%Y%m%d%H'),p.url_detail_id  from analytics_user_goods_order_detail g,"+
//				                                            "(select cookies_id,current_page,channel_id,campaign,keyword,area_id,url_detail_id,date from analytics_page_view  where date=? GROUP BY cookies_id,current_page) p "+
//		                                                    "where g.user_cookie_id=p.cookies_id and g.add_shopcart_current_url = p.current_page and g.add_shopcart_datetime BETWEEN ? and ?";
		 
		 public final static String INSERT_GOODS_ORDER_DETAIL_BY_URL="INSERT into analytics_user_goods_order_detail_by_url (goods_order_detail_id,channel_id,campaign,keyword,area_id,request_url,date,time_range) "+
	             "select g.id,p.channel_id,p.campaign,p.keyword,p.area_id,g.add_shopcart_current_url,p.date,date_format(g.add_shopcart_datetime,'%Y%m%d%H') from analytics_user_goods_order_detail g,"+
	             "(select cookies_id,channel_id,campaign,keyword,area_id,date from analytics_user_cookies  where "+AnalyzerConstant.WHERE_TIME_TYPE+"=? GROUP BY cookies_id) p "+
	             "where g.user_cookie_id=p.cookies_id  and g.add_shopcart_datetime BETWEEN ? and ?";
		 
		 public final static String INSERT_GOODS_ORDER_COUNT_SHOPING_CAR="INSERT into analytics_shopcart_count_by_url(channel_id,campaign,keyword,area_id,url_detail_id,time_range,date,number,goods_type) "+
		                                             "SELECT channel_id,campaign,keyword,area_id,url_detail_id,time_range,date,count(id),"+AnalyzerConstant.GOODS_TYPE_SHOPING_CAR+" FROM analytics_user_goods_order_detail_by_url where "+AnalyzerConstant.WHERE_TIME_TYPE+"=? GROUP BY channel_id,campaign,keyword,area_id,url_detail_id,time_range,date";
		 public final static String INSERT_GOODS_ORDER_COUNT_HAVE_TO_PAY="INSERT into analytics_shopcart_count_by_url(channel_id,campaign,keyword,area_id,url_detail_id,time_range,date,number,goods_type) "+
		                                              "select p.channel_id,p.campaign,p.keyword,p.area_id,p.url_detail_id,p.time_range,p.date,count(p.id),"+AnalyzerConstant.GOODS_TYPE_HAVE_TO_PAY+" from (select u.id,u.channel_id,u.campaign,u.keyword,u.area_id,u.url_detail_id,date_format(g.order_success_pay_datetime,'%Y%m%d%H') as time_range,date(g.order_success_pay_datetime) as date "+
				                                      "from analytics_user_goods_order_detail g,analytics_user_goods_order_detail_by_url u where g.id=u.goods_order_detail_id and g.order_success_pay_datetime BETWEEN ? and ?) p GROUP BY p.channel_id,p.campaign,p.keyword,p.area_id,p.url_detail_id,p.time_range,p.date";
		 
		 /**
		  * share backflow
		  */
		 public final static String SHARE_WHERE=" c.name in('微信','微博') ";
		 //public final static String SHARE_WHERE=" c.name in('360','搜狗') ";
		 public final static String FIND_SHARE_TOOL_UV="select  u.cookies_id,c.name,u.campaign,u.group_set,u.keyword,u.area,u.ip,u.action_time  from analytics_user_cookies u,base_channel c where c.id = u.channel_id and "+SHARE_WHERE+" and u.action_time between ? and ? group by u.cookies_id,c.name,u.campaign,u.group_set,u.keyword";
		 public final static String FIND_SHARE_REGISTER="select c.name,u.campaign,u.groupset,u.keyword,u.hycode,u.register_date from analytics_register_detail u,base_channel c where c.id = u.channel_id and "+SHARE_WHERE+" and u.register_date between ? and ?";
		 public final static String FIND_SHARE_ORDER="select c.name,u.campaign,u.group_set,u.keyword,u.order_code,u.operate_time from analytics_order_detail u,base_channel c where c.id = u.channel_id and "+SHARE_WHERE+" and u.operate_time between ? and ?";
		 

}
