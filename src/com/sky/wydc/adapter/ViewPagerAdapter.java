package com.sky.wydc.adapter;

import java.util.List;
import com.sky.wydc.R;
import com.sky.wydc.config.Config;
import com.sky.wydc.ui.LoginActivity;
import com.sky.wydc.utils.SharePreferenceUtil;
import android.app.Activity;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class ViewPagerAdapter extends PagerAdapter {

	// 页面列表
	private List<View> views;
	private Activity activity;

	public ViewPagerAdapter(List<View> views, Activity activity) {
		this.views = views;
		this.activity = activity;
	}

	// 销毁position位置的界面
	@Override
	public void destroyItem(View arg0, int position, Object arg2) {
		((ViewPager) arg0).removeView(views.get(position));
	}

	@Override
	public void finishUpdate(View arg0) {
	}

	// 获得当前界面数
	@Override
	public int getCount() {
		if (views != null) {
			return views.size();
		}
		return 0;
	}

	// 初始化position位置的界面
	@Override
	public Object instantiateItem(View view, int position) {
		((ViewPager) view).addView(views.get(position), 0);
		// 当 为最后一个界面的 时候,取得button组件，并设置监听
		if (position == views.size() - 1) {
			Button btn = (Button) view.findViewById(R.id.start_app);
			btn.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					setGuided();
					goLogin();
				}
			});
		}
		return views.get(position);
	}

	// 设置sharepreference中isFristIn为false,说明为第二次启动
	private void setGuided() {
		SharePreferenceUtil util = new SharePreferenceUtil(activity,
				Config.SHAREDINFO);
		util.setisFristIn(false);
	}

	private void goLogin() {
		// 跳转到登录界面
		Intent intent = new Intent(activity, LoginActivity.class);
		activity.startActivity(intent);
		activity.finish();
	}

	// 判断是否由对象生成界面
	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return (arg0 == arg1);
	}

	@Override
	public void restoreState(Parcelable arg0, ClassLoader arg1) {
	}

	@Override
	public Parcelable saveState() {
		return null;
	}

	@Override
	public void startUpdate(View arg0) {
	}

}
