package com.zhuoxin.huacong.news;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import com.zhouxin.huacong.news.R;
import com.zhuoxin.huacong.base.BaseActivity;

public class LogoActivity extends BaseActivity {
	ImageView iv_logo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_logo);
		
		Animation anima = AnimationUtils.loadAnimation(this,
				R.anim.alpha_logo_layout);

		iv_logo = (ImageView) findViewById(R.id.iv_logo);
		anima.setAnimationListener(new AnimationListener() {

			@Override
			public void onAnimationStart(Animation animation) {

			}

			@Override
			public void onAnimationRepeat(Animation animation) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onAnimationEnd(Animation animation) {
				// TODO Auto-generated method stub
				startActivity(HomeActivity.class, R.anim.translate_layout_out,
						R.anim.translate_layout_out);
				finish();

			}
		});
		iv_logo.startAnimation(anima);
	}
	

}
