package com.zhuoxin.huacong.fragment;

import java.util.ArrayList;
import java.util.List;

import org.xutils.x;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import com.zhouxin.huacong.news.R;
import com.zhuoxin.huacong.adapter.CollectionAdapter;
import com.zhuoxin.huacong.adapter.NewsAdapter;
import com.zhuoxin.huacong.bean.NewsBean;
import com.zhuoxin.huacong.news.NewsPagerActivity;
import com.zhuoxin.huacong.utils.DBUtils;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
@ContentView(R.layout.fragment_collection)
public class CollectionFragment extends Fragment {
	List<NewsBean> newsList;
	CollectionAdapter collectionAdapter;
	@ViewInject(R.id.lv_collection)
	ListView lv_coloction;

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = x.view().inject(this, inflater, container);
		newsList = new ArrayList<NewsBean>();
		newsList = DBUtils.quaryNews();
		collectionAdapter = new CollectionAdapter(getActivity());
		collectionAdapter.setDataToAdapter(newsList);
		lv_coloction.setAdapter(collectionAdapter);
		lv_coloction.setOnItemClickListener(listener);
		return view;
	}
	
	
	
	
	private OnItemClickListener listener = new OnItemClickListener() {

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
}
