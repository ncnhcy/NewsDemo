package com.zhuoxin.huacong.text;

import java.io.ByteArrayInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import android.test.AndroidTestCase;
import android.util.Log;

public class MD5Test extends AndroidTestCase {

	public void test() {
		try {
			// 获取数据指纹，指定算法为MD5算法
			MessageDigest md = MessageDigest.getInstance("MD5");
			// 定义一个字节数组，用来记录输入流的读取数量
			byte bytes[] = new byte[8192];
			// 定义int类型的count 用来记录输入流读取的数量
			int byteCount = 0;
			// 获取输入流，byteArray输入流
			String data = "123456";
			ByteArrayInputStream bais = new ByteArrayInputStream(
					data.getBytes());
			// 读取操作，每读取一次对数据指纹degister进行更新操作
			while ((byteCount = bais.read()) > 0) {
				md.update(bytes, 0, byteCount);
			}
			// 所有数据读取完毕后，获取数据指纹中的加密后的数据
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
		// 定义数组下标用来在data数组中储存转化后的16进制字符
		for (byte b : MD5) {
			// 获取高4位的对应16进制的右侧字符
			data[index++] = hexChar[b >>> 4  & 0x0f];
			// 获取低4位对应的
			data[index++] = hexChar[b & 0x0f];

		}
		String hexString = new String(data);
		return hexString;	

	}

}
