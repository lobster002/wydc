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

	// ҳ���б�
	private List<View> views;
	private Activity activity;

	public ViewPagerAdapter(List<View> views, Activity activity) {
		this.views = views;
		this.activity = activity;
	}

	// ����positionλ�õĽ���
	@Override
	public void destroyItem(View arg0, int position, Object arg2) {
		((ViewPager) arg0).removeView(views.get(position));
	}

	@Override
	public void finishUpdate(View arg0) {
	}

	// ��õ�ǰ������
	@Override
	public int getCount() {
		if (views != null) {
			return views.size();
		}
		return 0;
	}

	// ��ʼ��positionλ�õĽ���
	@Override
	public Object instantiateItem(View view, int position) {
		((ViewPager) view).addView(views.get(position), 0);
		// �� Ϊ���һ������� ʱ��,ȡ��button����������ü���
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

	// ����sharepreference��isFristInΪfalse,˵��Ϊ�ڶ�������
	private void setGuided() {
		SharePreferenceUtil util = new SharePreferenceUtil(activity,
				Config.SHAREDINFO);
		util.setisFristIn(false);
	}

	private void goLogin() {
		// ��ת����¼����
		Intent intent = new Intent(activity, LoginActivity.class);
		activity.startActivity(intent);
		activity.finish();
	}

	// �ж��Ƿ��ɶ������ɽ���
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
