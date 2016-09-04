package com.zhuoxin.huacong.news;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.zhouxin.huacong.news.R;
import com.zhuoxin.huacong.base.BaseActivity;
import com.zhuoxin.huacong.baseAdapter.MyApplication;
import com.zhuoxin.huacong.utils.DBUtils;

public class RegisterAcitvity extends BaseActivity {

	EditText et_name;
	EditText et_password;
	EditText et_repeatpassword;
	Button btn_register;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_register);
		initView();
	}

	private void initView() {
		et_name = (EditText) findViewById(R.id.et_name);
		et_password = (EditText) findViewById(R.id.et_password);
		et_repeatpassword = (EditText) findViewById(R.id.et_repeatpassword);
		btn_register = (Button) findViewById(R.id.btn_register);

		btn_register.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String name = et_name.getText().toString();
				String password = et_password.getText().toString();
				String repeatpassword = et_repeatpassword.getText().toString();

				if (password
						.matches("^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,20}$")) {

					if (password.equals(repeatpassword) && name != null
							&& (name.length() >= 6)) {
						Toast.makeText(MyApplication.getContext(), "恭喜注册成功",
								Toast.LENGTH_SHORT).show();
						DBUtils.registerUserInfo(name, password);
						finish();
					} else {
						if (DBUtils.quaryUserInfo(name)) {
							Toast.makeText(MyApplication.getContext(),
									"账号已存在,请重新注册", Toast.LENGTH_SHORT).show();
						} else if (name.length() < 6) {
							Toast.makeText(MyApplication.getContext(),
									"账号位数较少，请输入大于6位的账号", Toast.LENGTH_SHORT)
									.show();
						} else if (!password.equals(repeatpassword)) {
							Toast.makeText(MyApplication.getContext(),
									"两次输入的密码不一致，请重新输入", Toast.LENGTH_SHORT)
									.show();
						} else {
							Toast.makeText(MyApplication.getContext(),
									"注册失败", Toast.LENGTH_SHORT)
									.show();
						}
					}
				}else{
					Toast.makeText(MyApplication.getContext(),
							"注册失败,密码由6-20字母和数字组成", Toast.LENGTH_SHORT)
							.show();
				}

			}
		});
	}

}
