package com.zhuoxin.huacong.adapter;

import org.xutils.x;
import org.xutils.view.annotation.ViewInject;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.zhouxin.huacong.news.R;
import com.zhuoxin.huacong.baseAdapter.MyBaseAdapter;
import com.zhuoxin.huacong.bean.User;

public class LoginLogAdapter extends MyBaseAdapter<User.LoginMessage> {

	public LoginLogAdapter(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder = new ViewHolder();
		if (convertView == null) {
			convertView = layoutInflater.inflate(
					R.layout.layout_loginlog_listitem, null);
			x.view().inject(viewHolder, convertView);
			/*viewHolder.tv_address = (TextView) convertView
					.findViewById(R.id.tv_address);
			viewHolder.tv_time = (TextView) convertView
					.findViewById(R.id.tv_time);
			viewHolder.tv_device = (TextView) convertView
					.findViewById(R.id.tv_device);
					*/
			convertView.setTag(viewHolder);

		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		viewHolder.tv_address.setText((getItem(position).getAddress())+"");
		viewHolder.tv_time.setText((getItem(position).getTime()+""));
		viewHolder.tv_device.setText((getItem(position).getDevice())+"");

		return convertView;
	}

	private static class ViewHolder {
		@ViewInject(R.id.tv_address)
		TextView tv_address;
		@ViewInject(R.id.tv_time)
		TextView tv_time;
		@ViewInject(R.id.tv_device)
		TextView tv_device;

	}

}
