package com.zhuoxin.huacong.news;

import com.zhouxin.huacong.news.R;
import com.zhuoxin.huacong.base.BaseActivity;
import com.zhuoxin.huacong.baseAdapter.BaseViewPagerAdapter;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

public class LeaderActivity extends BaseActivity implements OnClickListener{
	View icon[] = new View[4];
	ViewPager vp_lead;
	TextView tv_skip;
	boolean isFirstRun;
	SharedPreferences sp ;
	BaseViewPagerAdapter viewAdapter = new BaseViewPagerAdapter(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sp = getSharedPreferences("lead_config",MODE_PRIVATE);
        isFirstRun = sp.getBoolean("isFirstRun", true);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_viewpager);
        initMainView();
        initView();
        if(isFirstRun){
        	sp.edit().putBoolean("isFirstRun", false).commit();
        }else{
        	startActivity(LogoActivity.class);
        	finish();
        }
    }
    /**
     * 初始化控件
     */
    protected void initMainView(){
    	vp_lead = (ViewPager) findViewById(R.id.vp_lead);
    	tv_skip = (TextView) findViewById(R.id.tv_skip);
    	tv_skip.setOnClickListener(this);
    	icon[0] = findViewById(R.id.iv_point1);
    	icon[1] = findViewById(R.id.iv_point2);
    	icon[2] = findViewById(R.id.iv_point3);
    	icon[3] = findViewById(R.id.iv_point4);
    	
    }
    
    protected void initView(){
    	//分别建出每一个页面的view  然后添加资源
    	viewAdapter.addViewToAdapter(getLayoutInflater().inflate(R.layout.leadpager1, null));
    	viewAdapter.addViewToAdapter(getLayoutInflater().inflate(R.layout.leadpager2, null));
    	viewAdapter.addViewToAdapter(getLayoutInflater().inflate(R.layout.leadpager3, null));
    	viewAdapter.addViewToAdapter(getLayoutInflater().inflate(R.layout.leadpager4, null));
    	/*
    	 * 只建立一个view  然后分别添加资源的方式
    	ImageView iv_picture = (ImageView) View.inflate(this, R.layout.layout_viewpager_view, null);
    	iv_picture.setImageResource(R.drawable.welcome1);
    	viewAdapter.addViewToAdapter(iv_picture);
    	*/
    	vp_lead.setAdapter(viewAdapter);
    	vp_lead.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int arg0) {
				// TODO Auto-generated method stub
				tv_skip.setVisibility(View.INVISIBLE);
				if(arg0==3){
					tv_skip.setVisibility(View.VISIBLE);
				}
				for(int i =0;i<icon.length;i++){
					icon[i].setAlpha(0.4f);
				}
				icon[arg0].setAlpha(1.0f);
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub
				
			}
		});
    }


	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv_skip:
			startActivity(LogoActivity.class, R.anim.translate_layout_in,R.anim.translate_layout_in);
			finish();
			break;

		default:
			break;
		}
	}
}
