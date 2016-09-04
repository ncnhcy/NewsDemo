package com.zhuoxin.huacong.news;

import java.util.List;
import org.xutils.x;
import org.xutils.common.Callback.CommonCallback;
import org.xutils.http.RequestParams;
import org.xutils.image.ImageOptions;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.tencent.qq.QQ;

import com.zhouxin.huacong.news.R;
import com.zhuoxin.huacong.adapter.LoginLogAdapter;
import com.zhuoxin.huacong.base.BaseActivity;
import com.zhuoxin.huacong.baseAdapter.MyApplication;
import com.zhuoxin.huacong.bean.User;
import com.zhuoxin.huacong.fragment.RightMenuFragment;
import com.zhuoxin.huacong.utils.JsonUtil;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

@ContentView(R.layout.fragment_personnelinfo)
public class PersonnelInfoActivity extends BaseActivity {
	@ViewInject(R.id.tv_logoutinfo)
	TextView tv_logoutinfo;
	@ViewInject(R.id.tv_login)
	TextView tv_login;
	@ViewInject(R.id.tv_integral)
	TextView tv_integral;
	@ViewInject(R.id.tv_count)
	TextView tv_count;
	@ViewInject(R.id.lv_log)
	ListView lv_log;
	@ViewInject(R.id.iv_login)
	ImageView iv_login;
	LoginLogAdapter adapter = new LoginLogAdapter(MyApplication.getContext());
	List<User.LoginMessage> loginList = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		x.view().inject(this);

		initView();

	}

	public void initView() {
		String imei = ((TelephonyManager) getSystemService(TELEPHONY_SERVICE))
				.getDeviceId();
		String token = getSharedPreferences("AccountConfig",
				Context.MODE_PRIVATE).getString("token", null);
		RequestParams params = new RequestParams(
				"http://118.244.212.82:9092/newsClient/user_home");
		params.addQueryStringParameter("ver", "1");
		params.addQueryStringParameter("imei", imei);
		params.addQueryStringParameter("token", token);
		x.http().post(params, new CommonCallback<String>() {

			@Override
			public void onCancelled(CancelledException arg0) {
				// TODO Auto-generated method stub
				Toast.makeText(MyApplication.getContext(), "服务器连接失败", 0).show();
			}

			@Override
			public void onError(Throwable arg0, boolean arg1) {
				// TODO Auto-generated method stub
				Toast.makeText(MyApplication.getContext(), "服务器连接错误", 0).show();
				Log.v("test", "" + arg0 + ":" + arg1);
			}

			@Override
			public void onSuccess(String arg0) {
				SharedPreferences sp = getSharedPreferences("AccountConfig",
						Context.MODE_PRIVATE);
				String platform = sp.getString("platform", null);
				String image = sp.getString("image", null);
				String name = sp.getString("name", null);

				if (platform.equals("zhuoxin")) {
					User user = JsonUtil.preaseResourceInfo(arg0);
					tv_login.setText(user.getUid());
					tv_count.setText(user.getComnum() + "");
					tv_integral.setText(user.getIntegration() + "");
					// 设置图片为圆形
					ImageOptions option = new ImageOptions.Builder()
							.setCircular(true).build();
					// Xutils加载图片
					x.image().bind(iv_login, user.getPortrait(), option);
					sp.edit().putString("image", user.getPortrait()).commit();
					loginList = user.getLoginlog();
					adapter.setDataToAdapter(loginList);
					adapter.notifyDataSetChanged();
					lv_log.setAdapter(adapter);
				} else {
					tv_login.setText(name);
					tv_count.setText("非官方账号暂无统计信息");
					tv_integral.setText("非官方账号暂无积分信息");
					// 设置图片为圆形
					ImageOptions option = new ImageOptions.Builder()
							.setCircular(true).build();
					// Xutils加载图片
					x.image().bind(iv_login, image, option);
				}

			}

			@Override
			public void onFinished() {
				// TODO Auto-generated method stub
				Toast.makeText(MyApplication.getContext(), "获取服务器数据结束", 0)
						.show();
			}

		});
	}

	@Event(value = { R.id.tv_logoutinfo })
	private void getListener(View v) {
		SharedPreferences sp = getSharedPreferences("AccountConfig",
				Context.MODE_PRIVATE);
		// sp.edit().putBoolean("isLogin", false).commit();
		ShareSDK.initSDK(MyApplication.getContext());
		Platform qq = ShareSDK.getPlatform(QQ.NAME);
		if (qq.isValid()) {
			qq.removeAccount();
		}
		sp.edit().clear().commit();
		RightMenuFragment.setQQVisible();
		RightMenuFragment.getTitle("点击登录");
		RightMenuFragment
				.getImage(R.drawable.biz_pc_main_info_profile_avatar_bg_dark);
		finish();
	}

}
