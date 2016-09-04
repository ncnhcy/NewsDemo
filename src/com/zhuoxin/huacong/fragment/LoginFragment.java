package com.zhuoxin.huacong.fragment;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.x;
import org.xutils.common.Callback.CommonCallback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import com.zhouxin.huacong.news.R;
import com.zhuoxin.huacong.baseAdapter.MyApplication;
import com.zhuoxin.huacong.news.ForgetActivity;
import com.zhuoxin.huacong.news.HomeActivity;
import com.zhuoxin.huacong.news.PersonnelInfoActivity;
import com.zhuoxin.huacong.utils.MD5Util;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

@ContentView(R.layout.fragment_login)
public class LoginFragment extends Fragment {
	@ViewInject(R.id.et_name)
	EditText et_name;
	@ViewInject(R.id.et_password)
	EditText et_password;

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return x.view().inject(this, inflater, container);
	}

	@Event(value = { R.id.tv_register, R.id.tv_forgetregister, R.id.btn_login })
	private void getEvent(View v) {
		switch (v.getId()) {

		case R.id.btn_login:
			final String name = et_name.getText().toString();
			final String password = et_password.getText().toString();
			String mdPassword = MD5Util.getMessageDigstData(password);

			Log.v("test", name + ":" + password);

			RequestParams params = new RequestParams(
					"http://118.244.212.82:9092/newsClient/user_login");
			params.addQueryStringParameter("ver", "1");
			params.addQueryStringParameter("uid", name);
			params.addQueryStringParameter("pwd", mdPassword);
			params.addQueryStringParameter("device", "0");

			x.http().post(params, new CommonCallback<String>() {

				@Override
				public void onCancelled(CancelledException arg0) {
					Log.v("test", "与服务器连接失败");

				}

				@Override
				public void onError(Throwable arg0, boolean arg1) {
					Log.v("test", "与服务器连接错误");

				}

				@Override
				public void onSuccess(String arg0) {
					Log.v("test", "连接成功" + arg0);
					try {
						JSONObject jsonRes = new JSONObject(arg0);
						int status = jsonRes.getInt("status");
						if (status == 0) {
							JSONObject data = jsonRes.getJSONObject("data");
							int result = data.getInt("result");
							String token = data.getString("token");
							switch (result) {
							case 0:
								Toast.makeText(MyApplication.getContext(),
										"登录成功", Toast.LENGTH_SHORT).show();
								Intent intent = new Intent(getActivity(),
										PersonnelInfoActivity.class);
								startActivity(intent);
								((HomeActivity)getActivity()).onBackPressed();
								final SharedPreferences sp = MyApplication
										.getContext().getSharedPreferences(
												"AccountConfig",
												Context.MODE_PRIVATE);
								sp.edit().putString("platform", "zhuoxin")
										.putString("token", token)
										.putBoolean("isLogin", true)
										.putString("name", name).commit();

								((HomeActivity) getActivity()).handler
										.post(new Runnable() {

											@Override
											public void run() {
												RightMenuFragment
														.getTitle(name);
												String imageicon = sp.getString("image", null);
												RightMenuFragment.getImage(imageicon);
												RightMenuFragment
														.setQQInvisible();
											}
										});
								break;

							default:
								Toast.makeText(MyApplication.getContext(),
										"该账号已被限制登录", Toast.LENGTH_SHORT).show();
								break;
							}
						} else {
							Toast.makeText(MyApplication.getContext(),
									"用户名或者密码错误，请检查账号密码", Toast.LENGTH_SHORT)
									.show();
						}
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}

				@Override
				public void onFinished() {
					Log.v("test", "断开连接");

				}

			});

			break;
		case R.id.tv_forgetregister:
			Intent intent = new Intent(getActivity(), ForgetActivity.class);
			getActivity().startActivity(intent);
			break;
		case R.id.tv_register:
			((HomeActivity) getActivity()).changeToRegisterFragment();
			break;

		}
	}

}
