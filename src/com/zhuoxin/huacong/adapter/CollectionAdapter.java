package com.zhuoxin.huacong.adapter;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.LruCache;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.zhouxin.huacong.news.R;
import com.zhuoxin.huacong.baseAdapter.MyBaseAdapter;
import com.zhuoxin.huacong.bean.NewsBean;
import com.zhuoxin.huacong.utils.AsyncImageLoaderUtil;

public class CollectionAdapter extends MyBaseAdapter<NewsBean> {
	LruCache<String, Bitmap> bitmapLru;
	List<Integer> list = new ArrayList<Integer>();
	ConvenientBanner<Integer> convenientBanner;

	// VolleyImageCacheUtil imageCache;
	// 获取系统最大运存
	private final int MAXMEMORY = (int) Runtime.getRuntime().maxMemory();

	public CollectionAdapter(Context context) {
		super(context);
		bitmapLru = new LruCache<String, Bitmap>(MAXMEMORY / 8) {
			@Override
			protected int sizeOf(String key, Bitmap value) {
				return value.getByteCount();
			}
		};
	}

	@SuppressLint("InflateParams")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder = new ViewHolder();

		if (convertView == null) {

			convertView = layoutInflater.inflate(
					R.layout.layout_home_xlistitem, null);
			viewHolder.iv_news_icon = (ImageView) convertView
					.findViewById(R.id.iv_news_icon);
			viewHolder.tv_news_title = (TextView) convertView
					.findViewById(R.id.tv_news_title);
			viewHolder.tv_news_realtype = (TextView) convertView
					.findViewById(R.id.tv_news_type);
			viewHolder.tv_news_date = (TextView) convertView
					.findViewById(R.id.tv_news_date);

			convertView.setTag(viewHolder);
		} else {

			viewHolder = (ViewHolder) convertView.getTag();

		}
		String id = getItem(position).getUniquekey();
		String path = getItem(position).getThumbnail_pic_s();
		viewHolder.iv_news_icon.setTag(getItem(position).getUrl());
		Bitmap bitmap = bitmapLru.get(id);
		// Bitmap bitmap = imageCache.getBitmap(path);

		/*
		 * 通过Volley框架获取的新闻数据 设置图片，这种方式会有图片错位的问题 未解决 if (bitmap == null) {
		 * viewHolder.iv_news_icon.setImageResource(R.drawable.a4);
		 * VolleyUtil.getBitmap(path, viewHolder.iv_news_icon, imageCache);
		 * 
		 * } else { viewHolder.iv_news_icon.setImageBitmap(bitmap); }
		 */

		if (bitmap != null) {
			viewHolder.iv_news_icon.setImageBitmap(bitmap);
		} else { // 加载网络图片
			viewHolder.iv_news_icon.setImageResource(R.drawable.a4);
			AsyncImageLoaderUtil asyncLoad = new AsyncImageLoaderUtil(
					bitmapLru, viewHolder.iv_news_icon);
			asyncLoad.execute(path, id);
		}

		viewHolder.tv_news_title.setText(getItem(position).getTitle());
		viewHolder.tv_news_realtype.setText(getItem(position).getRealtype());
		viewHolder.tv_news_date.setText(getItem(position).getDate());

		return convertView;
	}

	private static class ViewHolder {
		ImageView iv_news_icon;
		TextView tv_news_title;
		TextView tv_news_realtype;
		TextView tv_news_date;

	}

}
