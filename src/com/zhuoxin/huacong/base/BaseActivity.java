package com.zhuoxin.huacong.base;

import java.util.ArrayList;
import java.util.Iterator;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Window;

public class BaseActivity extends FragmentActivity {
	private static ArrayList<Activity> activityList = new ArrayList<Activity>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		activityList.add(this);
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	@Override
	public void finish() {
		// TODO Auto-generated method stub
		super.finish();
	}

	public void finishAll() {
		Iterator<Activity> it = activityList.iterator();
		if (it.hasNext()) {
			it.next().finish();
		}
	}

	// 普通跳转
	public void startActivity(Class<?> targetClass) {
		Intent intent = new Intent(this, targetClass);
		startActivity(intent);
	}

	// 带参数的跳转
	public void startActivity(Class<?> targetClass, Bundle bundle) {
		Intent intent = new Intent(this, targetClass);
		intent.putExtras(bundle);
		startActivity(intent);
	}

	/**
	 * 带动画的跳转
	 * 
	 * @param targetClass
	 *            目标Activity
	 * @param inAnimID
	 *            第二个activity传入的动画
	 * @param outAnimID
	 *            本activity传出的动画
	 */
	protected void startActivity(Class<?> targetClass, int inAnimID,
			int outAnimID) {
		Intent intent = new Intent(this, targetClass);
		startActivity(intent);
		overridePendingTransition(inAnimID, outAnimID);
	}

	/**
	 * 带参数的动画跳转
	 * 
	 * @param targetClass
	 *            目标Activity
	 * @param inAnimID
	 *            第二个Activity传入的动画
	 * @param outAnimID
	 *            本个Activity消失的动画
	 * @param bundle
	 *            传入的参数
	 */

	protected void startActivity(Class<?> targetClass, int inAnimID,
			int outAnimID, Bundle bundle) {
		Intent intent = new Intent(this, targetClass);
		intent.putExtras(bundle);
		startActivity(intent);
		overridePendingTransition(inAnimID, outAnimID);
	}

}
