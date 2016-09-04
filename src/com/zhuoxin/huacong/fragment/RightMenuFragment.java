package com.zhuoxin.huacong.fragment;

import java.util.HashMap;
import org.xutils.x;
import org.xutils.common.Callback.CommonCallback;
import org.xutils.http.RequestParams;
import org.xutils.image.ImageOptions;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;
import cn.sharesdk.tencent.qq.QQ;
import com.zhouxin.huacong.news.R;
import com.zhuoxin.huacong.baseAdapter.MyApplication;
import com.zhuoxin.huacong.bean.User;
import com.zhuoxin.huacong.news.HomeActivity;
import com.zhuoxin.huacong.news.PersonnelInfoActivity;
import com.zhuoxin.huacong.utils.JsonUtil;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class RightMenuFragment extends Fragment implements OnClickListener {
	static ImageView iv_login;
	static TextView tv_login;
	LinearLayout ll_share;
	static ImageView iv_qq;
	Boolean isLogin = false;
	static TextView tv_qq_login;
	String name = null;
	String platform;
	String profile_image_url;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_menu_right, null);

		iv_login = (ImageView) view.findViewById(R.id.iv_login);
		tv_login = (TextView) view.findViewById(R.id.tv_login);
		ll_share = (LinearLayout) view.findViewById(R.id.ll_share);
		iv_qq = (ImageView) view.findViewById(R.id.iv_qq);
		tv_qq_login = (TextView) view.findViewById(R.id.tv_qq_login);
		iv_login.setOnClickListener(this);
		tv_login.setOnClickListener(this);
		ll_share.setOnClickListener(this);
		iv_qq.setOnClickListener(this);
		tv_qq_login.setOnClickListener(this);

		SharedPreferences sp = getActivity().getSharedPreferences(
				"AccountConfig", Context.MODE_PRIVATE);
		isLogin = sp.getBoolean("isLogin", false);
		platform = sp.getString("platform", null);
		String nameinfo = sp.getString("name", "������¼");
		String name = sp.getString("name", null);
		String image = sp.getString("image", null);
		if (isLogin == false) {

			tv_login.setText(nameinfo);
			iv_login.setImageResource(R.drawable.biz_pc_main_info_profile_avatar_bg_dark);

		} else {
			tv_login.setText(name);
			getImage(image);
			getActivity().runOnUiThread(new Runnable() {

				@Override
				public void run() {
					setQQInvisible();
				}
			});

		}

		return view;
	}

	/**
	 * ��ť����¼�
	 */
	@Override
	public void onClick(View v) {
		SharedPreferences sp = getActivity().getSharedPreferences(
				"AccountConfig", Context.MODE_PRIVATE);
		isLogin = sp.getBoolean("isLogin", false);

		switch (v.getId()) {
		case R.id.iv_login:

			if (isLogin) {
				Intent intent = new Intent(getActivity(),
						PersonnelInfoActivity.class);
				getActivity().startActivity(intent);

			} else {
				((HomeActivity) getActivity()).changeToLoginFragment();
			}

			break;
		case R.id.tv_login:

			if (isLogin) {
				Intent intent = new Intent(getActivity(),
						PersonnelInfoActivity.class);
				getActivity().startActivity(intent);

			} else {
				((HomeActivity) getActivity()).changeToLoginFragment();
			}
			break;
		case R.id.ll_share:
			showShare();
			break;
		case R.id.iv_qq:
			Boolean isLogin = sp.getBoolean("isLogin", false);

			if (isLogin == true) {
				Toast.makeText(MyApplication.getContext(), "�����˺ŵ�¼", 0).show();
			} else {
				loginqq();
			}
			break;
		case R.id.tv_qq_login:
			isLogin = sp.getBoolean("isLogin", false);

			if (isLogin == true) {
				Toast.makeText(MyApplication.getContext(), "�����˺ŵ�¼", 0).show();
			} else {
				loginqq();
			}
			break;
		}
	}

	private void loginqq() {
		ShareSDK.initSDK(MyApplication.getContext());
		Platform qq = ShareSDK.getPlatform(QQ.NAME);
		qq.setPlatformActionListener(new PlatformActionListener() {

			@Override
			public void onError(Platform arg0, int arg1, Throwable arg2) {
				// TODO Auto-generated method stub
				Log.v("test", "��¼����");
			}

			@Override
			public void onComplete(Platform platform, int arg1,
					HashMap<String, Object> arg2) {
				// TODO Auto-generated method stub
				Log.v("test", "��¼�ɹ�");
				getMessage();
			}

			@Override
			public void onCancel(Platform arg0, int arg1) {
				// TODO Auto-generated method stub
				Log.v("test", "��¼");
			}
		});

		qq.authorize();

	}

	/**
	 * ��¼
	 */

	public void getMessage() {

		ShareSDK.initSDK(MyApplication.getContext());
		Platform qq = ShareSDK.getPlatform(QQ.NAME);
		qq.setPlatformActionListener(new PlatformActionListener() {

			@Override
			public void onError(Platform arg0, int arg1, Throwable arg2) {
				// TODO Auto-generated method stub
				Log.v("test", "��¼����");
			}

			@Override
			public void onComplete(Platform arg0, int arg1,
					HashMap<String, Object> arg2) {

				Log.v("test", "" + arg2);
				profile_image_url = arg2.get("figureurl_qq_2").toString();
				final String name = arg2.get("nickname").toString();
				ImageOptions options = new ImageOptions.Builder().setCircular(
						true).build();
				x.image().bind(iv_login, profile_image_url, options);

				SharedPreferences sp = getActivity().getSharedPreferences(
						"AccountConfig", Context.MODE_PRIVATE);
				sp.edit().putString("platform", "QQ").putString("name", name)
						.putString("token", null)
						.putString("image", profile_image_url)
						.putBoolean("isLogin", true).commit();
				((HomeActivity) getActivity()).handler.post(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						setQQInvisible();
						getTitle(name);
					}
				});

			}

			@Override
			public void onCancel(Platform arg0, int arg1) {
				// TODO Auto-generated method stub
				Log.v("test", "��¼ʧ��");
			}
		});
		qq.showUser(null);
	}

	

	// �������
	private void showShare() {
		ShareSDK.initSDK(MyApplication.getContext());
		OnekeyShare oks = new OnekeyShare();
		// �ر�sso��Ȩ
		oks.disableSSOWhenAuthorize();

		// ����ʱNotification��ͼ������� 2.5.9�Ժ�İ汾�����ô˷���
		// oks.setNotification(R.drawable.ic_launcher,
		// getString(R.string.app_name));
		// title���⣬ӡ��ʼǡ����䡢��Ϣ��΢�š���������QQ�ռ�ʹ��
		oks.setTitle("����");
		// titleUrl�Ǳ�����������ӣ�������������QQ�ռ�ʹ��
		oks.setTitleUrl("http://www.baidu.com");
		// text�Ƿ����ı�������ƽ̨����Ҫ����ֶ�
		oks.setText("���Ƿ����ı�");
		// imagePath��ͼƬ�ı���·����Linked-In�����ƽ̨��֧�ִ˲���
		// oks.setImagePath("/sdcard/test.jpg");//ȷ��SDcard������ڴ���ͼƬ
		// url����΢�ţ��������Ѻ�����Ȧ����ʹ��
		oks.setUrl("http://sharesdk.cn");
		// comment���Ҷ�������������ۣ�������������QQ�ռ�ʹ��
		oks.setComment("���ǲ��������ı�");
		// site�Ƿ�������ݵ���վ���ƣ�����QQ�ռ�ʹ��
		oks.setSite(getString(R.string.app_name));
		// siteUrl�Ƿ�������ݵ���վ��ַ������QQ�ռ�ʹ��
		oks.setSiteUrl("http://sharesdk.cn");

		// ��������GUI
		oks.show(MyApplication.getContext());

	}

	public static void getTitle(String title) {
		tv_login.setText(title);
	}

	public static void getImage(int i) {
		iv_login.setImageResource(i);
	}

	public static void getImage( String r) {
		ImageOptions option = new ImageOptions.Builder().setCircular(true)
				.build();
		// Xutils����ͼƬ
		x.image().bind(iv_login, r, option);
	}

	public static void setQQInvisible() {
		iv_qq.setVisibility(View.INVISIBLE);
		tv_qq_login.setVisibility(View.INVISIBLE);
	}

	public static void setQQVisible() {
		iv_qq.setVisibility(View.VISIBLE);
		tv_qq_login.setVisibility(View.VISIBLE);
	}

}
