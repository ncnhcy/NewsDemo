package com.zhuoxin.huacong.bean;

import java.util.List;

public class User {
	String uid;//�û���
	String portrait;//�û�ͼ��
	int integration;//�û�����Ʊ����
	int comnum;//��������

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
		String time;//��¼ʱ��
		String address;//�����г�����
		int device;//��½�豸 0��ʾ�ֻ��ˣ�1��ʾ���Զ�

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