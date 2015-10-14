package com.sky.wydc.fragment;

import com.sky.wydc.R;
import com.sky.wydc.config.CustomApplcation;
import com.sky.wydc.view.HeadLayout;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

/**
 * Fragmenet 基类
 */
public abstract class FragmentBase extends Fragment {

	protected View contentView;

	public LayoutInflater mInflater;

	public CustomApplcation mApplication;

	private Handler handler = new Handler();

	protected HeadLayout mLayout;

	public FragmentBase() {

	}

	public void runOnWorkThread(Runnable action) {
		new Thread(action).start();
	}

	public void runOnUiThread(Runnable action) {
		handler.post(action);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRetainInstance(true);
		mApplication = CustomApplcation.getInstance();
		mInflater = LayoutInflater.from(getActivity());

	}

	Toast mToast;

	public void ShowToast(String text) {
		if (mToast == null) {
			mToast = Toast.makeText(getActivity(), text, Toast.LENGTH_SHORT);
		} else {
			mToast.setText(text);
		}
		mToast.show();
	}

	public void ShowToast(int text) {
		if (mToast == null) {
			mToast = Toast.makeText(getActivity(), text, Toast.LENGTH_LONG);
		} else {
			mToast.setText(text);
		}
		mToast.show();
	}

	public View findViewById(int paramInt) {
		return getView().findViewById(paramInt);
	}

	/**
	 * 动画启动页面 startAnimActivity
	 * @throws
	 */
	public void startAnimActivity(Intent intent) {
		this.startActivity(intent);
	}

	public void startAnimActivity(Class<?> cla) {
		getActivity().startActivity(new Intent(getActivity(), cla));
	}

	public void initOnlyTitle(String title) {
		mLayout = (HeadLayout) findViewById(R.id.actionbar);
		mLayout.setOnlyTitle(title);
	}

}
