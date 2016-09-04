package com.zhuoxin.huacong.utils;

import java.util.ArrayList;
import java.util.List;

import com.zhuoxin.huacong.baseAdapter.MyApplication;

import android.database.Cursor;
import android.provider.MediaStore;
import android.provider.MediaStore.MediaColumns;

public class ImageLoader {
	
	public ImageLoader() {
		
	}
	
	static List<String> paths ;
	
	public static List<String> ImageQuary(){
		paths = new ArrayList<String>();
		Cursor cursor = MyApplication.getContext().getContentResolver().query(  
	               MediaStore.Images.Media.EXTERNAL_CONTENT_URI, null, null, null, null);  
	       //�������  
	       while (cursor.moveToNext()) {  
	           String path = cursor.getString(cursor.getColumnIndex(MediaColumns.DATA));  
	           //��ͼƬ·����ӵ�����  
	           paths.add(path); 
	       }  
	       cursor.close();  
           return paths;
	}
	
}
