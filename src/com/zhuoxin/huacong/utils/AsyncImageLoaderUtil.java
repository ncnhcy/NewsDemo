package com.zhuoxin.huacong.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import com.zhouxin.huacong.news.R;
import com.zhuoxin.huacong.baseAdapter.MyApplication;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.LruCache;
import android.widget.ImageView;
import android.widget.Toast;

public class AsyncImageLoaderUtil extends AsyncTask<String, Void, Bitmap> {

	LruCache<String, Bitmap> bitmapLru;
	ImageView iv_news_icon;
	String id;

	public AsyncImageLoaderUtil(LruCache<String, Bitmap> bitmapLru,
			ImageView iv_news_icon) {
		super();
		this.bitmapLru = bitmapLru;
		this.iv_news_icon = iv_news_icon;
	}

	/**
	 * params[0] 定义为访问的网络地址 params[1] 定义为网络地址的标识码
	 */
	@Override
	protected Bitmap doInBackground(String... params) {
		id = params[1];
		Bitmap bitmap = null;
		bitmap = getFromLru(params[1]);
		if (bitmap == null) {
			bitmap = getFromCache(params[1]);
			if (bitmap == null) {
				bitmap = HttpUtils.getBitmap(params[0]);
			}
		}
		putToLru(bitmap, params[1]);
		putToCache(bitmap, params[1]);
		return bitmap;
	}

	@Override
	protected void onPostExecute(Bitmap result) {
		super.onPostExecute(result);
		if (iv_news_icon.getTag() != null && iv_news_icon.getTag().equals(id)) {
			if (result != null) {
				iv_news_icon.setImageBitmap(result);
			} else {
				iv_news_icon.setImageResource(R.drawable.a4);
			}

		}
	}

	/**
	 * 从Lru中获取图片
	 * 
	 * @param id
	 * @return
	 */
	private Bitmap getFromLru(String id) {
		return bitmapLru.get(id);
	}

	/**
	 * 储存到Lru中
	 * 
	 * @param bitmap
	 * @param url
	 * @param id
	 */
	private void putToLru(Bitmap bitmap, String id) {

		if (bitmap != null) {
			bitmapLru.put(id, bitmap);
		}

	}

	/**
	 * 储存到缓存中
	 * 
	 * @param bitmap
	 * @param id
	 */

	private void putToCache(Bitmap bitmap, String id) {
		File cacheFileDir = MyApplication.getContext().getCacheDir();
		if (!cacheFileDir.exists()) {
			cacheFileDir.mkdirs();
		}
		FileOutputStream fos = null;
		File bitmapCache = new File(cacheFileDir, id);
		if (!bitmapCache.exists() && bitmap != null) {
			try {
				fos = new FileOutputStream(bitmapCache);

				bitmap.compress(CompressFormat.JPEG, 100, fos);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				if (fos != null) {
					try {

						fos.close();

					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}

	}

	/**
	 * 从缓存获取
	 * 
	 * @param id
	 * @return
	 */
	private Bitmap getFromCache(String id) {
		Bitmap bitmap = null;

		File cacheFileDir = MyApplication.getContext().getCacheDir();

		if (!cacheFileDir.exists()) {
			cacheFileDir.mkdirs();
		}
		FileInputStream is = null;
		File bitmapFile = new File(cacheFileDir, id);
		try {
			if (bitmapFile.exists()) {
				is = new FileInputStream(bitmapFile);
				bitmap = BitmapFactory.decodeStream(is);
			}
		} catch (FileNotFoundException e) {
			Toast.makeText(MyApplication.getContext(), "文件未找到",
					Toast.LENGTH_SHORT).show();
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					Toast.makeText(MyApplication.getContext(), "流关闭异常",
							Toast.LENGTH_SHORT).show();
				}
			}

		}
		return bitmap;

	}

}
