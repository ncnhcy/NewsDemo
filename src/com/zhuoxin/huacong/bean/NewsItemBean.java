package com.zhuoxin.huacong.bean;

import java.io.Serializable;

public class NewsItemBean implements Serializable{
	String title;
	String date;
	String category;
	String author_name;
	String thumbnail_pic_s;
	String url;

	public NewsItemBean(String title, String date, String category,
			String author_name, String thumbnail_pic_s, String url) {
		super();
		this.title = title;
		this.date = date;
		this.category = category;
		this.author_name = author_name;
		this.thumbnail_pic_s = thumbnail_pic_s;
		this.url = url;
	}

	public String getTitle() {
		return title;
	}

	public String getDate() {
		return date;
	}

	public String getCategory() {
		return category;
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

}
