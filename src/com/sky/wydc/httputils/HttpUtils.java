package com.sky.wydc.httputils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

/**
 * 访问网络的工具类
 */
public class HttpUtils {

	/**
	 * 
	 * @param url_path
	 *            链接地址
	 * @return 返回服务器发出的字符串
	 */
	public static String getJsonString(String url_path) {
		try {
			URL url = new URL(url_path);
			HttpURLConnection connection = (HttpURLConnection) url
					.openConnection();
			connection.setConnectTimeout(3000);
			connection.setRequestMethod("POST");
			connection.setDoInput(true);
			int code = connection.getResponseCode();
			if (code == 200) {
				return changecharset(connection.getInputStream());
			}
		} catch (Exception e) {
		}
		return "";
	}

	// 将输入流转化为字符串
	private static String changecharset(InputStream inputStream) {
		String jsonstring = "";
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		byte[] data = new byte[1024];
		int len = 0;
		try {
			while ((len = inputStream.read(data)) != -1) {
				outputStream.write(data, 0, len);
			}
			jsonstring = new String(outputStream.toByteArray());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return jsonstring;
	}

	// 通过post的方式拿到返回字符串
	public static String postRequest(String url, List<BasicNameValuePair> params) {
		HttpClient client = new DefaultHttpClient();
		HttpPost post = new HttpPost(url);
		HttpResponse response = null;
		try {
			post.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));// 设置post要发送的参数
			response = client.execute(post);// 获取服务器响应
			int code = response.getStatusLine().getStatusCode();// 获取响应码
			if (code == 200) {// 响应码为200说明服务器收到响应，并成功返回数据
				String result = EntityUtils.toString(response.getEntity());
				return result;// 返回相应的数据
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	// 通过get的方式拿到返回字符串
	public static String getRequest(String url, Map<String, String> params) {
		try {
			StringBuilder urlBuilder = new StringBuilder();
			urlBuilder.append(url);// 通过拼接字符串的方式设置参数
			if (null != params) {
				urlBuilder.append("?");
				Iterator<Entry<String, String>> iterator = params.entrySet()
						.iterator();
				while (iterator.hasNext()) {// 遍历MAP类型数据
					Entry<String, String> param = iterator.next();
					urlBuilder
							.append(URLEncoder.encode(param.getKey(), "UTF-8"))
							.append('=')
							.append(URLEncoder.encode(param.getValue(), "UTF-8"));
					if (iterator.hasNext()) {
						urlBuilder.append('&');
					}
				}
			}
			HttpClient client = new DefaultHttpClient();
			HttpGet get = new HttpGet(urlBuilder.toString());// 定义GET请求
			HttpResponse response = client.execute(get);
			if (response.getStatusLine().getStatusCode() == 200) {
				String result = EntityUtils.toString(response.getEntity());
				return result;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	public static InputStream getInputStream(String url) {
		try {
			URL address = new URL(url);
			HttpURLConnection connection = (HttpURLConnection) address
					.openConnection();
			connection.setConnectTimeout(3000);
			connection.setDoInput(true);
			// connection.setDoOutput(true);
			connection.setRequestMethod("GET");
			return connection.getInputStream();
			// return address.openStream();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
