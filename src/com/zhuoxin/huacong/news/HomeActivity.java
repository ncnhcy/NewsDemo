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
		// ����SlidingMenu
		slidinMenu = new SlidingMenu(this);
		// ���û���ģʽ
		slidinMenu.setMode(SlidingMenu.LEFT_RIGHT);
		// ���ô���ģʽ
		slidinMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
		// ���û����˵���ͼ�Ŀ��
		slidinMenu.setBehindOffsetRes(R.dimen.slidingmenu_behind_offset);
		// ���ý������Ч��
		slidinMenu.setFadeDegree(0.35f);
		// Ϊ�໬�˵����ò���
		slidinMenu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
		slidinMenu.setMenu(R.layout.layout_menu_left);
		slidinMenu.setSecondaryMenu(R.layout.layout_menu_right);
		// fragment���� ��ȡfragment���������������� �滻���� commit��������
		getFragmentManager().beginTransaction()
				.add(R.id.fl_menu_left, new LeftMenuFragment()).commit();
		getFragmentManager().beginTransaction()
				.add(R.id.fl_menu_right, new RightMenuFragment()).commit();

	}

	/**
	 * ע��ģʽ ����ֻҪָ��layout layout����Ĳ��ֻ��Զ��ҵ� ���ó�ʼ�� typeָ�����ͣ�Ĭ�ϵ�ʱonClikListener
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
	 * �滻����ҳfragment
	 */

	public void changeToNewsFragment() {
		tv_title.setText("ͷ��");
		titleList.add("ͷ��");
		FragmentTransaction transaction = getSupportFragmentManager()
				.beginTransaction();

		transaction.add(R.id.fl_home, new HomeNewsFragment());
		// ��fragment���뷵��ջ���й������������ؼ���ʱ��ͻ᷵�ص���һ��fragment
		transaction.addToBackStack(null);
		transaction.commit();
		slidinMenu.showContent();

	}
	
	

	/**
	 * �滻����ҳfragment
	 */

	public void changeToHomeFragment() {
		tv_title.setText("����");
		titleList.add("����");
		FragmentTransaction transaction = getSupportFragmentManager()
				.beginTransaction();

		transaction.add(R.id.fl_home, new HomeFragment());
		// ��fragment���뷵��ջ���й������������ؼ���ʱ��ͻ᷵�ص���һ��fragment
		transaction.addToBackStack(null);
		transaction.commit();
		slidinMenu.showContent();

	}

	/**
	 * �滻���ղ�fragment
	 */

	public void changeToCollectionFragment() {
		tv_title.setText("�ղ�");
		titleList.add("�ղ�");
		FragmentTransaction transaction = getSupportFragmentManager()
				.beginTransaction();

		transaction.add(R.id.fl_home, new CollectionFragment());
		transaction.addToBackStack(null);
		transaction.commit();
		slidinMenu.showContent();
	}

	/**
	 * �滻��ͼƬfragment
	 */

	public void changeToPhotoFragment() {
		tv_title.setText("ͼƬ");
		titleList.add("ͼƬ");
		FragmentTransaction transaction = getSupportFragmentManager()
				.beginTransaction();

		transaction.add(R.id.fl_home, new PhotoFragment());
		transaction.addToBackStack(null);
		transaction.commit();
		slidinMenu.showContent();
	}

	/**
	 * �滻�ɵ�¼
	 */

	public void changeToLoginFragment() {

		tv_title.setText("��¼");
		titleList.add("��¼");
		FragmentTransaction transaction = getSupportFragmentManager()
				.beginTransaction();

		transaction.add(R.id.fl_home, new LoginFragment());
		transaction.addToBackStack(null);
		transaction.commit();
		slidinMenu.showContent();
	}

	public void changeToRegisterFragment() {
		tv_title.setText("ע��");
		titleList.add("ע��");
		FragmentTransaction transaction = getSupportFragmentManager()
				.beginTransaction();

		transaction.add(R.id.fl_home, new RegisterFragment());
		transaction.addToBackStack(null);
		transaction.commit();
		slidinMenu.showContent();
	}

	/**
	 * ��д���ؼ��ķ���
	 */
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		// ��ȡ��Ƭ������
		FragmentManager fm = getSupportFragmentManager();
		// �жϵ�����ջ�ڵ�������������һ��ʱ��ִ�з���ջ��˳�򷵻� С�ڵ���1ʱ��ֱ��finish��������ҳ�����ؼ����ְװ�
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
