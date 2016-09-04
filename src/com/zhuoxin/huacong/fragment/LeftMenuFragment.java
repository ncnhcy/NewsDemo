package com.zhuoxin.huacong.fragment;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import com.zhouxin.huacong.news.R;
import com.zhuoxin.huacong.baseAdapter.MyApplication;
import com.zhuoxin.huacong.news.HomeActivity;

public class LeftMenuFragment extends Fragment implements OnClickListener {
	RelativeLayout rl_news;
	RelativeLayout rl_favrite;
	RelativeLayout rl_photo;
	RelativeLayout rl_commeet;

	@SuppressLint("InflateParams")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.fragment_menu_left, null);
		rl_news = (RelativeLayout) view.findViewById(R.id.rl_news);
		rl_favrite = (RelativeLayout) view.findViewById(R.id.rl_favrite);
		rl_photo = (RelativeLayout) view.findViewById(R.id.rl_photo);
		rl_commeet = (RelativeLayout)view.findViewById(R.id.rl_commeet);
		rl_news.setOnClickListener(this);
		rl_favrite.setOnClickListener(this);
		rl_photo.setOnClickListener(this);
		rl_commeet.setOnClickListener(this);
		return view;

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.rl_news:
			((HomeActivity) getActivity()).changeToHomeFragment();
			break;
		case R.id.rl_favrite:
			((HomeActivity) getActivity()).changeToCollectionFragment();
			break;
		case R.id.rl_photo:
			((HomeActivity) getActivity()).changeToPhotoFragment();
			break;
		case R.id.rl_commeet:
			((HomeActivity) getActivity()).changeToNewsFragment();
			break;
		}

	}

}
