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
			// (1) ����һ�� URL ����
			URL url = new URL(path);
			// (2) ���� HttpURLConnection ����������л�ȡ��ҳ����
			HttpURLConnection httpURLConnection = (HttpURLConnection) url
					.openConnection();
			// ���÷�������
			httpURLConnection.setRequestMethod("GET");
			// (3) �������ӳ�ʱ ����ȡ��ʱ
			httpURLConnection.setConnectTimeout(5000);
			httpURLConnection.setReadTimeout(5000);
			httpURLConnection.connect();
			// (4) ����Ӧ������ж�
			if (httpURLConnection.getResponseCode() == 200) {
				// ��������ȡ����
				is = httpURLConnection.getInputStream();
				isr = new InputStreamReader(is);
				bf = new BufferedReader(isr);
				// ��ʡ�ռ� ��һ��StringBuffer������
				sb = new StringBuffer();

				String len;
				while ((len = bf.readLine()) != null) {
					sb.append(len);
				}
				return sb.toString();

			} else {
				Looper.prepare();
				Toast.makeText(MyApplication.getContext(), "û�л�ȡJson����",
						Toast.LENGTH_SHORT).show();
				Looper.loop();
			}
			httpURLConnection.disconnect();
			// (5) �õ����緵�ص���������ת�����Լ���Ҫ����������

		} catch (MalformedURLException e) {
			Log.v("test", "URL�쳣");
		} catch (ProtocolException e) {
			Log.v("test", "ProtocolЭ���쳣");
		} catch (IOException e) {
			Log.v("test", "IO���쳣");
		} finally {
			try {
				if (sb != null) {
					bf.close();
					isr.close();
					is.close();
				}

			} catch (IOException e) {
				Log.v("test", "IO���ر��쳣");
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
			Toast.makeText(MyApplication.getContext(), "URL�쳣",
					Toast.LENGTH_SHORT).show();
			Looper.loop();
		} catch (ProtocolException e) {
			Looper.prepare();
			Toast.makeText(MyApplication.getContext(), "ProtocolЭ���쳣",
					Toast.LENGTH_SHORT).show();
			Looper.loop();
		} catch (IOException e) {
			Looper.prepare();
			Toast.makeText(MyApplication.getContext(), "IO���쳣",
					Toast.LENGTH_SHORT).show();
			Looper.loop();

		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					Looper.prepare();
					Toast.makeText(MyApplication.getContext(), "IO���ر��쳣",
							Toast.LENGTH_SHORT).show();
					Looper.loop();
					Log.v("test", "IO���ر��쳣");
				}
			}
		}
		return bitmap;
	}

	// ��ȡ����״̬
	public static boolean hasNetConnected() {
		// ��ȡϵͳ������״̬
		ConnectivityManager cm = (ConnectivityManager) MyApplication
				.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
		// ��ȡnetworkInfo��ʵ��
		NetworkInfo info = cm.getActiveNetworkInfo();
		if (info != null && info.isConnected()) {
			return true;
		} else {
			return false;
		}

	}

}
