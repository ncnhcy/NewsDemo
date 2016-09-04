package com.zhuoxin.huacong.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.util.LruCache;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhouxin.huacong.news.R;
import com.zhuoxin.huacong.baseAdapter.MyBaseAdapter;
import com.zhuoxin.huacong.bean.NewsItemBean;
import com.zhuoxin.huacong.utils.AsyncImageLoaderUtil;

public class NewsSecienceAdapter extends MyBaseAdapter<NewsItemBean> {

	LruCache<String, Bitmap> bitmapLru;
	private final int MAXMEMORY = (int) Runtime.getRuntime().maxMemory();

	public NewsSecienceAdapter(Context context) {
		super(context);
		bitmapLru = new LruCache<String, Bitmap>(MAXMEMORY / 8) {
			@Override
			protected int sizeOf(String key, Bitmap value) {
				return value.getByteCount();
				
			}
		};
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder = new ViewHolder();
		if (convertView == null) {
			convertView = layoutInflater.inflate(
					R.layout.layout_listview_science_item, null);
			viewHolder.iv_science_icon = (ImageView) convertView
					.findViewById(R.id.iv_science_icon);
			viewHolder.tv_science_title = (TextView) convertView
					.findViewById(R.id.tv_science_title);

			viewHolder.tv_science_type = (TextView) convertView
					.findViewById(R.id.tv_science_type);

			viewHolder.tv_science_date = (TextView) convertView
					.findViewById(R.id.tv_science_date);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		
		String path = getItem(position).getThumbnail_pic_s();
		String id = getItem(position).getUrl();
		
		viewHolder.iv_science_icon.setTag(id);
		Bitmap bitmap = bitmapLru.get(id);
		if (bitmap != null) {
			viewHolder.iv_science_icon.setImageBitmap(bitmap);
			
		} else { // º”‘ÿÕ¯¬ÁÕº∆¨
			viewHolder.iv_science_icon.setImageResource(R.drawable.a4);
			AsyncImageLoaderUtil asyncLoad = new AsyncImageLoaderUtil(
					bitmapLru, viewHolder.iv_science_icon);
			asyncLoad.execute(path, id);
		}

		viewHolder.tv_science_title.setText(getItem(position).getTitle());
		viewHolder.tv_science_type.setText(getItem(position).getCategory());
		viewHolder.tv_science_date.setText(getItem(position).getDate());

		return convertView;	
		
	}

	private static class ViewHolder {
		ImageView iv_science_icon;
		TextView tv_science_title;
		TextView tv_science_type;
		TextView tv_science_date;
	}

}
