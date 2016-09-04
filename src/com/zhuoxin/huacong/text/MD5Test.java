package com.zhuoxin.huacong.text;

import java.io.ByteArrayInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import android.test.AndroidTestCase;
import android.util.Log;

public class MD5Test extends AndroidTestCase {

	public void test() {
		try {
			// ��ȡ����ָ�ƣ�ָ���㷨ΪMD5�㷨
			MessageDigest md = MessageDigest.getInstance("MD5");
			// ����һ���ֽ����飬������¼�������Ķ�ȡ����
			byte bytes[] = new byte[8192];
			// ����int���͵�count ������¼��������ȡ������
			int byteCount = 0;
			// ��ȡ��������byteArray������
			String data = "123456";
			ByteArrayInputStream bais = new ByteArrayInputStream(
					data.getBytes());
			// ��ȡ������ÿ��ȡһ�ζ�����ָ��degister���и��²���
			while ((byteCount = bais.read()) > 0) {
				md.update(bytes, 0, byteCount);
			}
			// �������ݶ�ȡ��Ϻ󣬻�ȡ����ָ���еļ��ܺ������
			byte[] mddata = md.digest();
			Log.v("test", "" + mddata.length);		
			Log.v("test", "" + byteArrayToHexString(mddata));

		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public static String byteArrayToHexString(byte MD5[]) {
		char hexChar[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
				'a', 'b', 'c', 'd', 'e', 'f' };
		char data[] = new char[32];
		int index = 0;
		// ���������±�������data�����д���ת�����16�����ַ�
		for (byte b : MD5) {
			// ��ȡ��4λ�Ķ�Ӧ16���Ƶ��Ҳ��ַ�
			data[index++] = hexChar[b >>> 4  & 0x0f];
			// ��ȡ��4λ��Ӧ��
			data[index++] = hexChar[b & 0x0f];

		}
		String hexString = new String(data);
		return hexString;	

	}

}
