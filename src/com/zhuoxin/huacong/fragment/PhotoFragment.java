package com.zhuoxin.huacong.fragment;

import java.util.ArrayList;
import java.util.List;
import com.zhouxin.huacong.news.R;
import com.zhuoxin.huacong.adapter.PhotoAdapter;
import com.zhuoxin.huacong.utils.ImageLoader;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.LinearLayout;

public class PhotoFragment extends Fragment {
	RecyclerView rv_photo;
	List<String> data = new ArrayList<String>();
	PhotoAdapter adapter;
	List<String> list = ImageLoader.ImageQuary();

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.fragment_photo, null);
		rv_photo = (RecyclerView) view.findViewById(R.id.rv_photo);
		for (int i = 0; i <list.size(); i++) {
			data.add("" + i);
		}
		adapter = new PhotoAdapter(data);
		rv_photo.setAdapter(adapter);
		// rv.setLayoutManager(new LinearLayoutManager(this));
		// gridLayoutManager表示表格式 第二个参数表示显示几行或者是几列 第三个参数 表示展示的方向 第四个参数表示是否反向拉动
		rv_photo.setLayoutManager(new StaggeredGridLayoutManager(3,
				GridLayout.VERTICAL));
		return view;
	}

}
