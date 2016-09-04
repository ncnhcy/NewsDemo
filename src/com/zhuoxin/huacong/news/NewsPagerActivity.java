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

import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;

import com.zhouxin.huacong.news.R;
import com.zhuoxin.huacong.base.BaseActivity;
import com.zhuoxin.huacong.baseAdapter.MyApplication;
import com.zhuoxin.huacong.bean.NewsBean;

public class NewsPagerActivity extends BaseActivity implements OnClickListener {
	ProgressBar pb_news;
	TextView tv_web;
	WebView wv_news;
	ImageView iv_back;
	ImageView iv_share_web;
	NewsBean news = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_webview);
		news = (NewsBean) getIntent().getSerializableExtra("news");
		tv_web = (TextView) findViewById(R.id.tv_web);
		pb_news = (ProgressBar) findViewById(R.id.pb_news);
		wv_news = (WebView) findViewById(R.id.wv_news);
		iv_back = (ImageView) findViewById(R.id.iv_back);
		iv_share_web = (ImageView) findViewById(R.id.iv_share_web);
		iv_back.setOnClickListener(this);
		iv_share_web.setOnClickListener(this);

		// 获取设置信息并将缓存模式设置成为缓存+网络访问
		wv_news.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
		WebChromeClient client = new WebChromeClient() {
			@Override
			public void onProgressChanged(WebView view, int newProgress) {
				pb_news.setProgress(newProgress);
				if (newProgress >= 100) {
					pb_news.setVisibility(View.GONE);
				}
			}
		};
		wv_news.setWebChromeClient(client);
		wv_news.loadUrl(news.getUrl());
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.iv_back:
			finish();
			break;
		case R.id.iv_share_web:
			showShare();
			break;

		}
	}

	private void showShare() {
		ShareSDK.initSDK(MyApplication.getContext());
		OnekeyShare oks = new OnekeyShare();
		// 关闭sso授权
		oks.disableSSOWhenAuthorize();

		// 分享时Notification的图标和文字 2.5.9以后的版本不调用此方法
		// oks.setNotification(R.drawable.ic_launcher,
		// getString(R.string.app_name));
		// title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
		oks.setTitle(news.getTitle());
		// titleUrl是标题的网络链接，仅在人人网和QQ空间使用
		oks.setTitleUrl(news.getUrl());
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
		oks.setSiteUrl(news.getUrl());

		// 启动分享GUI
		oks.show(MyApplication.getContext());
	}

}
