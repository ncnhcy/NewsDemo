package com.zhuoxin.huacong.utils;

import java.io.ByteArrayInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Util {

	public static String getMessageDigstData(String data) {
		byte[] mddata = null;
		try {
			// ��ȡ����ָ�ƣ�ָ���㷨ΪMD5�㷨
			MessageDigest md = MessageDigest.getInstance("MD5");
			// ����һ���ֽ����飬������¼�������Ķ�ȡ����
			byte bytes[] = new byte[8192];
			// ����int���͵�count ������¼��������ȡ������
			int byteCount = 0;
			// ��ȡ��������byteArray������
			String dataInfo = data;

			ByteArrayInputStream bais = new ByteArrayInputStream(
					dataInfo.getBytes());
			// ��ȡ������ÿ��ȡһ�ζ�����ָ��degister���и��²���
			while ((byteCount = bais.read()) > 0) {
				md.update(bytes, 0, byteCount);
			}
			// �������ݶ�ȡ��Ϻ󣬻�ȡ����ָ���еļ��ܺ������
			mddata = md.digest();

			// Log.v("test", "" + mddata.length);

		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return byteArrayToHexString(mddata);

	}

	public static String byteArrayToHexString(byte MD5[]) {

		char hexChar[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
				'a', 'b', 'c', 'd', 'e', 'f' };
		char data[] = new char[32];
		int index = 0;
		// ���������±�������data�����д���ת�����16�����ַ�
		for (byte b : MD5) {
			// ��ȡ��4λ�Ķ�Ӧ16���Ƶ��Ҳ��ַ�
			data[index++] = hexChar[b >>> 4 & 0x0f];
			// ��ȡ��4λ��Ӧ��
			data[index++] = hexChar[b & 0x0f];
		}
		String hexString = new String(data);
		return hexString;

	}

}
