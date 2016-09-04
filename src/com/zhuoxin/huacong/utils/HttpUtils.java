package com.zhuoxin.huacong.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import com.zhuoxin.huacong.baseAdapter.MyApplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

public class HttpUtils {
	public static String HttpVisit(String path) {
		BufferedReader bf = null;
		InputStreamReader isr = null;
		InputStream is = null;
		StringBuffer sb = null;

		try {
			// (1) 创建一个 URL 对象
			URL url = new URL(path);
			// (2) 利用 HttpURLConnection 对象从网络中获取网页数据
			HttpURLConnection httpURLConnection = (HttpURLConnection) url
					.openConnection();
			// 设置访问类型
			httpURLConnection.setRequestMethod("GET");
			// (3) 设置连接超时 、读取超时
			httpURLConnection.setConnectTimeout(5000);
			httpURLConnection.setReadTimeout(5000);
			httpURLConnection.connect();
			// (4) 对响应码进行判断
			if (httpURLConnection.getResponseCode() == 200) {
				// 流操作获取数据
				is = httpURLConnection.getInputStream();
				isr = new InputStreamReader(is);
				bf = new BufferedReader(isr);
				// 节省空间 用一个StringBuffer来承载
				sb = new StringBuffer();

				String len;
				while ((len = bf.readLine()) != null) {
					sb.append(len);
				}
				return sb.toString();

			} else {
				Looper.prepare();
				Toast.makeText(MyApplication.getContext(), "没有获取Json数据",
						Toast.LENGTH_SHORT).show();
				Looper.loop();
			}
			httpURLConnection.disconnect();
			// (5) 得到网络返回的输入流并转化成自己需要的数据类型

		} catch (MalformedURLException e) {
			Log.v("test", "URL异常");
		} catch (ProtocolException e) {
			Log.v("test", "Protocol协议异常");
		} catch (IOException e) {
			Log.v("test", "IO流异常");
		} finally {
			try {
				if (sb != null) {
					bf.close();
					isr.close();
					is.close();
				}

			} catch (IOException e) {
				Log.v("test", "IO流关闭异常");
			}

		}
		return null;
	}

	public static Bitmap getBitmap(String path) {

		Bitmap bitmap = null;
		InputStream is = null;
		try {
			URL url = new URL(path);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setConnectTimeout(5000);
			conn.setReadTimeout(5000);
			conn.connect();
			if (conn.getResponseCode() == 200) {
				is = conn.getInputStream();
				bitmap = BitmapFactory.decodeStream(is);
			}
			conn.disconnect();
		} catch (MalformedURLException e) {
			Looper.prepare();
			Toast.makeText(MyApplication.getContext(), "URL异常",
					Toast.LENGTH_SHORT).show();
			Looper.loop();
		} catch (ProtocolException e) {
			Looper.prepare();
			Toast.makeText(MyApplication.getContext(), "Protocol协议异常",
					Toast.LENGTH_SHORT).show();
			Looper.loop();
		} catch (IOException e) {
			Looper.prepare();
			Toast.makeText(MyApplication.getContext(), "IO流异常",
					Toast.LENGTH_SHORT).show();
			Looper.loop();

		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					Looper.prepare();
					Toast.makeText(MyApplication.getContext(), "IO流关闭异常",
							Toast.LENGTH_SHORT).show();
					Looper.loop();
					Log.v("test", "IO流关闭异常");
				}
			}
		}
		return bitmap;
	}

	// 获取网络状态
	public static boolean hasNetConnected() {
		// 获取系统的网络状态
		ConnectivityManager cm = (ConnectivityManager) MyApplication
				.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
		// 获取networkInfo的实例
		NetworkInfo info = cm.getActiveNetworkInfo();
		if (info != null && info.isConnected()) {
			return true;
		} else {
			return false;
		}

	}

}
