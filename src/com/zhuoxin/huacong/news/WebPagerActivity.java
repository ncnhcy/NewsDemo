package com.zhuoxin.huacong.news;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.zhouxin.huacong.news.R;
import com.zhuoxin.huacong.base.BaseActivity;
import com.zhuoxin.huacong.bean.NewsItemBean;

public class WebPagerActivity extends BaseActivity {

	ProgressBar pb_science;
	TextView tv_web;
	WebView wv_science;
	ImageView iv_back;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_scienceweb);

		NewsItemBean scienceBean = (NewsItemBean) getIntent()
				.getSerializableExtra("science");
		String title = getIntent().getStringExtra("title");
		pb_science = (ProgressBar) findViewById(R.id.pb_science);
		wv_science = (WebView) findViewById(R.id.wv_science);
		iv_back = (ImageView) findViewById(R.id.iv_back);
		tv_web = (TextView) findViewById(R.id.tv_web);
		tv_web.setText(title);
		iv_back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});

		// 获取设置信息并将缓存模式设置成为缓存+网络访问
		wv_science.getSettings().setCacheMode(
				WebSettings.LOAD_CACHE_ELSE_NETWORK);
		WebChromeClient client = new WebChromeClient() {
			@Override
			public void onProgressChanged(WebView view, int newProgress) {
				pb_science.setProgress(newProgress);
				if (newProgress >= 100) {
					pb_science.setVisibility(View.GONE);
				}

			}
		};
		wv_science.setWebChromeClient(client);
		wv_science.loadUrl(scienceBean.getUrl());
	}
}
