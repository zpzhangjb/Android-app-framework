package zjb.hyl.framework.net;

import java.io.File;
import java.io.InputStream;
import java.net.URI;
import java.net.URLEncoder;
import java.util.List;
import java.util.zip.GZIPInputStream;

import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.params.CookiePolicy;
import org.apache.http.client.params.HttpClientParams;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.FormBodyPart;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.SingleClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.util.EntityUtils;

import zjb.hyl.framework.exception.NetException;
import android.util.Log;

/**
 * 自定义参数的Httpclient。<br>
 * 提供httpGet，httpPost两种传送消息的方式<br>
 * 提供httpPost上传文件的方式
 */
public class MHttpClient {

	// SDK默认参数设置
	public static final int CON_TIME_OUT_MS = 20000;
	public static final int SO_TIME_OUT_MS = 20000;

	private int conTimeOutMs;
	private int soTimeOutMs;

	// 日志输出
	private static final String TAG = "HttpClient";

	private HttpClient httpClient;

	public MHttpClient() {
		this(CON_TIME_OUT_MS, SO_TIME_OUT_MS, null);
	}

	/**
	 * 个性化配置连接管理器
	 * 
	 * @param conTimeOutMs
	 *            连接超时
	 * @param soTimeOutMs
	 *            socket超时
	 * @param proxy
	 *            代理设置，若无请填null
	 */
	public MHttpClient(int conTimeOutMs, int soTimeOutMs, HttpHost proxy) {

		this.conTimeOutMs = conTimeOutMs;
		this.soTimeOutMs = soTimeOutMs;
		SchemeRegistry supportedSchemes = new SchemeRegistry();
		supportedSchemes.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));

		// -----------------------------------SSL
		// Scheme------------------------------------------
		try {
			SSLSocketFactory sslSocketFactory = SSLSocketFactory.getSocketFactory();
			sslSocketFactory.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
			supportedSchemes.register(new Scheme("https", new MSSLSocketFactory(), 443));
		} catch (Exception e) {
			e.printStackTrace();
		}
		// ----------------------------------SSL Scheme
		// end---------------------------------------

		// 参数设置
		HttpParams httpParams = new BasicHttpParams();

		HttpProtocolParams.setVersion(httpParams, HttpVersion.HTTP_1_1);
		HttpProtocolParams.setUseExpectContinue(httpParams, false);

		SingleClientConnManager singleClientConnManager = new SingleClientConnManager(httpParams, supportedSchemes);
		HttpConnectionParams.setConnectionTimeout(httpParams, conTimeOutMs);
		HttpConnectionParams.setSoTimeout(httpParams, soTimeOutMs);

		HttpClientParams.setCookiePolicy(httpParams, CookiePolicy.BROWSER_COMPATIBILITY);
		httpClient = new DefaultHttpClient(singleClientConnManager, httpParams);

		// 设置代理
		if (null != proxy) {
			httpClient.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, proxy);
		}
	}

	/**
	 * Get方法传送消息
	 * 
	 * @param url
	 *            连接的URL
	 * @param queryString
	 *            请求参数串
	 * @return 服务器返回的信息
	 * @throws Exception
	 */
	public String httpGet(String url, String queryString) throws NetException {
		String responseData = null;
		if (queryString != null && !queryString.equals("")) {
			url += "?" + queryString;
		}
		Log.i(TAG, "request--->>>" + url);

		HttpGet httpGet = new HttpGet(url);
		// httpGet.addHeader("Accept-Encoding", "gzip,deflate,sdch");
		httpGet.getParams().setParameter("http.socket.timeout", conTimeOutMs);

		HttpResponse response;
		try {
			response = httpClient.execute(httpGet);
			Log.i(TAG, "StatusLine : " + response.getStatusLine());
			responseData = EntityUtils.toString(response.getEntity());
			httpGet.abort();
			Log.i(TAG, "Response = " + responseData.toString());
		} catch (Exception e) {
			throw new NetException(NetException.NET_ERROR);
		}
		return responseData;
	}

	public InputStream httpGet(String url) throws Exception {
		Log.i(TAG, "request--->>>" + url);
		HttpGet httpGet = new HttpGet(url);
		httpGet.getParams().setParameter("http.socket.timeout", conTimeOutMs);
		HttpResponse response = httpClient.execute(httpGet);
		Log.i(TAG, "StatusLine : " + response.getStatusLine());
		return response.getEntity().getContent();
	}

	/**
	 * Post方法传送消息
	 * 
	 * @param url
	 *            连接的URL
	 * @param queryString
	 *            请求参数串
	 * @return 服务器返回的信息
	 * @throws Exception
	 */
	public String httpPost(String url, String queryString) throws Exception {
		String responseData = null;
		URI tmpUri = new URI(url);

		Log.i(TAG, "request--->>>" + tmpUri.toURL() + "?" + queryString);

		HttpPost httpPost = new HttpPost(tmpUri);
		// httpPost.addHeader("Accept-Encoding", "gzip,deflate,sdch");
		httpPost.getParams().setParameter("http.socket.timeout", conTimeOutMs);
		if (queryString != null && !queryString.equals("")) {
			StringEntity reqEntity = new StringEntity(queryString);
			// 设置类型
			reqEntity.setContentType("application/x-www-form-urlencoded");
			// 设置请求的数据
			httpPost.setEntity(reqEntity);
		}
		HttpResponse response = httpClient.execute(httpPost);

		Log.i(TAG, "StatusLine : " + response.getStatusLine());
		responseData = EntityUtils.toString(response.getEntity());
		httpPost.abort();
		Log.i(TAG, "Response = " + responseData.toString());
		return responseData.toString();
	}

	/**
	 * Post方法传送消息
	 * 
	 * @param url
	 *            连接的URL
	 * @param queryString
	 *            请求参数串
	 * @return 服务器返回的信息
	 * @throws Exception
	 */
	public String httpPostWithFile(String url, String queryString, List<NameValuePair> files) throws Exception {

		String responseData;

		URI tmpUri = new URI(url);
		Log.i(TAG, "request--->>>" + tmpUri.toURL());
		Log.i(TAG, "request--->>>" + queryString);
		MultipartEntity mpEntity = new MultipartEntity();
		HttpPost httpPost = new HttpPost(tmpUri);
		// httpPost.addHeader("Accept-Encoding", "gzip,deflate,sdch");
		StringBody stringBody;
		FileBody fileBody;
		File targetFile;
		String filePath;
		FormBodyPart fbp;

		List<NameValuePair> queryParamList = MStrOperate.getQueryParamsList(queryString);
		for (NameValuePair queryParam : queryParamList) {
			stringBody = new StringBody(URLEncoder.encode(queryParam.getValue(), "utf-8"));
			// stringBody = new StringBody(queryParam.getValue(),
			// Charset.forName("UTF-8"));
			fbp = new FormBodyPart(queryParam.getName(), stringBody);
			mpEntity.addPart(fbp);
		}

		for (NameValuePair param : files) {
			filePath = param.getValue();
			targetFile = new File(filePath);
			fileBody = new FileBody(targetFile, "application/octet-stream");
			fbp = new FormBodyPart(param.getName(), fileBody);
			mpEntity.addPart(fbp);
		}

		httpPost.setEntity(mpEntity);
		HttpResponse response = httpClient.execute(httpPost);
		Log.i(TAG, "StatusLine = " + response.getStatusLine());
		responseData = EntityUtils.toString(response.getEntity());
		httpPost.abort();
		Log.i(TAG, "Response = " + responseData);
		return responseData;
	}

	/**
	 * Get方法传送消息
	 * 
	 * @param url
	 *            连接的URL
	 * @param queryString
	 *            请求参数串
	 * @return 服务器返回的信息
	 * @throws Exception
	 */
	public String gzipHttpGet(String url, String queryString) throws Exception {

		StringBuilder responseData = new StringBuilder();
		if (queryString != null && !queryString.equals("")) {
			url += "?" + queryString;
		}
		Log.i(TAG, "request--->>>" + url);

		HttpGet httpGet = new HttpGet(url);
		httpGet.addHeader("Accept-Encoding", "gzip,deflate,sdch");
		httpGet.getParams().setParameter("http.socket.timeout", conTimeOutMs);

		HttpResponse response;
		response = httpClient.execute(httpGet);
		Log.i(TAG, "StatusLine : " + response.getStatusLine());

		try {
			byte[] b = new byte[2048];
			GZIPInputStream gzin = new GZIPInputStream(response.getEntity().getContent());
			int length = 0;
			while ((length = gzin.read(b)) != -1) {
				responseData.append(new String(b, 0, length));
			}
			gzin.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			httpGet.abort();
		}
		Log.i(TAG, "Response = " + responseData.toString());

		return responseData.toString();
	}

	/**
	 * 断开QHttpClient的连接
	 */
	public void shutdownConnection() {
		try {
			httpClient.getConnectionManager().shutdown();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public int getConTimeOutMs() {
		return conTimeOutMs;
	}

	public void setConTimeOutMs(int conTimeOutMs) {
		this.conTimeOutMs = conTimeOutMs;
	}

	public int getSoTimeOutMs() {
		return soTimeOutMs;
	}

	public void setSoTimeOutMs(int soTimeOutMs) {
		this.soTimeOutMs = soTimeOutMs;
	}

}
