package com.zhuoxin.huacong.news;

import java.util.ArrayList;

import org.xutils.x;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentManager.OnBackStackChangedListener;
import android.support.v4.app.FragmentTransaction;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.zhouxin.huacong.news.R;
import com.zhuoxin.huacong.base.BaseActivity;
import com.zhuoxin.huacong.fragment.CollectionFragment;
import com.zhuoxin.huacong.fragment.HomeFragment;
import com.zhuoxin.huacong.fragment.HomeNewsFragment;
import com.zhuoxin.huacong.fragment.LeftMenuFragment;
import com.zhuoxin.huacong.fragment.LoginFragment;
import com.zhuoxin.huacong.fragment.PhotoFragment;
import com.zhuoxin.huacong.fragment.RegisterFragment;
import com.zhuoxin.huacong.fragment.RightMenuFragment;

@ContentView(R.layout.activity_home)
public class HomeActivity extends BaseActivity {
	@ViewInject(R.id.tv_title)
	TextView tv_title;
	ArrayList<String> titleList = new ArrayList<String>();
	SlidingMenu slidinMenu;
	public Handler handler = new Handler();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		x.view().inject(this);
		initSliding();
		changeToNewsFragment();
	}
	

	private void initSliding() {
		// 构造SlidingMenu
		slidinMenu = new SlidingMenu(this);
		// 设置滑动模式
		slidinMenu.setMode(SlidingMenu.LEFT_RIGHT);
		// 设置触屏模式
		slidinMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
		// 设置滑动菜单视图的宽度
		slidinMenu.setBehindOffsetRes(R.dimen.slidingmenu_behind_offset);
		// 设置渐入间间出效果
		slidinMenu.setFadeDegree(0.35f);
		// 为侧滑菜单设置布局
		slidinMenu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
		slidinMenu.setMenu(R.layout.layout_menu_left);
		slidinMenu.setSecondaryMenu(R.layout.layout_menu_right);
		// fragment操作 获取fragment管理器，开启事务， 替换操作 commit结束事务
		getFragmentManager().beginTransaction()
				.add(R.id.fl_menu_left, new LeftMenuFragment()).commit();
		getFragmentManager().beginTransaction()
				.add(R.id.fl_menu_right, new RightMenuFragment()).commit();

	}

	/**
	 * 注解模式 这里只要指定layout layout里面的布局会自动找到 不用初始化 type指定类型，默认的时onClikListener
	 * 
	 * @param v
	 */
	@Event(value = { R.id.iv_home_left, R.id.iv_share_right }, type = View.OnClickListener.class)
	private void getEvent(View v) {
		switch (v.getId()) {
		case R.id.iv_home_left:
			slidinMenu.showMenu();
			break;
		case R.id.iv_share_right:
			slidinMenu.showSecondaryMenu();
			break;
		}
	}
	
	
	
	/**
	 * 替换成主页fragment
	 */

	public void changeToNewsFragment() {
		tv_title.setText("头条");
		titleList.add("头条");
		FragmentTransaction transaction = getSupportFragmentManager()
				.beginTransaction();

		transaction.add(R.id.fl_home, new HomeNewsFragment());
		// 将fragment放入返回栈进行管理，这样摁返回键的时候就会返回到上一个fragment
		transaction.addToBackStack(null);
		transaction.commit();
		slidinMenu.showContent();

	}
	
	

	/**
	 * 替换成主页fragment
	 */

	public void changeToHomeFragment() {
		tv_title.setText("新闻");
		titleList.add("新闻");
		FragmentTransaction transaction = getSupportFragmentManager()
				.beginTransaction();

		transaction.add(R.id.fl_home, new HomeFragment());
		// 将fragment放入返回栈进行管理，这样摁返回键的时候就会返回到上一个fragment
		transaction.addToBackStack(null);
		transaction.commit();
		slidinMenu.showContent();

	}

	/**
	 * 替换成收藏fragment
	 */

	public void changeToCollectionFragment() {
		tv_title.setText("收藏");
		titleList.add("收藏");
		FragmentTransaction transaction = getSupportFragmentManager()
				.beginTransaction();

		transaction.add(R.id.fl_home, new CollectionFragment());
		transaction.addToBackStack(null);
		transaction.commit();
		slidinMenu.showContent();
	}

	/**
	 * 替换成图片fragment
	 */

	public void changeToPhotoFragment() {
		tv_title.setText("图片");
		titleList.add("图片");
		FragmentTransaction transaction = getSupportFragmentManager()
				.beginTransaction();

		transaction.add(R.id.fl_home, new PhotoFragment());
		transaction.addToBackStack(null);
		transaction.commit();
		slidinMenu.showContent();
	}

	/**
	 * 替换成登录
	 */

	public void changeToLoginFragment() {

		tv_title.setText("登录");
		titleList.add("登录");
		FragmentTransaction transaction = getSupportFragmentManager()
				.beginTransaction();

		transaction.add(R.id.fl_home, new LoginFragment());
		transaction.addToBackStack(null);
		transaction.commit();
		slidinMenu.showContent();
	}

	public void changeToRegisterFragment() {
		tv_title.setText("注册");
		titleList.add("注册");
		FragmentTransaction transaction = getSupportFragmentManager()
				.beginTransaction();

		transaction.add(R.id.fl_home, new RegisterFragment());
		transaction.addToBackStack(null);
		transaction.commit();
		slidinMenu.showContent();
	}

	/**
	 * 重写返回键的方法
	 */
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		// 获取碎片管理器
		FragmentManager fm = getSupportFragmentManager();
		// 判断当返回栈内的数据数量大于一的时候，执行返回栈的顺序返回 小于等于1时，直接finish，避免主页摁返回键出现白板
		if (fm.getBackStackEntryCount() > 1) {
			titleList.remove(titleList.size() - 1);
			tv_title.setText(titleList.get(titleList.size() - 1));
			fm.popBackStack();
		} else {
			//super.onBackPressed();
			finish();
		}

	}

}
