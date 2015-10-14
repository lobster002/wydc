package com.sky.wydc.ui;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.message.BasicNameValuePair;

import com.sky.wydc.R;
import com.sky.wydc.bean.User;
import com.sky.wydc.config.Config;
import com.sky.wydc.httputils.HttpUtils;
import com.sky.wydc.utils.CurrentUserUitl;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

public class UserInfoActivity extends BaseActivity implements OnClickListener {

	private TextView ed_update_nickname;
	private TextView ed_update_address;
	private Button btn_reset_update;
	private Button btn_update;
	private String nickname;
	private String address;
	private static int UPDATE = 1;
	private User user;
	private ProgressDialog dialog = null;

	// 接收子线程过来的消息
	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			if (!msg.obj.toString().equals("-1")) {// -1 表示成功
				mApplication.getSpUtil().setSTRINGUSERINFO(msg.obj.toString());
				dialog.dismiss();
				ShowToast("更改完成 ");
				finish();
			} else {
				ShowToast("更改失败");
				dialog.dismiss();
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_userinfo);
		// 初始化布局
		ininView();
		// 初始化事件
		initEvent();
	}

	private void ininView() {
		// 显示标题栏 设置返回按钮
		initLeftBtnAndTitle("个人信息修改");
		// 取得各个控件
		user = CurrentUserUitl.getCurUser(mApplication);
		ed_update_nickname = (TextView) findViewById(R.id.ed_update_nickname);
		ed_update_nickname.setText(user.getNickname());
		ed_update_address = (TextView) findViewById(R.id.ed_update_address);
		ed_update_address.setText(user.getAddress());
		btn_reset_update = (Button) findViewById(R.id.btn_reset_update);
		btn_update = (Button) findViewById(R.id.btn_update);
		dialog = new ProgressDialog(this);
		dialog.setMessage("更改中");
		dialog.setCanceledOnTouchOutside(false);
	}

	private void initEvent() {
		// 设置点击事件
		btn_reset_update.setOnClickListener(this);
		btn_update.setOnClickListener(this);
	}

	List<BasicNameValuePair> parms;

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_reset_update:// 重置按钮
			ed_update_nickname.setText(user.getNickname());
			ed_update_address.setText(user.getAddress());
			break;
		case R.id.btn_update:// 确定修改
			nickname = ed_update_nickname.getText().toString();
			address = ed_update_address.getText().toString();
			parms = new ArrayList<BasicNameValuePair>();
			parms.add(new BasicNameValuePair("n", nickname));
			parms.add(new BasicNameValuePair("d", address));
			parms.add(new BasicNameValuePair("id", user.getId() + ""));
			new Thread(new MyThread(Config.IP_ADDRESS
					+ "/android/UpdateUserIfo", parms, UPDATE)).start();
			dialog.show();
			break;
		}
	}

	// 子线程
	class MyThread implements Runnable {
		private String ip;
		private List<BasicNameValuePair> parms;
		private int what;

		// 初始化
		public MyThread(String ip, List<BasicNameValuePair> parms, int what) {
			this.ip = ip;
			this.parms = parms;
			this.what = what;
		}

		@Override
		public void run() {
			String str = HttpUtils.postRequest(ip, parms);
			Message message = Message.obtain();
			message.obj = str;
			Log.i("update", str + "sss");
			// message.what = what;
			handler.sendMessage(message);
		}
	}
}
