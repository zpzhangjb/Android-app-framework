package zjb.hyl.framework.net;

import java.io.InputStream;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import zjb.hyl.framework.exception.NetException;

/**
 * 调用Http和Https协议通讯的接口
 */
public class Request {

	/**
	 * 使用Get方法发送API请求
	 * 
	 * @param url
	 *            远程API请求地址
	 * @param params
	 *            参数列表
	 * @return
	 * @throws Exception
	 */
	public static String getResource(String url, List<NameValuePair> params) throws NetException {
		/*if (StaticInfo.loginUser != null && StaticInfo.loginUser.access_token != null) {
			params.add(new BasicNameValuePair("access_token", StaticInfo.loginUser.access_token));
		}*/
		MHttpClient client = new MHttpClient();
		String response = client.httpGet(url, MStrOperate.getQueryString(params));
		client.shutdownConnection();
		return response;
	};

	public static InputStream getResource(String url) throws Exception {
		MHttpClient client = new MHttpClient();
		return client.httpGet(url);
	}

	/**
	 * 使用Post方法发送API请求
	 * 
	 * @param url
	 *            远程API请求地址
	 * @param params
	 *            参数列表
	 * @return
	 * @throws Exception
	 */
	public static String postContent(String url, List<NameValuePair> params) throws Exception {
		/*if (StaticInfo.loginUser != null && StaticInfo.loginUser.access_token != null) {
			params.add(new BasicNameValuePair("access_token", StaticInfo.loginUser.access_token));
		}*/
		MHttpClient client = new MHttpClient();
		String response = client.httpPost(url, MStrOperate.getQueryString(params));
		client.shutdownConnection();
		return response;
	}

	/**
	 * 使用Post方法发送API请求，并上传文件
	 * 
	 * @param url
	 *            远程API请求地址
	 * @param params
	 *            参数列表
	 * @param files
	 *            需要上传的文件列表
	 * @return
	 * @throws Exception
	 */
	public static String postFile(String url, List<NameValuePair> params, List<NameValuePair> files) throws Exception {
		/*if (StaticInfo.loginUser != null && StaticInfo.loginUser.access_token != null) {
			params.add(new BasicNameValuePair("access_token", StaticInfo.loginUser.access_token));
		}*/
		MHttpClient client = new MHttpClient();
		String response = client.httpPostWithFile(url, MStrOperate.getQueryString(params), files);
		client.shutdownConnection();
		return response;
	}
}
