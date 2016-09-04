package com.zhuoxin.huacong.news;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.zhouxin.huacong.news.R;
import com.zhuoxin.huacong.base.BaseActivity;
import com.zhuoxin.huacong.baseAdapter.MyApplication;
import com.zhuoxin.huacong.utils.DBUtils;

public class LoginActivity extends BaseActivity {

	EditText et_name;
	EditText et_password;
	Button btn_login;
	TextView tv_register;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_login);
		initView();
	}

	private void initView() {
		et_name = (EditText) findViewById(R.id.et_name);
		et_password = (EditText) findViewById(R.id.et_password);
		tv_register = (TextView) findViewById(R.id.tv_register);
		btn_login = (Button) findViewById(R.id.btn_login);

		btn_login.setOnClickListener(listener);
		tv_register.setOnClickListener(listener);
	}

	private OnClickListener listener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			String name = et_name.getText().toString();
			String password = et_password.getText().toString();

			switch (v.getId()) {
			case R.id.btn_login:
				
					if (DBUtils.loginUserInfo(name, password)) {
						Toast.makeText(MyApplication.getContext(), "¹§Ï²µÇÂ¼³É¹¦",
								Toast.LENGTH_SHORT).show();
						finish();

					} else {
						Toast.makeText(MyApplication.getContext(), "µÇÂ¼Ê§°Ü",
								Toast.LENGTH_SHORT).show();
					}
					break;

				
			case R.id.tv_register:
				startActivity(RegisterAcitvity.class);
				break;
			}
		}
	};

}
