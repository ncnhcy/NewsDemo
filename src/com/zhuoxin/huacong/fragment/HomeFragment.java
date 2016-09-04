package com.zhuoxin.huacong.fragment;

import java.util.ArrayList;
import java.util.List;

import org.xutils.x;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import me.maxwin.view.TabPageIndicator;
import com.zhouxin.huacong.news.R;
import com.zhuoxin.huacong.baseAdapter.HomePagerAdapter;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

@ContentView(R.layout.fragment_home)
public class HomeFragment extends Fragment {
	@ViewInject(R.id.tabPageIndicator)
	TabPageIndicator tabPageIndicator;
	@ViewInject(R.id.vp_home)
	ViewPager vp_home;
	HomePagerAdapter homeAdapter;
	List<Fragment> fragmentList;
	String title[] = new String[] { "�Ƽ�", "����", "����", "����", "����", "�ƾ�",
			"ʱ��" };

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = x.view().inject(this, inflater, container);
		// ���������õ���getFragmentManager�Ļ� �����һ��bug������fragment��Ƕ�׵��£�getItem�������ܷ���
		// ������getChildFragmentManager �����������ȡǶ�׵�fragment
		homeAdapter = new HomePagerAdapter(getChildFragmentManager(), title);
		fragmentList = new ArrayList<Fragment>();
		//fragmentList.add(new HomeNewsFragment());
		fragmentList.add(new HomeNewsScienceFragment());
		fragmentList.add(new HomeNewsCountryFragment());
		fragmentList.add(new HomeNewsOutCountryFragment());
		fragmentList.add(new HomeNewsRecreationFragment());
		fragmentList.add(new HomeNewsMilitaryFragment());
		fragmentList.add(new HomeNewsFininceFragment());
		fragmentList.add(new HomeNewsFishionFragment());
		homeAdapter.setDataToAdapter(fragmentList);
		// ViewPager����Adapter
		vp_home.setAdapter(homeAdapter);
		// ����Indicator��ViewPager�Ĺ���
		boolean tab = tabPageIndicator == null;
		boolean vp = vp_home == null;
		tabPageIndicator.setViewPager(vp_home);
		setTabPagerIndicator();
		return view;
	}

	private void setTabPagerIndicator() {
		tabPageIndicator
				.setIndicatorMode(TabPageIndicator.IndicatorMode.MODE_WEIGHT_NOEXPAND_SAME);// ����ģʽ��һ��Ҫ������ģʽ
		tabPageIndicator.setDividerColor(Color.parseColor("#00bbcf"));// ���÷ָ��ߵ���ɫ
		tabPageIndicator.setDividerPadding(10);// ����
		tabPageIndicator.setIndicatorColor(Color.parseColor("#00FFFF"));// ���õײ������ߵ���ɫ
		tabPageIndicator.setTextColorSelected(Color.parseColor("#F08080"));// ����tab����ѡ�е���ɫ
		tabPageIndicator.setTextColor(Color.parseColor("#FFF0F5"));// ����tab����δ��ѡ�е���ɫ
		// tabPageIndicator.setTextSize(22);// ���������С
	}

}
