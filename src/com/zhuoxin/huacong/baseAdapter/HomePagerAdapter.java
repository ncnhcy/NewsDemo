package com.zhuoxin.huacong.baseAdapter;

import java.util.ArrayList;
import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class HomePagerAdapter extends FragmentPagerAdapter {
	List<Fragment> fragmentList;
	String title[];

	public HomePagerAdapter(FragmentManager fm, String title[]) {
		super(fm);
		fragmentList = new ArrayList<Fragment>();
		this.title = title;
	}

	// 添加数据的方法
	public void setDataToAdapter(List<Fragment> list) {
		fragmentList.clear();
		fragmentList.addAll(list);
	}

	@Override
	public Fragment getItem(int arg0) {
		// TODO Auto-generated method stub
		return fragmentList.get(arg0);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return fragmentList.size();
	}

	@Override
	public CharSequence getPageTitle(int position) {
		// TODO Auto-generated method stub
		return  title[position];
	}
}
