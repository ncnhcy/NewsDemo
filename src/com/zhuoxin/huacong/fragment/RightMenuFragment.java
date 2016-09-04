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
		String nameinfo = sp.getString("name", "立即登录");
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
	 * 按钮点击事件
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
				Toast.makeText(MyApplication.getContext(), "已有账号登录", 0).show();
			} else {
				loginqq();
			}
			break;
		case R.id.tv_qq_login:
			isLogin = sp.getBoolean("isLogin", false);

			if (isLogin == true) {
				Toast.makeText(MyApplication.getContext(), "已有账号登录", 0).show();
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
				Log.v("test", "登录错误");
			}

			@Override
			public void onComplete(Platform platform, int arg1,
					HashMap<String, Object> arg2) {
				// TODO Auto-generated method stub
				Log.v("test", "登录成功");
				getMessage();
			}

			@Override
			public void onCancel(Platform arg0, int arg1) {
				// TODO Auto-generated method stub
				Log.v("test", "登录");
			}
		});

		qq.authorize();

	}

	/**
	 * 登录
	 */

	public void getMessage() {

		ShareSDK.initSDK(MyApplication.getContext());
		Platform qq = ShareSDK.getPlatform(QQ.NAME);
		qq.setPlatformActionListener(new PlatformActionListener() {

			@Override
			public void onError(Platform arg0, int arg1, Throwable arg2) {
				// TODO Auto-generated method stub
				Log.v("test", "登录错误");
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
				Log.v("test", "登录失败");
			}
		});
		qq.showUser(null);
	}

	

	// 分享操作
	private void showShare() {
		ShareSDK.initSDK(MyApplication.getContext());
		OnekeyShare oks = new OnekeyShare();
		// 关闭sso授权
		oks.disableSSOWhenAuthorize();

		// 分享时Notification的图标和文字 2.5.9以后的版本不调用此方法
		// oks.setNotification(R.drawable.ic_launcher,
		// getString(R.string.app_name));
		// title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
		oks.setTitle("标题");
		// titleUrl是标题的网络链接，仅在人人网和QQ空间使用
		oks.setTitleUrl("http://www.baidu.com");
		// text是分享文本，所有平台都需要这个字段
		oks.setText("我是分享文本");
		// imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
		// oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
		// url仅在微信（包括好友和朋友圈）中使用
		oks.setUrl("http://sharesdk.cn");
		// comment是我对这条分享的评论，仅在人人网和QQ空间使用
		oks.setComment("我是测试评论文本");
		// site是分享此内容的网站名称，仅在QQ空间使用
		oks.setSite(getString(R.string.app_name));
		// siteUrl是分享此内容的网站地址，仅在QQ空间使用
		oks.setSiteUrl("http://sharesdk.cn");

		// 启动分享GUI
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
		// Xutils加载图片
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
