package com.zhuoxin.huacong.bean;

import java.io.Serializable;

public class NewsBean implements Serializable {// 实现一个序列化接口，就会有一个标记
	String title;
	String date;
	String author_name;
	String thumbnail_pic_s;
	String url;
	String uniquekey;
	String realtype;


	public NewsBean(String title, String date, String author_name,
			String thumbnail_pic_s, String url, String uniquekey,
			String realtype) {
		super();
		this.title = title;
		this.date = date;
		this.author_name = author_name;
		this.thumbnail_pic_s = thumbnail_pic_s;
		this.url = url;
		this.uniquekey = uniquekey;
		this.realtype = realtype;

	}


	public String getTitle() {
		return title;
	}

	public String getDate() {
		return date;
	}

	public String getAuthor_name() {
		return author_name;
	}

	public String getThumbnail_pic_s() {
		return thumbnail_pic_s;
	}

	public String getUrl() {
		return url;
	}

	public String getUniquekey() {
		return uniquekey;
	}

	public String getRealtype() {
		return realtype;
	}

}
