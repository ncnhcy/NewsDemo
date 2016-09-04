package com.zhuoxin.huacong.bean;

import java.util.List;

public class User {
	String uid;//用户名
	String portrait;//用户图标
	int integration;//用户积分票总数
	int comnum;//评论总数

	List<LoginMessage> loginlog;

	public String getUid() {
		return uid;
	}

	public String getPortrait() {
		return portrait;
	}

	public int getIntegration() {
		return integration;
	}

	public int getComnum() {
		return comnum;
	}

	public List<LoginMessage> getLoginlog() {
		return loginlog;
	}

	public class LoginMessage {
		String time;//登录时间
		String address;//北京市朝阳区
		int device;//登陆设备 0表示手机端，1表示电脑端

		public String getTime() {
			return time;
		}

		public String getAddress() {
			return address;
		}

		public int getDevice() {
			return device;
		}
	}

}