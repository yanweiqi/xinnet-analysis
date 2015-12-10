package com.xinnet.xa.core.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.log4j.Logger;

/**
 * @author 闫伟旗[yanweiqi@xinnet.com]
 * @version
 * @since 2014-3-11
 */
public class HttpClientUtil {
	private static Logger log = Logger.getLogger(HttpClientUtil.class);

	/**
	 * 功能描述：HttpClient post 请求,普通页面请求
	 * 
	 * @param url
	 * @param params
	 * @param encoding
	 * @return String
	 * @throws Exception
	 * @author 闫伟旗[yanweiqi@xinnet.com]
	 * @since 2014-3-19
	 * @update:[变更日期YYYY-MM-DD][更改人姓名][变更描述]
	 */
	public static String post(String url, String params, String encoding)
			throws Exception {
		HttpPost post = new HttpPost(url);
		String result = "";
		try {
			StringEntity stringEntity = new StringEntity(params);
			if (StringUtils.isNotEmpty(params)
					&& StringUtils.isNotEmpty(encoding)) {
				stringEntity.setContentEncoding(encoding);
			}
			post.setEntity(stringEntity);
			DefaultHttpClient client = HttpClientManager.getHtttpClient();
			HttpResponse response = client.execute(post);
			result = getHttpResult(url, response, encoding);

		} catch (Exception e) {
			// 保证出现异常的情况下，链接能正常释放
			if (post != null && !post.isAborted())
				post.abort();
			throw new Exception(e.getMessage(), e);
		}
		return result;

	}

	/**
	 * 功能描述：HttpClient post 请求,Form提交
	 * 
	 * @param url
	 * @param params
	 * @param encoding
	 * @throws Exception
	 * @author 闫伟旗[yanweiqi@xinnet.com]
	 * @since 2014-3-11
	 * @update:[变更日期YYYY-MM-DD][更改人姓名][变更描述]
	 */
	public static String post(String url, Map<String, String> params,
			String encoding) throws Exception {
		HttpPost post = new HttpPost(url);
		String result = "";
		try {
			List<NameValuePair> nvps = new ArrayList<NameValuePair>();
			for (Entry<String, String> entry : params.entrySet()) {
				nvps.add(new BasicNameValuePair(entry.getKey(), entry
						.getValue()));
			}
			UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(nvps);
			if (StringUtils.isNotEmpty(encoding)) {
				formEntity.setContentEncoding(encoding);
			}

			post.setEntity(formEntity);
			DefaultHttpClient client = HttpClientManager.getHtttpClient();
			HttpResponse response = client.execute(post);
			result = getHttpResult(url, response, encoding);

		} catch (Exception e) {
			// 保证出现异常的情况下，链接能正常释放
			if (post != null && !post.isAborted())
				post.abort();
			throw new Exception(e.getMessage(), e);
		}
		return result;
	}

	/**
	 * 功能描述：HttpClient get请求
	 * 
	 * @param url
	 * @param encoding
	 * @return String
	 * @throws Exception
	 * @author 闫伟旗[yanweiqi@xinnet.com]
	 * @since 2014-3-11
	 * @update:[变更日期YYYY-MM-DD][更改人姓名][变更描述]
	 */
	public static String get(String url, String encoding,Header[] headers) throws Exception {
		HttpGet get = new HttpGet(url);
		String result = "";
		try {
			DefaultHttpClient client = HttpClientManager.getHtttpClient();
			if(ArrayUtils.isNotEmpty(headers)){
				get.setHeaders(headers);
			}
			HttpResponse response = client.execute(get);
			result = getHttpResult(url, response, encoding);
		} catch (Exception e) {
			if (get != null && !get.isAborted())
				get.abort();
			throw new Exception(e.getClass().getName() + ":" + e.getMessage(),
					e);
		}
		return result;
	}

	/**
	 * 功能描述：HttpClient 相应结果
	 * 
	 * @param response
	 * @param encoding
	 * @return String
	 * @throws Exception
	 * @author 闫伟旗[yanweiqi@xinnet.com]
	 * @since 2014-3-11
	 * @update:[变更日期YYYY-MM-DD][更改人姓名][变更描述]
	 */
	private static String getHttpResult(String url, HttpResponse response,
			String encoding) throws Exception {
		HttpEntity entity = response.getEntity();
		// 判断返回的状态是不是200
		int statusCode = response.getStatusLine().getStatusCode();
		String result = "";
		if (entity != null) {
			BufferedReader br = null;
			try {
				InputStreamReader streamReader = StringUtils.isEmpty(encoding) ? new InputStreamReader(
						entity.getContent()) : new InputStreamReader(
						entity.getContent(), encoding);
				br = new BufferedReader(streamReader);

				String tempbf;
				StringBuilder html = new StringBuilder();
				while ((tempbf = br.readLine()) != null) {
					html.append(tempbf + "\r\n");
				}

				result = html.toString();

				log.debug(result);

			} finally {
				if (br != null)
					br.close();
			}
		}
		if (statusCode != HttpStatus.SC_OK) {
			throw new Exception("httpclient call " + url
					+ " error httpResponse status=" + statusCode + ",  result="
					+ result);
		}
		return result;
	}

 

	 

	

}
