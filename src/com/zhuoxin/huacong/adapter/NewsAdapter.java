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
import android.widget.Toast;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.holder.Holder;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.zhouxin.huacong.news.R;
import com.zhuoxin.huacong.baseAdapter.MyApplication;
import com.zhuoxin.huacong.baseAdapter.MyBaseAdapter;
import com.zhuoxin.huacong.bean.NewsBean;
import com.zhuoxin.huacong.utils.AsyncImageLoaderUtil;
import com.zhuoxin.huacong.utils.VolleyImageCacheUtil;
import com.zhuoxin.huacong.utils.VolleyUtil;

public class NewsAdapter extends MyBaseAdapter<NewsBean> {

	LruCache<String, Bitmap> bitmapLru;
	List<Integer> list = new ArrayList<Integer>();
	ConvenientBanner<Integer> convenientBanner;
	final int TYPE_1 = 0;
	final int TYPE_2 = 1;
	VolleyImageCacheUtil imageCache;
	// ��ȡϵͳ����˴�
	private final int MAXMEMORY = (int) Runtime.getRuntime().maxMemory();

	public NewsAdapter(Context context) {
		super(context);
		// ����Lrucache
		bitmapLru = new LruCache<String, Bitmap>(MAXMEMORY / 8) {
			@Override
			protected int sizeOf(String key, Bitmap value) {
				return value.getByteCount();
			}
		};
		imageCache = new VolleyImageCacheUtil(bitmapLru);

	}

	@Override
	public int getViewTypeCount() {
		// TODO Auto-generated method stub
		return 2;
	}

	@Override
	public int getItemViewType(int position) {
		// TODO Auto-generated method stub

		if (position == 0) {
			return TYPE_1;
		} else {
			return TYPE_2;
		}
	}

	@SuppressWarnings("unchecked")
	@SuppressLint("InflateParams")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder = new ViewHolder();
		int type = getItemViewType(position);

		if (type == 0) {
			convertView = layoutInflater.inflate(R.layout.layout_banner_item,
					null);

			convenientBanner = (ConvenientBanner<Integer>) convertView
					.findViewById(R.id.convenientBanner);
			setConvenientBanner();

		} else {
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
				if (type != 0) {
					viewHolder = (ViewHolder) convertView.getTag();
				}
			}

			String id = getItem(position).getUniquekey();
			String path = getItem(position).getThumbnail_pic_s();
			/*
			 * viewHolder.iv_news_icon.setTag(getItem(position).getUrl());
			 * Bitmap bitmap = bitmapLru.get(id);
			 * 
			 * if (bitmap != null) {
			 * viewHolder.iv_news_icon.setImageBitmap(bitmap);
			 * 
			 * } else { // ��������ͼƬ
			 * viewHolder.iv_news_icon.setImageResource(R.drawable.a4);
			 * AsyncImageLoaderUtil asyncLoad = new AsyncImageLoaderUtil(
			 * bitmapLru, viewHolder.iv_news_icon); asyncLoad.execute(path, id);
			 * }
			 */

			Bitmap bitmap = imageCache.getBitmap(path);

			// ͨ��Volley��ܻ�ȡ���������� ����ͼƬ�����ַ�ʽ����ͼƬ��λ������ δ���
			if (bitmap == null) {
				viewHolder.iv_news_icon.setImageResource(R.drawable.a4);
				VolleyUtil.getBitmap(path, viewHolder.iv_news_icon, imageCache);

			} else {
				viewHolder.iv_news_icon.setImageBitmap(bitmap);
			}

			viewHolder.tv_news_title.setText(getItem(position).getTitle());
			viewHolder.tv_news_realtype
					.setText(getItem(position).getRealtype());
			viewHolder.tv_news_date.setText(getItem(position).getDate());

		}
		return convertView;
	}

	private static class ViewHolder {
		ImageView iv_news_icon;
		TextView tv_news_title;
		TextView tv_news_realtype;
		TextView tv_news_date;

	}

	private void setConvenientBanner() {
		list.clear();
		list.add(R.drawable.image1);
		list.add(R.drawable.image2);
		list.add(R.drawable.image3);

		// �Զ������Holder��ʵ�ָ��ิ�ӵĽ��棬��һ����ͼƬ��ҳ�������κοؼ���ҳ��ɡ�
		convenientBanner
				.setPages(new CBViewHolderCreator<LocalImageHolderView>() {
					@Override
					public LocalImageHolderView createHolder() {
						return new LocalImageHolderView();
					}
				}, list)
				// ����������ͼƬ��Ϊ��ҳָʾ������������û��ָʾ�������Ը����Լ�������������Լ���ָʾ��,����ҪԲ��ָʾ�����ò���
				.setPageIndicator(
						new int[] { R.drawable.adware_style_default,
								R.drawable.adware_style_selected })
				// ����ָʾ���ķ���
				.setPageIndicatorAlign(
						ConvenientBanner.PageIndicatorAlign.ALIGN_PARENT_RIGHT)
				.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(int position) {
						// TODO Auto-generated method stub
						Toast.makeText(MyApplication.getContext(), "�ֲ������¼�������",
								0).show();

					}
				}).startTurning(2000);
		// ���÷�ҳ��Ч��������Ҫ��ҳЧ�����ò���
		// .setPageTransformer(Transformer.DefaultTransformer);
		// ������Ч֮����а��������°��Ѿ����룬���Ҫ������Ч�����ӿ��Կ�Demo�ĵ����Ӧ��
		// convenientBanner.setManualPageable(false);//���ò����ֶ�Ӱ��
	}

	public class LocalImageHolderView implements Holder<Integer> {
		private ImageView imageView;

		@Override
		public View createView(Context context) {
			imageView = new ImageView(context);
			imageView.setScaleType(ImageView.ScaleType.FIT_XY);
			return imageView;
		}

		@Override
		public void UpdateUI(Context context, final int position, Integer data) {
			imageView.setImageResource(data);
		}
	}
}
