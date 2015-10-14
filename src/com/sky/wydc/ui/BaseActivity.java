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

// ����
public class BaseActivity extends FragmentActivity {

	protected int mScreenWidth;// ��Ļ��(px)���
	protected int mScreenHeight;// ��Ļ����(px)�߶�
	protected CustomApplcation mApplication;
	protected HeadLayout mLayout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// ��ȡ��ǰӦ�ó����е�Applcation����
		mApplication = CustomApplcation.getInstance();
		// ��ȡ�豸��Ļ������
		DisplayMetrics metric = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metric);
		mScreenWidth = metric.widthPixels;
		mScreenHeight = metric.heightPixels;

	}

	Toast mToast;//

	// ��ʾToast
	public void ShowToast(final String text) {
		// ���д��ݵ�text�Ƿ�Ϊ""
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

	// ��ʾToast
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
	 * ��Log ShowLog
	 * 
	 * @return void
	 * @throws
	 */
	public void ShowLog(String msg) {
		Log.i("lisao", msg);
	}

	// ͨ��class����Activity
	public void startAnimActivity(Class<?> cla) {
		this.startActivity(new Intent(this, cla));
	}

	// ͨ��Intent����Activity
	public void startAnimActivity(Intent intent) {
		this.startActivity(intent);
	}

	// �Զ���ͷ���ֳ�ʼ����ֻ��һ������
	public void initOnlyTitle(String title) {
		mLayout = (HeadLayout) findViewById(R.id.actionbar);
		mLayout.setOnlyTitle(title);
	}

	// �Զ���ͷ���ֳ�ʼ������һ�����⣬��һ�����ذ�ť
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
