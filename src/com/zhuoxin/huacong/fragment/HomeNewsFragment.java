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
		// ��������������ˢ��
		xlv_news.setPullLoadEnable(false);
		xlv_news.setPullRefreshEnable(true);
		// �������� ����������
		xlv_news.setXListViewListener(xListViewListener);
		newsAdapter = new NewsAdapter(getActivity());
		xlv_news.setAdapter(newsAdapter);
		xlv_news.setOnItemClickListener(xlv_ItemClickListener);
		xlv_news.setOnItemLongClickListener(longClickListener);
		xListViewListener.onRefresh();
		return view;
	}

	private IXListViewListener xListViewListener = new IXListViewListener() {
		// ����ˢ��
		@Override
		public void onRefresh() {
			if (HttpUtils.hasNetConnected()) {
				VolleyUtil.getHttp(PATH, HomeNewsFragment.this);

			} else {
				Looper.prepare();
				Toast.makeText(MyApplication.getContext(),
						"����״̬�쳣�����������Ƿ�����...", Toast.LENGTH_SHORT).show();
				Looper.loop();
			}
		}

		// ��������
		@Override
		public void onLoadMore() {

		}
	};

	// ������Ŀ����������¼�
	private OnItemClickListener xlv_ItemClickListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			// �������� ���淽ʽ��ͨ��Bundle���д���
			Intent intent = new Intent(getActivity(), NewsPagerActivity.class);
			NewsBean news = (NewsBean) parent.getItemAtPosition(position);
			intent.putExtra("news", news);
			getActivity().startActivity(intent);

		}
	};

	// �����������ѡ�
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

	// ��ȡjson���ݳɹ�
	@SuppressLint("SimpleDateFormat")
	@Override
	public void getJsonSuccess(String json) {
		// ��ȡjson���ݳɹ�
		if (json != null) {
			List<NewsBean> newsList = JsonUtil.parseJson(json);

			// �ύ���ݵ�Adapter֪ͨ���ݸı�
			newsAdapter.setDataToAdapter(newsList);
			((HomeActivity) getActivity()).handler.post(new Runnable() {

				@Override
				public void run() {
					newsAdapter.notifyDataSetInvalidated();
					// ����ֹͣˢ�µķ���
					xlv_news.stopRefresh();
					// ����ˢ��ʱ��
					Date date = new Date();
					SimpleDateFormat simpleDate = new SimpleDateFormat(
							"HH:mm:ss");
					String time = simpleDate.format(date);
					xlv_news.setRefreshTime(time);
				}
			});
		}
	}

	// ��ȡjson����ʧ�� �ص�����
	@Override
	public void getJsonDefeat() {
		// ��ȡjson����ʧ��
		Toast.makeText(MyApplication.getContext(), "��ȡJson���ݳ���,��������",
				Toast.LENGTH_SHORT).show();
	}
	
	

}
