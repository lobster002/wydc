package com.sky.wydc.ui;

import com.sky.wydc.R;
import com.sky.wydc.view.ZoomImageView;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Window;

/*
 * 用来显示大图 
 * */
public class ShowActivity extends Activity {

	//自定义控件  继承SmartImageView  可以根据url 获取图片显示
	private ZoomImageView mImageViews;
	private String imgurl;  //图片url

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//设置不显示标题栏
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		//加载布局
		setContentView(R.layout.activity_show);
		//初始化
		initView();
	}

	private void initView() {
		//取得组件
		mImageViews = (ZoomImageView) findViewById(R.id.id_zoomimg);
		//获取url
		imgurl = getIntent().getStringExtra("url");
		//设置url 显示图片
		mImageViews.setImageUrl(imgurl);
	}

	//监听返回键 点击是返回并销毁当前对象
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			ShowActivity.this.finish();
			return true;
		} else {
			return super.onKeyDown(keyCode, event);
		}
	}

}
