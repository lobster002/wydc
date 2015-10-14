package com.sky.wydc.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.Toast;

import com.sky.wydc.R;
import com.sky.wydc.config.CustomApplcation;
import com.sky.wydc.view.HeadLayout;
import com.sky.wydc.view.HeadLayout.*;

// 基类
public class BaseActivity extends FragmentActivity {

	protected int mScreenWidth;// 屏幕像(px)宽度
	protected int mScreenHeight;// 屏幕像素(px)高度
	protected CustomApplcation mApplication;
	protected HeadLayout mLayout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 获取当前应用程序中的Applcation对象
		mApplication = CustomApplcation.getInstance();
		// 获取设备屏幕的像素
		DisplayMetrics metric = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metric);
		mScreenWidth = metric.widthPixels;
		mScreenHeight = metric.heightPixels;

	}

	Toast mToast;//

	// 显示Toast
	public void ShowToast(final String text) {
		// 先判传递的text是否为""
		if (!TextUtils.isEmpty(text)) {

			runOnUiThread(new Runnable() {
				@Override
				public void run() {
					if (mToast == null) {
						mToast = Toast.makeText(getApplicationContext(), text,
								Toast.LENGTH_LONG);
					} else {
						mToast.setText(text);
					}
					mToast.show();
				}
			});

		}
	}

	// 显示Toast
	public void ShowToast(final int resId) {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				if (mToast == null) {
					mToast = Toast.makeText(
							BaseActivity.this.getApplicationContext(), resId,
							Toast.LENGTH_LONG);
				} else {
					mToast.setText(resId);
				}
				mToast.show();
			}
		});
	}

	/**
	 * 打Log ShowLog
	 * 
	 * @return void
	 * @throws
	 */
	public void ShowLog(String msg) {
		Log.i("lisao", msg);
	}

	// 通过class启动Activity
	public void startAnimActivity(Class<?> cla) {
		this.startActivity(new Intent(this, cla));
	}

	// 通过Intent启动Activity
	public void startAnimActivity(Intent intent) {
		this.startActivity(intent);
	}

	// 自定义头部局初始化，只有一个标题
	public void initOnlyTitle(String title) {
		mLayout = (HeadLayout) findViewById(R.id.actionbar);
		mLayout.setOnlyTitle(title);
	}

	// 自定义头部局初始化，有一个标题，和一个返回按钮
	public void initLeftBtnAndTitle(String title) {
		mLayout = (HeadLayout) findViewById(R.id.actionbar);
		mLayout.setLeftAndTitle(title);
		mLayout.setLefeBtnClick(new onLeftImageButtonClickListener() {

			@Override
			public void click() {
				finish();
			}
		});
	}
}
