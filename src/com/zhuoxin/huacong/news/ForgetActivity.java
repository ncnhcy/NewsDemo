package com.zhuoxin.huacong.news;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.x;
import org.xutils.common.Callback.CommonCallback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import com.zhouxin.huacong.news.R;
import com.zhuoxin.huacong.base.BaseActivity;
import com.zhuoxin.huacong.baseAdapter.MyApplication;

@ContentView(R.layout.activity_forget)
public class ForgetActivity extends BaseActivity {
	@ViewInject(R.id.et_forget)
	EditText et_forget;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		x.view().inject(this);

	}

	@Event(value = { R.id.btn_commit })
	private void onClick(View v) {
		String email = et_forget.getText().toString();
		if (email.matches("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*")) {
			RequestParams params = new RequestParams(
					"http://118.244.212.82:9092/newsClient/user_forgetpass?ver=1&email=758017188@qq.com");
			params.addQueryStringParameter("ver", "1");
			params.addQueryStringParameter("email", email);
			x.http().post(params, new CommonCallback<String>() {

				@Override
				public void onCancelled(CancelledException arg0) {
					// TODO Auto-generated method stub
					Toast.makeText(MyApplication.getContext(), "ȡ������",
							Toast.LENGTH_SHORT).show();
				}

				@Override
				public void onError(Throwable arg0, boolean arg1) {
					// TODO Auto-generated method stub
					Toast.makeText(MyApplication.getContext(), "���ʷ���������",
							Toast.LENGTH_SHORT).show();
				}

				@Override
				public void onSuccess(String arg0) {
					// TODO Auto-generated method stub
					try {
						JSONObject json = new JSONObject("arg0");
						int status = json.getInt("status");
						if (status == 0) {
							// ���ͳɹ�
							JSONObject data = json.getJSONObject("data");
							int result = data.getInt("result");
							if (result == 0) {
								// ��������ɹ�
								Toast.makeText(MyApplication.getContext(),
										"����ɹ������������", Toast.LENGTH_SHORT)
										.show();
							} else {
								// ����ʧ�ܣ�������δע�ᣬ�򱻷�ţ�
								Toast.makeText(MyApplication.getContext(),
										"������δע��򱻷��", Toast.LENGTH_SHORT)
										.show();
							}
						} else if (status == -1) {
							// ����ʧ�ܣ�������δע�ᣩ
							Toast.makeText(MyApplication.getContext(),
									"������δע��", Toast.LENGTH_SHORT).show();
						} else {
							// ����ʧ�ܣ����䲻���ڻ򱻷�ţ�
							Toast.makeText(MyApplication.getContext(),
									"���䲻���ڻ򱻷��", Toast.LENGTH_SHORT).show();
						}
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

				@Override
				public void onFinished() {
					// TODO Auto-generated method stub

				}

			});
		} else {
			Toast.makeText(MyApplication.getContext(), "�����ʽ����",
					Toast.LENGTH_SHORT).show();
		}
	}
}
