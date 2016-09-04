package com.zhuoxin.huacong.baseAdapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

public class BaseViewPagerAdapter extends PagerAdapter {
	private Context context;
	private ArrayList<View> pagerList = new ArrayList<View>();

	public BaseViewPagerAdapter(Context context) {
		super();
		this.context = context;
	}

	public ArrayList<View> getViewList() {
		return pagerList;
	}
	
	public void addViewToAdapter(View view){
		pagerList.add(view);
	}
	

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return pagerList.size();
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		// TODO Auto-generated method stub
		View view = pagerList.get(position);
		container.addView(view);
		return view;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		// TODO Auto-generated method stub
		View view = pagerList.get(position);
		container.removeView(view);
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		// TODO Auto-generated method stub
		return arg0 == arg1;
	}
	
}
