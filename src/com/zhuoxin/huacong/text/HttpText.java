package com.zhuoxin.huacong.text;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.test.AndroidTestCase;
import android.util.Log;

import com.google.gson.Gson;
import com.zhuoxin.huacong.bean.NewsBean;
import com.zhuoxin.huacong.bean.NewsJsonBean;

public class HttpText extends AndroidTestCase {
	public void test() {
		BufferedReader bf = null;
		InputStreamReader isr = null;
		InputStream is = null;

		try {
			// (1) 创建一个 URL 对象
			URL url = new URL(
					"http://v.juhe.cn/toutiao/index?type=&key=d728ab4e75e137c4f23aec12ed3ee6cd");
			// (2) 利用 HttpURLConnection 对象从网络中获取网页数据
			HttpURLConnection httpURLConnection = (HttpURLConnection) url
					.openConnection();
			// (3) 设置连接超时 、读取超时
			httpURLConnection.setConnectTimeout(5000);
			httpURLConnection.setReadTimeout(5000);
			// 设置访问类型
			httpURLConnection.setRequestMethod("GET");
			// (4) 对响应码进行判断
			if (httpURLConnection.getResponseCode() == 200) {
				// 流操作获取数据
				is = httpURLConnection.getInputStream();
				isr = new InputStreamReader(is);
				bf = new BufferedReader(isr);
				// 节省空间 用一个StringBuffer来承载
				StringBuffer sb = new StringBuffer();

				String len;
				while ((len = bf.readLine()) != null) {
					sb.append(len);
				}
				Gson gson = new Gson();
				NewsJsonBean njb = gson.fromJson(sb.toString(),
						NewsJsonBean.class);
				List<NewsBean> newsList = njb.result.data;
				Log.v("test", newsList.size() + "");
			}
			// (5) 得到网络返回的输入流并转化成自己需要的数据类型
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				bf.close();
				isr.close();
				is.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}

	public void test2() {
		// (1) 新建 HttpGet 对象，并通过构造方法把要请求的地址传过来；
		HttpGet httpGet = new HttpGet(
				"http://v.juhe.cn/toutiao/index?type=&key=d728ab4e75e137c4f23aec12ed3ee6cd");
		// (2) 新建 HttpClient 对象；
		HttpClient httpClient = new DefaultHttpClient();

		try {
			// (3) 调用 HttpClient 对象的 execute(HttpGet 的对象)方法新建一个

			// HttpResponse 的对象，并发起 Get 请求。
			HttpResponse httpResponse = httpClient.execute(httpGet);
			// (4) 利用 HttpResponse 的对象的 getEntity()方法获取到服务器返回的内容。
			HttpEntity httpEntity = httpResponse.getEntity();
			String json = EntityUtils.toString(httpEntity);

			Gson gson = new Gson();
			NewsJsonBean njb = gson.fromJson(json, NewsJsonBean.class);
			List<NewsBean> newsList = njb.result.data;
			Log.v("test", newsList.size() + "这是第二条数据");
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void test3() {
		InputStream is = null;
		InputStreamReader isr = null;
		BufferedReader bf = null;
		StringBuffer sb = null;
		try {
			URL url = new URL("https://www.baidu.com/");
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("GET");
			con.setConnectTimeout(5000);
			con.setReadTimeout(5000);
			con.connect();
			con.disconnect();

			if (con.getResponseCode() == 200) {
				is = con.getInputStream();
				isr = new InputStreamReader(is);
				bf = new BufferedReader(isr);
				sb = new StringBuffer();
				String data = null;
				while ((data = bf.readLine()) != null) {
					sb.append(data);
				}
				Log.v("test", sb.toString());
			}
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if (sb != null) {
					is.close();
					bf.close();
					isr.close();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

}
