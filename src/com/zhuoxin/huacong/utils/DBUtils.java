package com.zhuoxin.huacong.utils;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.zhuoxin.huacong.baseAdapter.MyApplication;
import com.zhuoxin.huacong.bean.NewsBean;

public class DBUtils {
	/**
	 * ��������
	 * 
	 * @param news
	 */
	public static void addNews(NewsBean news) {
		delectNews(news);
		MyOpenHelper myHelper = new MyOpenHelper(MyApplication.getContext(),
				"news.db", null, 1);
		SQLiteDatabase sqlDatabase = myHelper.getWritableDatabase();
		String title = news.getTitle();
		String date = news.getDate();
		String author_name = news.getAuthor_name();
		String thumbnail_pic_s = news.getThumbnail_pic_s();
		String url = news.getUrl();
		String uniquekey = news.getUniquekey();
		String realtype = news.getRealtype();
		// ??
		ContentValues contentValues = new ContentValues();
		contentValues.put("title", title);
		contentValues.put("date", date);
		contentValues.put("author_name", author_name);
		contentValues.put("thumbnail_pic_s", thumbnail_pic_s);
		contentValues.put("url", url);
		contentValues.put("uniquekey", uniquekey);
		contentValues.put("realtype", realtype);
		sqlDatabase.insert("top", null, contentValues);
		sqlDatabase.close();
		myHelper.close();
	}

	/**
	 * ɾ����������
	 * 
	 * @param news
	 */
	public static void delectNews(NewsBean news) {
		MyOpenHelper myHelper = new MyOpenHelper(MyApplication.getContext(),
				"news.db", null, 1);
		SQLiteDatabase sqlDatabase = myHelper.getWritableDatabase();
		String url = news.getUrl();
		sqlDatabase.delete("top", "url=?", new String[] { url });
		sqlDatabase.close();
		myHelper.close();
	}

	/**
	 * ��ѯ������������
	 * 
	 * @return
	 */
	public static List<NewsBean> quaryNews() {
		List<NewsBean> newsList = new ArrayList<NewsBean>();
		MyOpenHelper myHelper = new MyOpenHelper(MyApplication.getContext(),
				"news.db", null, 1);
		SQLiteDatabase sqlDatabase = myHelper.getWritableDatabase();
		Cursor cursor = sqlDatabase.query("top", null, null, null, null, null,
				null);
		if (cursor.moveToFirst()) {
			do {
				String title = cursor.getString(0);
				String date = cursor.getString(1);
				String author_name = cursor.getString(2);
				String thumbnail_pic_s = cursor.getString(3);
				String url = cursor.getString(4);
				String uniquekey = cursor.getString(5);
				String realtype = cursor.getString(6);
				NewsBean news = new NewsBean(title, date, author_name,
						thumbnail_pic_s, url, uniquekey, realtype);
				newsList.add(news);
			} while (cursor.moveToNext());

		}
		return newsList;

	}

	/**
	 * ��ѯ���ݿ��д洢���˺���Ϣ����
	 * 
	 * @return
	 */
	public static boolean quaryUserInfo(String name) {

		// �ĸ������ֱ��� ���� �����ݿ����ƣ� cursor���� ���汾
		MyOpenHelper helper = new MyOpenHelper(MyApplication.getContext(),
				"news.db", null, 1);
		SQLiteDatabase sqlDB = helper.getWritableDatabase();
		Cursor cursor = sqlDB.query("userinfo", null, "name = ?",
				new String[] { name }, null, null, null);
		if (cursor.moveToFirst()) {
			cursor.close();
			sqlDB.close();
			helper.close();
			return true;
		}
		cursor.close();
		sqlDB.close();
		helper.close();
		return false;
	}

	/**
	 * ע��
	 * 
	 * @return
	 */
	public static boolean registerUserInfo(String name, String password) {
		String MD5Password = MD5Util.getMessageDigstData(password);
		if (quaryUserInfo(name)) {
			return false;
		} else {
			MyOpenHelper helper = new MyOpenHelper(MyApplication.getContext(),
					"news.db", null, 1);
			SQLiteDatabase sqlDB = helper.getWritableDatabase();
			ContentValues values = new ContentValues();
			values.put("name", name);
			values.put("password", MD5Password);
			sqlDB.insert("userinfo", null, values);
			return true;
		}

	}

	/**
	 * ��¼
	 * 
	 * @return
	 */
	public static boolean loginUserInfo(String name, String password) {
		String MD5Password = MD5Util.getMessageDigstData(password);
		MyOpenHelper helper = new MyOpenHelper(MyApplication.getContext(),
				"news.db", null, 1);
		SQLiteDatabase sqlDB = helper.getWritableDatabase();
		Cursor cursor = sqlDB.query("userinfo", null,
				"name = ? and password = ?",
				new String[] { name, MD5Password }, null, null, null);
		if (cursor.moveToFirst()) {
			cursor.close();
			sqlDB.close();
			helper.close();
			return true;
		}
		cursor.close();
		sqlDB.close();
		helper.close();
		return false;

	}
}
