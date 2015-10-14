package com.sky.wydc.ui;

import android.os.Bundle;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sky.wydc.R;

public class DescripeActivity extends ActivityBase {
	private TextView tv = null;
	private int index = -1;
	private LinearLayout layout = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 设置不显示标题栏
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		// 加载布局
		setContentView(R.layout.activity_descripe);
		// 接受上一界面传递的参数
		Bundle mBundle = this.getIntent().getExtras();
		index = mBundle.getInt("index");
		// 取得组件
		tv = (TextView) findViewById(R.id.descripe);
		initLeftBtnAndTitle("");

		// 通过获得参数的不同显示不同的内容
		switch (index) {
		case -1:
			initLeftBtnAndTitle("关于我们");
			layout = (LinearLayout) findViewById(R.id.fulllayput);
			layout.setBackgroundResource(R.drawable.aboutus);
			break;
		case 1:
			tv.setText(R.string.zhifu);
			break;
		case 2:
			tv.setText(R.string.shoujicuo);
			break;
		case 3:
			tv.setText(R.string.other);
			break;
		case 4:
			tv.setText(R.string.tuikuan);
			break;
		case 5:
			tv.setText(R.string.daozhang);
			break;
		case 6:
			tv.setText(R.string.xiugai);
			break;
		}
	}
}
