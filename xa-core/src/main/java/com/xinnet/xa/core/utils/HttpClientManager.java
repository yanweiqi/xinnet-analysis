package com.xinnet.xa.core.utils;

import java.io.IOException;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLHandshakeException;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpRequest;
import org.apache.http.NoHttpResponseException;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.conn.ssl.X509HostnameVerifier;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.ExecutionContext;
import org.apache.http.protocol.HttpContext;
import org.apache.log4j.Logger;

public class HttpClientManager {
	private static Logger log = Logger.getLogger(HttpClientManager.class);

	public static int HTTP_MAX_TOTAL_CONNECTION = 50;     // http连接池的总连接数
	public static int HTTP_MAX_CONNECTION_PER_ROUTE = 50; // http连接池对每个主机的最大连接数
	public static int HTTP_CONNECTION_TIMEOUT = 20000;    // http连接的超时时间
	public static int HTTP_SO_TIMEOUT = 120000;           // http连接传输数据的超时时间	
	public static int HTTP_RETRY_COUNT = 5;               // http出错后重试次数

	private HttpClientManager() {
	};

    /**
     * 功能描述：返回httpclient对象
     * @return DefaultHttpClient
     */
	private static DefaultHttpClient createClient() {
		HttpParams params = new BasicHttpParams();
		params.setBooleanParameter(CoreConnectionPNames.STALE_CONNECTION_CHECK,true);
		params.setIntParameter(CoreConnectionPNames.CONNECTION_TIMEOUT,HTTP_CONNECTION_TIMEOUT);
		params.setIntParameter(CoreConnectionPNames.SO_TIMEOUT, HTTP_SO_TIMEOUT);
		HttpProtocolParams.setUserAgent(params, "HttpComponents Client 4.0");
		SchemeRegistry schemeRegistry = new SchemeRegistry();
		schemeRegistry.register(new Scheme("http", 80, PlainSocketFactory.getSocketFactory()));
		try {
			SSLContext ctx = SSLContext.getInstance(SSLSocketFactory.TLS);
			X509TrustManager tm = new X509TrustManager() {
				public void checkClientTrusted(X509Certificate[] xcs,String string) {
				}

				public void checkServerTrusted(X509Certificate[] xcs,String string) {
				}

				public X509Certificate[] getAcceptedIssuers() {
					return null;
				}
			};

			X509HostnameVerifier hostnameVerifier = new X509HostnameVerifier() {
				@Override
				public void verify(String host, SSLSocket ssl)throws IOException {
				}

				@Override
				public void verify(String host, X509Certificate cert)throws SSLException {
				}

				@Override
				public void verify(String host, String[] cns,String[] subjectAlts) throws SSLException {
				}

				@Override
				public boolean verify(String arg0, SSLSession arg1) {
					return true;
				}
			};
			ctx.init(null, new TrustManager[] { tm }, null);

			final SSLSocketFactory sslSocketFactory = new SSLSocketFactory(ctx,hostnameVerifier);
			schemeRegistry.register(new Scheme("https", 443, sslSocketFactory));
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		ThreadSafeClientConnManager cm = new ThreadSafeClientConnManager(schemeRegistry);
		cm.setMaxTotal(HTTP_MAX_TOTAL_CONNECTION);
		cm.setDefaultMaxPerRoute(HTTP_MAX_CONNECTION_PER_ROUTE);
		DefaultHttpClient client = new DefaultHttpClient(cm, params);

		// 设置重试的条件,DefaluHttpRequestRestryHandler看看源码
		HttpRequestRetryHandler myRetryHandler = new HttpRequestRetryHandler() {
			Log log = LogFactory.getLog(HttpRequestRetryHandler.class);
			public boolean retryRequest(IOException exception,int executionCount, HttpContext context) {
				log.warn("retry http request(" + executionCount + ")...");
				if (executionCount >= HTTP_RETRY_COUNT) {
					// Do not retry if over max retry count
					return false;
				}
				if (exception instanceof NoHttpResponseException) {
					// Retry if the server dropped connection on us
					return true;
				}
				if (exception instanceof SSLHandshakeException) {
					// Do not retry on SSL handshake exception
					return false;
				}
				HttpRequest request = (HttpRequest) context.getAttribute(ExecutionContext.HTTP_REQUEST);
				boolean idempotent = !(request instanceof HttpEntityEnclosingRequest);
				if (idempotent) {
					// Retry if the request is considered idempotent
					return true;
				}
				return false;
			}
		};
		client.setHttpRequestRetryHandler(myRetryHandler);
		return client;
	}

	private static class SingletonHolder {
		private static DefaultHttpClient client = createClient();
	}

	public static DefaultHttpClient getHtttpClient() {
		return SingletonHolder.client;
	}

}
