package com.zhuoxin.huacong.adapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.xutils.x;

import com.zhouxin.huacong.news.R;
import com.zhuoxin.huacong.baseAdapter.MyApplication;
import com.zhuoxin.huacong.utils.ImageLoader;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.util.LruCache;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.support.v7.widget.StaggeredGridLayoutManager.LayoutParams;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

public class PhotoAdapter extends Adapter<MyViewHolder> {

	// 获取系统最大运存
	private final int MAXMEMORY = (int) Runtime.getRuntime().maxMemory();

	LruCache<String, Bitmap> lruCache;

	List<String> data;

	public PhotoAdapter(List<String> data) {
		super();
		this.data = data;
		lruCache = new LruCache<String, Bitmap>(MAXMEMORY / 8) {
			@Override
			protected int sizeOf(String key, Bitmap value) {
				// TODO Auto-generated method stub
				return value.getByteCount();
			}
		};
	}

	@Override
	public int getItemCount() {
		// TODO Auto-generated method stub
		return data.size();
	}

	@Override
	public void onBindViewHolder(MyViewHolder holder, int position) {
		List<String> list = ImageLoader.ImageQuary();
		x.image().bind(holder.iv_photo,list.get(position));
		/*
		// TODO Auto-generated method stub
		int rId = 0;
		switch (position % 7) {
		case 0:
			rId = R.drawable.waterfall0;
			break;
		case 1:
			rId = R.drawable.waterfall1;
			break;
		case 2:
			rId = R.drawable.waterfall2;
			break;
		case 3:
			rId = R.drawable.waterfall3;
			break;
		case 4:
			rId = R.drawable.waterfall4;
			break;
		case 5:
			rId = R.drawable.waterfall5;
			break;
		case 6:
			rId = R.drawable.waterfall6;
			break;
		}

			holder.iv_photo.setImageBitmap(bitmap);
	
		 */
	}

	@Override
	public MyViewHolder onCreateViewHolder(ViewGroup view, int position) {
		
		
		LayoutInflater inflater = LayoutInflater.from(MyApplication
				.getContext());

		View v = inflater.inflate(R.layout.layout_photo, view, false);
		LayoutParams lp = (LayoutParams) v.getLayoutParams();
		v.setLayoutParams(lp);
		MyViewHolder mh = new MyViewHolder(v);
		return mh;
	}

}

class MyViewHolder extends ViewHolder {
	ImageView iv_photo;

	public MyViewHolder(View view) {
		super(view);
		// TODO Auto-generated constructor stub
		iv_photo = (ImageView) view.findViewById(R.id.iv_photo);
	}

}
