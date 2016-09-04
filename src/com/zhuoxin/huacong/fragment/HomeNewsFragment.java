package com.zhuoxin.huacong.fragment;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import me.maxwin.view.XListView;
import me.maxwin.view.XListView.IXListViewListener;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager.LayoutParams;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.zhouxin.huacong.news.R;
import com.zhuoxin.huacong.adapter.NewsAdapter;
import com.zhuoxin.huacong.baseAdapter.MyApplication;
import com.zhuoxin.huacong.bean.NewsBean;
import com.zhuoxin.huacong.callback.VolleyCallBack;
import com.zhuoxin.huacong.news.HomeActivity;
import com.zhuoxin.huacong.news.NewsPagerActivity;
import com.zhuoxin.huacong.utils.DBUtils;
import com.zhuoxin.huacong.utils.HttpUtils;
import com.zhuoxin.huacong.utils.JsonUtil;
import com.zhuoxin.huacong.utils.VolleyUtil;

public class HomeNewsFragment extends Fragment implements VolleyCallBack {

	NewsAdapter newsAdapter;
	XListView xlv_news;
	String json = null;
	Button btn_affirm = null;
	Button btn_cancel = null;
	PopupWindow pop = null;
	NewsBean news;

	final String PATH = "http://v.juhe.cn/toutiao/index?type=&key=d728ab4e75e137c4f23aec12ed3ee6cd";

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.fragment_home_news, null);
		xlv_news = (XListView) view.findViewById(R.id.xlv_news);
		// 设置上拉和下拉刷新
		xlv_news.setPullLoadEnable(false);
		xlv_news.setPullRefreshEnable(true);
		// 设置上拉 下拉的侦听
		xlv_news.setXListViewListener(xListViewListener);
		newsAdapter = new NewsAdapter(getActivity());
		xlv_news.setAdapter(newsAdapter);
		xlv_news.setOnItemClickListener(xlv_ItemClickListener);
		xlv_news.setOnItemLongClickListener(longClickListener);
		xListViewListener.onRefresh();
		return view;
	}

	private IXListViewListener xListViewListener = new IXListViewListener() {
		// 下拉刷新
		@Override
		public void onRefresh() {
			if (HttpUtils.hasNetConnected()) {
				VolleyUtil.getHttp(PATH, HomeNewsFragment.this);

			} else {
				Looper.prepare();
				Toast.makeText(MyApplication.getContext(),
						"网络状态异常，请检查网络是否连接...", Toast.LENGTH_SHORT).show();
				Looper.loop();
			}
		}

		// 上拉加载
		@Override
		public void onLoadMore() {

		}
	};

	// 设置条目点击的侦听事件
	private OnItemClickListener xlv_ItemClickListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			// 传递数据 常规方式是通过Bundle进行传递
			Intent intent = new Intent(getActivity(), NewsPagerActivity.class);
			NewsBean news = (NewsBean) parent.getItemAtPosition(position);
			intent.putExtra("news", news);
			getActivity().startActivity(intent);

		}
	};

	// 长按弹窗提醒。
	private OnItemLongClickListener longClickListener = new OnItemLongClickListener() {

		@SuppressLint("InflateParams")
		@Override
		public boolean onItemLongClick(AdapterView<?> parent, View view,
				final int position, long id) {

			View v = getActivity().getLayoutInflater().inflate(
					R.layout.layout_popup, null);
			news = (NewsBean) parent.getItemAtPosition(position);
			pop = new PopupWindow(v, LayoutParams.WRAP_CONTENT,
					LayoutParams.WRAP_CONTENT, true);
			pop.showAtLocation(view, Gravity.CENTER, 0, 0);
			btn_affirm = (Button) v.findViewById(R.id.btn_affirm);
			btn_cancel = (Button) v.findViewById(R.id.btn_cancel);
			btn_affirm.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					DBUtils.addNews(news);
					pop.dismiss();
				}
			});
			btn_cancel.setOnClickListener(listener);
			return true;
		}
	};

	public OnClickListener listener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.btn_cancel:
				pop.dismiss();
				break;
			}
		}
	};

	// 获取json数据成功
	@SuppressLint("SimpleDateFormat")
	@Override
	public void getJsonSuccess(String json) {
		// 获取json数据成功
		if (json != null) {
			List<NewsBean> newsList = JsonUtil.parseJson(json);

			// 提交数据到Adapter通知数据改变
			newsAdapter.setDataToAdapter(newsList);
			((HomeActivity) getActivity()).handler.post(new Runnable() {

				@Override
				public void run() {
					newsAdapter.notifyDataSetInvalidated();
					// 调用停止刷新的方法
					xlv_news.stopRefresh();
					// 设置刷新时间
					Date date = new Date();
					SimpleDateFormat simpleDate = new SimpleDateFormat(
							"HH:mm:ss");
					String time = simpleDate.format(date);
					xlv_news.setRefreshTime(time);
				}
			});
		}
	}

	// 获取json数据失败 回调方法
	@Override
	public void getJsonDefeat() {
		// 获取json数据失败
		Toast.makeText(MyApplication.getContext(), "获取Json数据出错,请检查网络",
				Toast.LENGTH_SHORT).show();
	}
	
	

}
