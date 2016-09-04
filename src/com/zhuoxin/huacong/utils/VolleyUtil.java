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
		// 想要用volley发送大量的访问操作必须现有一个请求队列
		RequestQueue requestQueue = MyApplication.getRequestQueue();
		// 获取图片需要ImageLoader
		ImageLoader imageLoader = new ImageLoader(requestQueue, imageCache);
		// 第一种加载方式
		imageLoader.get(path, ImageLoader.getImageListener(iv, R.drawable.a4,
				R.drawable.back));
		/*
		 * 第二种加载方式 ImageRequest imageRequest = new ImageRequest(path, new
		 * Listener<Bitmap>() {
		 * 
		 * @Override public void onResponse(Bitmap response) { // TODO
		 * Auto-generated method stub iv.setImageBitmap(response); } }, 0, 0,
		 * Config.RGB_565, new ErrorListener() {
		 * 
		 * @Override public void onErrorResponse(VolleyError error) { // TODO
		 * Auto-generated method stub Toast.makeText(MyApplication.getContext(),
		 * "获取Json数据出错,请检查网络", Toast.LENGTH_SHORT).show(); } });
		 * 
		 * requestQueue.add(imageRequest); requestQueue.start();
		 */
	}
}
