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
			// (1) ����һ�� URL ����
			URL url = new URL(
					"http://v.juhe.cn/toutiao/index?type=&key=d728ab4e75e137c4f23aec12ed3ee6cd");
			// (2) ���� HttpURLConnection ����������л�ȡ��ҳ����
			HttpURLConnection httpURLConnection = (HttpURLConnection) url
					.openConnection();
			// (3) �������ӳ�ʱ ����ȡ��ʱ
			httpURLConnection.setConnectTimeout(5000);
			httpURLConnection.setReadTimeout(5000);
			// ���÷�������
			httpURLConnection.setRequestMethod("GET");
			// (4) ����Ӧ������ж�
			if (httpURLConnection.getResponseCode() == 200) {
				// ��������ȡ����
				is = httpURLConnection.getInputStream();
				isr = new InputStreamReader(is);
				bf = new BufferedReader(isr);
				// ��ʡ�ռ� ��һ��StringBuffer������
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
			// (5) �õ����緵�ص���������ת�����Լ���Ҫ����������
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
		// (1) �½� HttpGet ���󣬲�ͨ�����췽����Ҫ����ĵ�ַ��������
		HttpGet httpGet = new HttpGet(
				"http://v.juhe.cn/toutiao/index?type=&key=d728ab4e75e137c4f23aec12ed3ee6cd");
		// (2) �½� HttpClient ����
		HttpClient httpClient = new DefaultHttpClient();

		try {
			// (3) ���� HttpClient ����� execute(HttpGet �Ķ���)�����½�һ��

			// HttpResponse �Ķ��󣬲����� Get ����
			HttpResponse httpResponse = httpClient.execute(httpGet);
			// (4) ���� HttpResponse �Ķ���� getEntity()������ȡ�����������ص����ݡ�
			HttpEntity httpEntity = httpResponse.getEntity();
			String json = EntityUtils.toString(httpEntity);

			Gson gson = new Gson();
			NewsJsonBean njb = gson.fromJson(json, NewsJsonBean.class);
			List<NewsBean> newsList = njb.result.data;
			Log.v("test", newsList.size() + "���ǵڶ�������");
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
