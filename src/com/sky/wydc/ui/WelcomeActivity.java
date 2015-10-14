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
		// 不显示程序的标题栏
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		// 加载布局
		setContentView(R.layout.activty_welcome);
		init();
		// 检查网络连接
		if (!CommonUtils.isNetworkAvailable(this)) {
			ShowToast("暂无网络连接，请检查你的网络");
		}
	}

	private void init() {
		SharePreferenceUtil util = mApplication.getSpUtil();// 判断是否第一次安装
		// 如果是第一次安装，跳出引导页
		if (!util.isFristIn()) {
			// 判断是否第一次登陆
			if (util.getIS_FRIST_LOGIN() == 0) {
				mHandler.sendEmptyMessageDelayed(GO_LOGIN, 2000);// 跳转到登录界面
			} else {
				mHandler.sendEmptyMessageDelayed(GO_HOME, 2000);// 跳转主界面
			}
		} else {
			mHandler.sendEmptyMessageDelayed(GO_GUIDE, 2000);// 跳转到引导页
		}
	}

	@SuppressLint("HandlerLeak")
	private Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case GO_HOME:
				startAnimActivity(MainActivity.class);
				finish();// 必须要finish掉，不然回退仍可访问上一界面
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
