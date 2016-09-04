package com.zhuoxin.huacong.fragment;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.x;
import org.xutils.common.Callback.CommonCallback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import com.google.gson.JsonObject;
import com.zhouxin.huacong.news.R;
import com.zhuoxin.huacong.baseAdapter.MyApplication;
import com.zhuoxin.huacong.news.HomeActivity;
import com.zhuoxin.huacong.utils.MD5Util;
import android.annotation.SuppressLint;
import android.app.Activity;
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

@ContentView(R.layout.fragment_register)
public class RegisterFragment extends Fragment {
	@ViewInject(R.id.et_name)
	EditText et_name;
	@ViewInject(R.id.et_password)
	EditText et_password;
	@ViewInject(R.id.et_repeatpassword)
	EditText et_repeatpassword;
	@ViewInject(R.id.et_email)
	EditText et_email;
	SharedPreferences sp = MyApplication.getContext().getSharedPreferences(
			"account_config", Activity.MODE_PRIVATE);

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return x.view().inject(this, inflater, container);
	}

	@Event(value = { R.id.btn_register })
	private void getListener(View v) {
		switch (v.getId()) {

		case R.id.btn_register:
			final String name = et_name.getText().toString();
			String email = et_email.getText().toString();
			String password = et_password.getText().toString();
			String repeatpassword = et_repeatpassword.getText().toString();
			final String mdPassword = MD5Util.getMessageDigstData(password);
			// 邮箱的正则表达式
			if (email
					.matches("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*")) {
				if (password.equals(repeatpassword)) {
					if (password
							.matches("^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,20}$")) {
						// 注册成功条件成立

						// 设置服务器的请求参数
						RequestParams params = new RequestParams(
								"http://118.244.212.82:9092/newsClient/user_register");
						params.addQueryStringParameter("ver", "1");
						params.addQueryStringParameter("uid", name);
						params.addQueryStringParameter("email", email);
						params.addQueryStringParameter("pwd", mdPassword);
						// 通过post提交、
						x.http().post(params, new CommonCallback<String>() {

							@Override
							public void onCancelled(CancelledException arg0) {
								// 与服务器取消连接

							}

							@Override
							public void onError(Throwable arg0, boolean arg1) {
								// 服务器连接错误
							}

							@SuppressLint("CommitPrefEdits")
							@Override
							public void onSuccess(String arg0) {
								// 与服务器来连接成功
								try {
									JSONObject jsonRes = new JSONObject(arg0);
									JSONObject data = jsonRes
											.getJSONObject("data");
									int result = data.getInt("result");
									switch (result) {
									case 0:
										Toast.makeText(
												MyApplication.getContext(),
												"注册成功", Toast.LENGTH_SHORT)
												.show();
										((HomeActivity) getActivity())
												.onBackPressed();
										break;
									case -1:
										Toast.makeText(
												MyApplication.getContext(),
												"服务器不允许注册", Toast.LENGTH_SHORT)
												.show();
										break;
									case -2:
										Toast.makeText(
												MyApplication.getContext(),
												"用户名重复", Toast.LENGTH_SHORT)
												.show();
										break;
									case -3:
										Toast.makeText(
												MyApplication.getContext(),
												"邮箱重复", Toast.LENGTH_SHORT)
												.show();
										break;

									}
								} catch (JSONException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}

							}

							@Override
							public void onFinished() {
								// 结束，与服务器断开连接

							}

						});

					} else {
						Toast.makeText(MyApplication.getContext(),
								"注册失败,密码由6-20字母和数字组成", Toast.LENGTH_SHORT)
								.show();
					}
				} else {
					Toast.makeText(MyApplication.getContext(),
							"两次输入的密码不一致请重新输入", Toast.LENGTH_SHORT).show();
				}
			} else {
				Toast.makeText(MyApplication.getContext(), "邮箱格式错误",
						Toast.LENGTH_SHORT).show();
			}
			break;

		}
	}
}
