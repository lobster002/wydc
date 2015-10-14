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

	// �������ҳ��
	private Button btn = null;
	private ImageView iv = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// ���ò���ʾ ������
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		// ���ز���
		setContentView(R.layout.activity_feedback);

		// ��ֹ������̵��������
		InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.toggleSoftInput(InputMethodManager.RESULT_UNCHANGED_SHOWN,
				InputMethodManager.HIDE_NOT_ALWAYS);

		// ȡ�����
		iv = (ImageView) findViewById(R.id.actionbar_back);
		btn = (Button) findViewById(R.id.submit_button);
		// ���ü���
		btn.setOnClickListener(this);
		iv.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.actionbar_back:// �������ʱ ����
			FeedBackActivity.this.finish();
			break;
		case R.id.submit_button:
			// ����ύʱ��ʾ
			ShowToast("��л������Ľ��飬���ǽ�Ŭ����������:-D");
			break;
		}
	}

}
