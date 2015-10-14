package com.sky.wydc.ui;

import com.sky.wydc.R;
import com.sky.wydc.view.ZoomImageView;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Window;

/*
 * ������ʾ��ͼ 
 * */
public class ShowActivity extends Activity {

	//�Զ���ؼ�  �̳�SmartImageView  ���Ը���url ��ȡͼƬ��ʾ
	private ZoomImageView mImageViews;
	private String imgurl;  //ͼƬurl

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//���ò���ʾ������
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		//���ز���
		setContentView(R.layout.activity_show);
		//��ʼ��
		initView();
	}

	private void initView() {
		//ȡ�����
		mImageViews = (ZoomImageView) findViewById(R.id.id_zoomimg);
		//��ȡurl
		imgurl = getIntent().getStringExtra("url");
		//����url ��ʾͼƬ
		mImageViews.setImageUrl(imgurl);
	}

	//�������ؼ� ����Ƿ��ز����ٵ�ǰ����
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
