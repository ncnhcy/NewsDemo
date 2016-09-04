package com.zhuoxin.huacong.baseAdapter;

import org.xutils.x;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import android.app.Application;
import android.telephony.TelephonyManager;

public class MyApplication extends Application {
	private static MyApplication context;
	private static RequestQueue rq;
	
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		MyApplication.context = this;
		x.Ext.init(this);
		rq = Volley.newRequestQueue(getContext());
	}

	public static MyApplication getContext() {
		return context;
	}

	public static RequestQueue getRequestQueue() {
		return rq;
	}
	
	public String getimei(){
		String imei = ((TelephonyManager) getSystemService(TELEPHONY_SERVICE))
			.getDeviceId();
		return imei;
	}
	

}
