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
	 * 增加数据
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
	 * 删除所有数据
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
	 * 查询所有新闻数据
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
	 * 查询数据控中存储的账号信息数据
	 * 
	 * @return
	 */
	public static boolean quaryUserInfo(String name) {

		// 四个参数分别是 环境 ，数据库名称， cursor工厂 ，版本
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
	 * 注册
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
	 * 登录
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
