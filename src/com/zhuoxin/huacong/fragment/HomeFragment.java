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
	String title[] = new String[] { "科技", "国内", "国际", "娱乐", "军事", "财经",
			"时尚" };

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = x.view().inject(this, inflater, container);
		// 这里的如果用的是getFragmentManager的话 会造成一个bug，由于fragment的嵌套导致，getItem（）不能访问
		// 必须用getChildFragmentManager 这个方法来获取嵌套的fragment
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
		// ViewPager设置Adapter
		vp_home.setAdapter(homeAdapter);
		// 设置Indicator和ViewPager的关联
		boolean tab = tabPageIndicator == null;
		boolean vp = vp_home == null;
		tabPageIndicator.setViewPager(vp_home);
		setTabPagerIndicator();
		return view;
	}

	private void setTabPagerIndicator() {
		tabPageIndicator
				.setIndicatorMode(TabPageIndicator.IndicatorMode.MODE_WEIGHT_NOEXPAND_SAME);// 设置模式，一定要先设置模式
		tabPageIndicator.setDividerColor(Color.parseColor("#00bbcf"));// 设置分割线的颜色
		tabPageIndicator.setDividerPadding(10);// 设置
		tabPageIndicator.setIndicatorColor(Color.parseColor("#00FFFF"));// 设置底部导航线的颜色
		tabPageIndicator.setTextColorSelected(Color.parseColor("#F08080"));// 设置tab标题选中的颜色
		tabPageIndicator.setTextColor(Color.parseColor("#FFF0F5"));// 设置tab标题未被选中的颜色
		// tabPageIndicator.setTextSize(22);// 设置字体大小
	}

}
