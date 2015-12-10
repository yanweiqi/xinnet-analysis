package com.xinnet.xa.core;

import com.xinnet.xa.core.utils.properties.PropertitesFactory;

public class Constant {
	public final static String PROPERTITIES_PATH   = "/config.properties";
    public final static String PORT_OBJECT_NAME    = "http-bio-";
	public static final String XA_COMP_RECV        = "receive";
	public static final String XA_COMP_CTRL        = "controller";
	public static final String XA_COMP_CLCT        = "collector";
	public static final String XA_COMP_ANLT        = "analyzer";
	public static final String COMP_TYPE           = PropertitesFactory.getInstance().getPropertyByPathAndKey(PROPERTITIES_PATH,"compType");
	public static final String CLCT_URL            = PropertitesFactory.getInstance().getPropertyByPathAndKey(PROPERTITIES_PATH,"clctUrl");
	public static final String CLCT_SEND_EXCEPTION_URL = PropertitesFactory.getInstance().getPropertyByPathAndKey(PROPERTITIES_PATH,"clctSendExceptionUrl");
	public static final String HTTP_REP_OK             = "success";
	public static final String HTTP_REP_FAIL           = "fail";
	public static  int PORT	=	0;
	public static String IP	=	"";
	public final static int COMP_STATE_OK	 =	0;
	public final static int COMP_STATE_FALIL = 1;
	public final static Boolean IS_REGISTER  = Boolean.valueOf(PropertitesFactory.getInstance().getPropertyByPathAndKey(PROPERTITIES_PATH,"register"));
	public final static String DB_CONNECTION_EXCEPTION = "Could not create connection to database server";
	 
	
	
}
