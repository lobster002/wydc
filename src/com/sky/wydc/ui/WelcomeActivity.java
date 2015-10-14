package com.sky.wydc.ui;

import com.sky.wydc.R;
import com.sky.wydc.utils.CommonUtils;
import com.sky.wydc.utils.SharePreferenceUtil;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Window;

public class WelcomeActivity extends BaseActivity {

	private static final int GO_HOME = 1;
	private static final int GO_LOGIN = 2;
	private static final int GO_GUIDE = 3;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// ����ʾ����ı�����
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		// ���ز���
		setContentView(R.layout.activty_welcome);
		init();
		// �����������
		if (!CommonUtils.isNetworkAvailable(this)) {
			ShowToast("�����������ӣ������������");
		}
	}

	private void init() {
		SharePreferenceUtil util = mApplication.getSpUtil();// �ж��Ƿ��һ�ΰ�װ
		// ����ǵ�һ�ΰ�װ����������ҳ
		if (!util.isFristIn()) {
			// �ж��Ƿ��һ�ε�½
			if (util.getIS_FRIST_LOGIN() == 0) {
				mHandler.sendEmptyMessageDelayed(GO_LOGIN, 2000);// ��ת����¼����
			} else {
				mHandler.sendEmptyMessageDelayed(GO_HOME, 2000);// ��ת������
			}
		} else {
			mHandler.sendEmptyMessageDelayed(GO_GUIDE, 2000);// ��ת������ҳ
		}
	}

	@SuppressLint("HandlerLeak")
	private Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case GO_HOME:
				startAnimActivity(MainActivity.class);
				finish();// ����Ҫfinish������Ȼ�����Կɷ�����һ����
				break;
			case GO_GUIDE:
				startAnimActivity(GuideActivity.class);
				finish();
				break;
			case GO_LOGIN:
				startAnimActivity(LoginActivity.class);
				finish();
				break;
			}
		}
	};
}
