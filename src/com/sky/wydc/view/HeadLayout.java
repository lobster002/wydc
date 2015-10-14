package com.sky.wydc.view;

import com.sky.wydc.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class HeadLayout extends LinearLayout {
	private LayoutInflater mInflater;
	private View mHeader;
	private TextView mHeaderTvSubTitle;
	private LinearLayout leftBtnBack;

	public HeadLayout(Context context, AttributeSet attr) {
		super(context, attr);
		mInflater = LayoutInflater.from(context);
		mHeader = mInflater.inflate(R.layout.header, this);
		mHeaderTvSubTitle = (TextView) findViewById(R.id.id_actionbar_text);
		leftBtnBack = (LinearLayout) findViewById(R.id.id_actionbar_back);
	}

	public interface onLeftImageButtonClickListener {
		void click();
	};

	public onLeftImageButtonClickListener lefeBtnClick;

	public void setLeftAndTitle(String title) {
		mHeaderTvSubTitle.setText(title);
		leftBtnBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (lefeBtnClick != null) {
					lefeBtnClick.click();
				}
			}
		});
	}

	public void setOnlyTitle(String title) {
		leftBtnBack.setVisibility(GONE);
		mHeaderTvSubTitle.setText(title);
		mHeaderTvSubTitle.setGravity(Gravity.CENTER);
	}

	public void setLefeBtnClick(
			onLeftImageButtonClickListener onLefBtnClickListener) {
		this.lefeBtnClick = onLefBtnClickListener;
	}
}
