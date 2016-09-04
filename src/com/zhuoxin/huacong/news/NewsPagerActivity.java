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

		// ��ȡ������Ϣ��������ģʽ���ó�Ϊ����+�������
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
		// �ر�sso��Ȩ
		oks.disableSSOWhenAuthorize();

		// ����ʱNotification��ͼ������� 2.5.9�Ժ�İ汾�����ô˷���
		// oks.setNotification(R.drawable.ic_launcher,
		// getString(R.string.app_name));
		// title���⣬ӡ��ʼǡ����䡢��Ϣ��΢�š���������QQ�ռ�ʹ��
		oks.setTitle(news.getTitle());
		// titleUrl�Ǳ�����������ӣ�������������QQ�ռ�ʹ��
		oks.setTitleUrl(news.getUrl());
		// text�Ƿ����ı�������ƽ̨����Ҫ����ֶ�
		oks.setText("���Ƿ����ı�");
		// imagePath��ͼƬ�ı���·����Linked-In�����ƽ̨��֧�ִ˲���
		// oks.setImagePath("/sdcard/test.jpg");//ȷ��SDcard������ڴ���ͼƬ
		// url����΢�ţ��������Ѻ�����Ȧ����ʹ��
		oks.setUrl("http://sharesdk.cn");
		// comment���Ҷ�������������ۣ�������������QQ�ռ�ʹ��
		oks.setComment("���ǲ��������ı�");
		// site�Ƿ�������ݵ���վ���ƣ�����QQ�ռ�ʹ��
		oks.setSite(getString(R.string.app_name));
		// siteUrl�Ƿ�������ݵ���վ��ַ������QQ�ռ�ʹ��
		oks.setSiteUrl(news.getUrl());

		// ��������GUI
		oks.show(MyApplication.getContext());
	}

}
