package com.xinnet.xa.core.vo;

import java.net.URL;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.xinnet.xa.core.utils.DateUtil;
import com.xinnet.xa.core.utils.Utils;
import com.xinnet.xa.core.utils.XaWebUtils;

public class TrackData {

	private String domain;
	private String ip;
	private String prevUrl;
	private String currentUrl;
	private String cookie;
	private String action;
	private String operationType;
	private String operationData;
	private String source;
	private String userId;
	private long vtime;
	private String utm_source;
	private String utm_medium;
	private String utm_campaign;
	private String utm_content;
	private String utm_term;
	private String searchKW;
	private String searchPKW;
	private String actionTime;
	private String tenantID;
	private String terminal;
	private String area;
	private String ftime;
	private String ltime;
	private String[] transformDatas;
	private String jsSessionId;
	private String timeRange;
	private int pageNumber;
	private int newVisitor;

	
	public int getNewVisitor() {
		return newVisitor;
	}

	public void setNewVisitor(int newVisitor) {
		this.newVisitor = newVisitor;
	}

	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	public String getTimeRange() {
		return timeRange;
	}

	public void setTimeRange(String timeRange) {
		this.timeRange = timeRange;
	}

	public boolean multiple() {
		if (StringUtils.isBlank(this.operationData)) {
			return false;
		}
		return this.operationData.indexOf("||") > 0;
	}

	public String getJsSessionId() {
		return jsSessionId;
	}

	public void setJsSessionId(String jsSessionId) {
		this.jsSessionId = jsSessionId;
	}

	public String[] getTransformDatas() {
		return transformDatas;
	}

	public void setTransformDatas(String[] transformDatas) {
		this.transformDatas = transformDatas;
	}

	public String getFtime() {
		return ftime;
	}

	public void setFtime(String ftime) {
		this.ftime = ftime;
	}

	public String getLtime() {
		return ltime;
	}

