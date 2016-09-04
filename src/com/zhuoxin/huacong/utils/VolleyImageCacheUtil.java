package com.zhuoxin.huacong.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Bitmap.CompressFormat;
import android.util.LruCache;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader.ImageCache;
import com.zhuoxin.huacong.baseAdapter.MyApplication;

public class VolleyImageCacheUtil implements ImageCache {

	LruCache<String, Bitmap> bitmapLru;

	public VolleyImageCacheUtil(LruCache<String, Bitmap> bitmapLru) {
		super();
		this.bitmapLru = bitmapLru;
	}

	@Override
	public void putBitmap(String url, Bitmap bitmap) {
		// TODO Auto-generated method stub
		if (getFromLru(url) == null) {

			putToLru(bitmap, url);
		}
		if (getFromCache(url) == null) {

			putToCache(bitmap, url);
		}

	}

	@Override
	public Bitmap getBitmap(String url) {
		// TODO Auto-generated method stub
		Bitmap bitmap = getFromLru(url);
		if (bitmap == null) {
			bitmap = getFromCache(url);
		}
		return bitmap;
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
	 * 储存到文件缓存中
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
		String data[] = id.split("/");
		String url = data[data.length - 1];
		File bitmapCache = new File(cacheFileDir, "volley/"+url);
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
	 * 从文件缓存获取
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
