package com.zhuoxin.huacong.utils;

import org.json.JSONObject;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.widget.ImageView;
import android.widget.Toast;
import com.android.volley.RequestQueue;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageLoader.ImageCache;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.zhouxin.huacong.news.R;
import com.zhuoxin.huacong.baseAdapter.MyApplication;
import com.zhuoxin.huacong.callback.VolleyCallBack;

public class VolleyUtil {
	static VolleyCallBack callback = null;

	public static void getHttp(String path, VolleyCallBack callback) {
		VolleyUtil.callback = callback;
		RequestQueue rq = MyApplication.getRequestQueue();
		rq.add(new JsonObjectRequest(path, null, new Listener<JSONObject>() {

			@Override
			public void onResponse(JSONObject response) {
				// TODO Auto-generated method stub
				VolleyUtil.callback.getJsonSuccess(response.toString());
			}
		}, new ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError error) {
				// TODO Auto-generated method stub
				VolleyUtil.callback.getJsonDefeat();
			}
		}));

		rq.start();

	}

	public static void getBitmap(String path, final ImageView iv,
			VolleyImageCacheUtil imageCache) {
		// ��Ҫ��volley���ʹ����ķ��ʲ�����������һ���������
		RequestQueue requestQueue = MyApplication.getRequestQueue();
		// ��ȡͼƬ��ҪImageLoader
		ImageLoader imageLoader = new ImageLoader(requestQueue, imageCache);
		// ��һ�ּ��ط�ʽ
		imageLoader.get(path, ImageLoader.getImageListener(iv, R.drawable.a4,
				R.drawable.back));
		/*
		 * �ڶ��ּ��ط�ʽ ImageRequest imageRequest = new ImageRequest(path, new
		 * Listener<Bitmap>() {
		 * 
		 * @Override public void onResponse(Bitmap response) { // TODO
		 * Auto-generated method stub iv.setImageBitmap(response); } }, 0, 0,
		 * Config.RGB_565, new ErrorListener() {
		 * 
		 * @Override public void onErrorResponse(VolleyError error) { // TODO
		 * Auto-generated method stub Toast.makeText(MyApplication.getContext(),
		 * "��ȡJson���ݳ���,��������", Toast.LENGTH_SHORT).show(); } });
		 * 
		 * requestQueue.add(imageRequest); requestQueue.start();
		 */
	}
}