	public void setLtime(String ltime) {
		this.ltime = ltime;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getTerminal() {
		return terminal;
	}

	public void setTerminal(String terminal) {

		this.terminal = terminal;

	}

	public String getTenantID() {
		return tenantID;
	}

	public void setTenantID(String tenantID) {
		this.tenantID = tenantID;
	}

	public String getActionTime() {
		return actionTime;
	}

	public void setActionTime(String actionTime) {
		this.actionTime = actionTime;
	}

	public String getSearchKW() {
		return searchKW;
	}

	public void setSearchKW(String searchKW) {
		this.searchKW = searchKW == null ? null
				: searchKW.length() > 100 ? searchKW.substring(0, 90)
						: searchKW;
	}

	public String getSearchPKW() {
		return searchPKW;
	}

	public void setSearchPKW(String searchPKW) {
		this.searchPKW = searchPKW == null ? null
				: searchPKW.length() > 100 ? searchPKW.substring(0, 90)
						: searchPKW;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getPrevUrl() {
		return prevUrl;
	}

	public void setPrevUrl(String prevUrl) {
		this.prevUrl = prevUrl;
	}

	public String getCurrentUrl() {
		return currentUrl;
	}

	public void setCurrentUrl(String currentUrl) {
		this.currentUrl = currentUrl;
	}

	public String getCookie() {
		return cookie;
	}

	public void setCookie(String cookie) {
		this.cookie = cookie;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getOperationType() {
		return operationType;
	}

	public void setOperationType(String operationType) {
		this.operationType = operationType;
	}

	public String getOperationData() {
		return operationData;
	}

	public void setOperationData(String operationData) {
		this.operationData = operationData;
	}

	public long getVtime() {
		return vtime;
	}

	public void setVtime(long vtime) {
		this.vtime = vtime;
	}

	public String getUtm_source() {
		return utm_source;
	}

	public void setUtm_source(String utm_source) {
		this.utm_source = utm_source;
	}

	public String getUtm_medium() {
		return utm_medium;
	}

	public void setUtm_medium(String utm_medium) {
		this.utm_medium = utm_medium;
	}

	public String getUtm_campaign() {
		return utm_campaign;
	}

	public void setUtm_campaign(String utm_campaign) {

		this.utm_campaign = utm_campaign;

	}

	public String getUtm_content() {
		return utm_content;
	}

	public void setUtm_content(String utm_content) {
		this.utm_content = utm_content;
	}

	public String getUtm_term() {
		return utm_term;
	}

	public void setUtm_term(String utm_term) {
		this.utm_term = utm_term;
	}

	public void createUtm() throws Exception {
		StringBuilder sb = new StringBuilder(currentUrl);
		sb.append("&utm_medium=").append(utm_medium);
		if (StringUtils.isNotBlank(utm_campaign)) {
			sb.append("&utm_campaign=").append(utm_campaign);
		}
		if (StringUtils.isNotBlank(utm_campaign)) {
			sb.append("&utm_content=").append(utm_content);
		}
		if (StringUtils.isNotBlank(utm_term)) {
			sb.append("&utm_term=").append(utm_term);
		}
		
		currentUrl = sb.length()<=250?sb.toString():sb.substring(0, 250);
		URL url = new URL(currentUrl);
		String query = url.getQuery();
		if (StringUtils.isNotBlank(query)) {
			String[] utms = query.split("&");
			for (String utm : utms) {
				if (utm.startsWith("utm_source")) {
					String[] utmArray = utm.split("=");
					utm_source = utmArray[1];
				}
			}
		}
		// Map<String, String> properties = this.getUtmMap();
		// BeanUtils.populate(this, properties);
	}

	private String DecodeUrl(String url) {
		String decoderUrl = "";
		if (StringUtils.isNotBlank(url)) {
			decoderUrl = Utils.getUrlDecoder(url.toLowerCase());
			if (decoderUrl.indexOf("�") > 0) {
				decoderUrl = decoderUrl.substring(0, decoderUrl.indexOf("?"));
			} 
			if (decoderUrl.length() > 200) {
				decoderUrl = decoderUrl.substring(0, 200);
			}

		}
		return decoderUrl;

	}

	public void analystData(List<String> transformTypes) throws Exception {
		convertVtime();
		formatTimeRange();
		if (StringUtils.isNotBlank(terminal) && terminal.length() > 90) {
			terminal = terminal.substring(0, 90);
		}
		prevUrl = DecodeUrl(prevUrl);
		currentUrl = DecodeUrl(currentUrl);

		if (StringUtils.isNotBlank(operationType)
				&& !transformTypes.contains(operationType)) {
			if (currentUrl.indexOf("utm_") > 0) {
				createUtm();
				source = utm_source;
				searchKW = utm_term;
				analystPrevUrl(true);
			} else if (StringUtils.isBlank(prevUrl)) {
				source = "DIRECT";
			} else {
				analystPrevUrl(false);
			}
		}
	}
	
	public void convertVtime(){
		if(operationType.equals("V")){
			long time = vtime/1000;
			vtime= time/1000>0?time:1;
		}
		
	}

	public void analystTransformDatas() {
		transformDatas = operationData.split(",");
	}

	public void analystPrevUrl(boolean payChannel) {

		URL url = XaWebUtils.getURL(prevUrl);
		if (url != null) {
			if (!payChannel) {
				source = url.getHost();
			}
			if (source.matches("\\w*\\.baidu\\.com.*")) {
				analystSearchKW(url, "wd=", "bs=");
				analystSearchKW(url, "word=", "bs=");
			}
			if (source.matches("\\w*\\.so\\.com.*") || source.matches("\\w*\\.haosou\\.com.*")) {
				analystSearchKW(url, "q=", "pq=");
			}

			if (source.matches("\\w*\\.sogou\\.com.*")) {
				analystSearchKW(url, "query=", "");
			}
		}

	}

	public void analystSearchKW(URL url, String keword, String priorKW) {
		String query = url.getQuery();
		if (StringUtils.isNotBlank(query)) {
			String[] items = query.split("&");
			for (String item : items) {
				if (item.startsWith(keword)) {
					setSearchKW(item.substring(item.indexOf("=") + 1));
				} else if (StringUtils.isNotBlank(priorKW)
						&& item.startsWith(priorKW)) {
					setSearchPKW(item.substring(item.indexOf("=") + 1));
				}
			}
		}
	}

	private void formatTimeRange() throws ParseException {

		Date date = DateUtil.parseDateTime(actionTime);
		timeRange = DateUtil.formatDateHour(date);

	}

	@Override
	public String toString() {
		return "TrackData [domain=" + domain + ", ip=" + ip + ", prevUrl="
				+ prevUrl + ", currentUrl=" + currentUrl + ", cookie=" + cookie
				+ ", action=" + action + ", operationType=" + operationType
				+ ", operationData=" + operationData + ", source=" + source
				+ ", userId=" + userId + ", vtime=" + vtime + ", utm_source="
				+ utm_source + ", utm_medium=" + utm_medium + ", utm_campaign="
				+ utm_campaign + ", utm_content=" + utm_content + ", utm_term="
				+ utm_term + ", searchKW=" + searchKW + ", searchPKW="
				+ searchPKW + ", actionTime=" + actionTime + ", tenantID="
				+ tenantID + ", terminal=" + terminal + ", area=" + area
				+ ", ftime=" + ftime + ", ltime=" + ltime + "]";
	}

}
