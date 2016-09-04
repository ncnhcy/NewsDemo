package com.zhuoxin.huacong.utils;

import java.util.List;
import com.google.gson.Gson;
import com.zhuoxin.huacong.bean.NewsBean;
import com.zhuoxin.huacong.bean.NewsJsonBean;
import com.zhuoxin.huacong.bean.ResourceUserBean;
import com.zhuoxin.huacong.bean.NewsItemBean;
import com.zhuoxin.huacong.bean.ScienceJsonBean;
import com.zhuoxin.huacong.bean.User;

public class JsonUtil {

	public static List<NewsBean> parseJson(String json) {
		Gson gson = new Gson();
		NewsJsonBean njb = gson.fromJson(json, NewsJsonBean.class);
		List<NewsBean> newsList = njb.result.data;
		return newsList;
	}
	
	public static List<NewsItemBean> parseScienceJson(String json) {
		Gson gson = new Gson();
		ScienceJsonBean njb = gson.fromJson(json, ScienceJsonBean.class);
		List<NewsItemBean> ScienceList  = njb.result.data;
		return ScienceList;
	}
	
	
	public static User preaseResourceInfo(String json) {
		Gson gson = new Gson();
		ResourceUserBean njb = gson.fromJson(json, ResourceUserBean.class);
		return njb.getData();
	}

}
