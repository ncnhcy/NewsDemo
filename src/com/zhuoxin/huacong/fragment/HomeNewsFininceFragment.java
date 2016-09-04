package com.zhuoxin.huacong.fragment;

import java.util.List;

import org.xutils.x;
import org.xutils.common.Callback.CancelledException;
import org.xutils.common.Callback.CommonCallback;
import org.xutils.http.RequestParams;
import org.xutils.image.ImageOptions;

import me.maxwin.view.XListView;
import me.maxwin.view.XListView.IXListViewListener;
import com.zhouxin.huacong.news.R;
import com.zhuoxin.huacong.adapter.NewsSecienceAdapter;
import com.zhuoxin.huacong.baseAdapter.MyApplication;
import com.zhuoxin.huacong.bean.NewsBean;
import com.zhuoxin.huacong.bean.NewsItemBean;
import com.zhuoxin.huacong.bean.User;
import com.zhuoxin.huacong.news.HomeActivity;
import com.zhuoxin.huacong.news.NewsPagerActivity;
import com.zhuoxin.huacong.news.PersonnelInfoActivity;
import com.zhuoxin.huacong.news.WebPagerActivity;
import com.zhuoxin.huacong.utils.HttpUtils;
import com.zhuoxin.huacong.utils.JsonUtil;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class HomeNewsFininceFragment extends Fragment {
	XListView xlv_science;
	NewsSecienceAdapter scienceAdapter;
	String json = null;

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.fragment_home_science, null);
		xlv_science = (XListView) view.findViewById(R.id.xlv_science);
		xlv_science.setPullLoadEnable(false);
		xlv_science.setPullRefreshEnable(true);
		scienceAdapter = new NewsSecienceAdapter(getActivity());
		xlv_science.setAdapter(scienceAdapter);
		xlv_science.setXListViewListener(xlvListener);
		xlv_science.setOnItemClickListener(xlv_ItemClickListener);
		xlvListener.onRefresh();
		return view;
	}

	// http://v.juhe.cn/toutiao/index?type=keji&key=d728ab4e75e137c4f23aec12ed3ee6cd

	private IXListViewListener xlvListener = new IXListViewListener() {

		@Override
		public void onRefresh() {
			if (HttpUtils.hasNetConnected()) {
				new Thread(new Runnable() {

					@Override
					public void run() {
						json = HttpUtils
								.HttpVisit("http://v.juhe.cn/toutiao/index?type=caijing&key=d728ab4e75e137c4f23aec12ed3ee6cd");
						if (json != null) {
							List<NewsItemBean> scienceList = JsonUtil
									.parseScienceJson(json);
							scienceAdapter.setDataToAdapter(scienceList);
							((HomeActivity) getActivity()).handler
									.post(new Runnable() {

										@Override
										public void run() {
											scienceAdapter
													.notifyDataSetInvalidated();
											xlv_science.stopRefresh();
											xlv_science.setRefreshTime("刚刚...");
										}
									});

						}
					}
				}).start();
			} else {
				Looper.prepare();
				Toast.makeText(MyApplication.getContext(),
						"网络状态异常，请检查网络是否连接...", Toast.LENGTH_SHORT).show();
				Looper.loop();
			}
		}

		@Override
		public void onLoadMore() {
			// TODO Auto-generated method stub

		}
	};

	// 设置条目点击的侦听事件
	private OnItemClickListener xlv_ItemClickListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			// 传递数据 常规方式是通过Bundle进行传递
			Intent intent = new Intent(getActivity(),
					WebPagerActivity.class);
			NewsItemBean science = (NewsItemBean) parent
					.getItemAtPosition(position);
			intent.putExtra("title", "财经");
			intent.putExtra("science", science);
			getActivity().startActivity(intent);

		}
	};


}
