package com.zhuoxin.huacong.baseAdapter;

import java.util.ArrayList;
import java.util.List;

import android.R.layout;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public abstract class MyBaseAdapter<E> extends BaseAdapter {

	protected ArrayList<E> viewsList = new ArrayList<E>();
	protected Context context;
	protected LayoutInflater layoutInflater;

	public MyBaseAdapter(Context context) {
		super();
		layoutInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	public void setDataToAdapter(List<E> list) {
		viewsList.clear();
		viewsList.addAll(list);
	}

	public void addDataToAdapter(E e) {
		viewsList.add(e);
	}

	public void addDataToAdapter(List<E> list) {
		viewsList.addAll(list);
	}

	protected void removeDataToAdapter(E e) {
		viewsList.remove(e);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return viewsList.size();
	}

	@Override
	public E getItem(int position) {
		// TODO Auto-generated method stub
		return viewsList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public abstract View getView(int position, View convertView,
			ViewGroup parent);

}
