package com.sky.wydc.ui;

import com.sky.wydc.R;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;

public class FeedBackActivity extends ActivityBase implements OnClickListener {

	// 意见反馈页面
	private Button btn = null;
	private ImageView iv = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 设置不显示 标题栏
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		// 加载布局
		setContentView(R.layout.activity_feedback);

		// 防止被软键盘挡着输入框
		InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.toggleSoftInput(InputMethodManager.RESULT_UNCHANGED_SHOWN,
				InputMethodManager.HIDE_NOT_ALWAYS);

		// 取得组件
		iv = (ImageView) findViewById(R.id.actionbar_back);
		btn = (Button) findViewById(R.id.submit_button);
		// 设置监听
		btn.setOnClickListener(this);
		iv.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.actionbar_back:// 点击返回时 返回
			FeedBackActivity.this.finish();
			break;
		case R.id.submit_button:
			// 点击提交时显示
			ShowToast("感谢您宝贵的建议，我们将努力做到更好:-D");
			break;
		}
	}

}
