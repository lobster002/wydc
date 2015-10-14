package com.sky.wydc.ui;

import java.util.ArrayList;
import java.util.List;
import org.apache.http.message.BasicNameValuePair;
import com.sky.wydc.R;
import com.sky.wydc.config.Config;
import com.sky.wydc.httputils.HttpUtils;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

public class RegisterActivity extends BaseActivity implements OnClickListener {
	// 四个输入框
	private EditText ed_username;
	private EditText ed_password;
	private EditText ed_repassword;
	private EditText ed_address;
	// 两个按钮，重置，提交
	private Button btn_reset;
	private Button btn_submit;
	// 用于获得用户名，密码，确认密码的输入框数据
	private String username;
	private String password;
	private String repassword;
	// 进度条
	private ProgressDialog dialog;

	private  Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			String result = msg.obj.toString();
			// 验证接收的数据
			if (!result.equals("n")) {
				dialog.dismiss();
				ShowToast("注册成功！");
				finish();
			} else {
				dialog.dismiss();
				ShowToast("注册失败。。用户名已被注册");
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);// 防止输入法软件盘覆盖输入框
		setContentView(R.layout.activity_register);
		initView();// 初始化所有的视图
		initEvent();// 初始化所有的事件
	}

	private void initView() {
		initLeftBtnAndTitle("用户注册");// 初始化自定义头部局
		// 找到所有UI组件
		ed_username = (EditText) findViewById(R.id.ed_register_username);
		ed_password = (EditText) findViewById(R.id.ed_register_password);
		ed_repassword = (EditText) findViewById(R.id.ed_register_repassword);
		ed_address = (EditText) findViewById(R.id.ed_register_address);
		btn_reset = (Button) findViewById(R.id.btn_reset);
		btn_submit = (Button) findViewById(R.id.btn_submit);
		// 初始化进度条
		dialog = new ProgressDialog(this);
		dialog.setMessage("正在提交数据。。。");
		dialog.setCanceledOnTouchOutside(false);
	}

	// 设置监听
	private void initEvent() {
		// 两个按钮的监听事件设置
		btn_reset.setOnClickListener(this);
		btn_submit.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_reset:// 如果为重置按钮，将所有输入数据清空
			ed_username.setText("");
			ed_password.setText("");
			ed_repassword.setText("");
			ed_address.setText("");
			break;
		case R.id.btn_submit:// 提交按钮
			// 先获得用户的输入数据
			username = ed_username.getText().toString();
			password = ed_password.getText().toString();
			repassword = ed_repassword.getText().toString();
			// 本地验证数据是否为空
			if (username == null || username.isEmpty()) {
				ShowToast("用户名不得为空");
				return;
			}
			if (password == null || password.isEmpty() || repassword == null
					|| repassword.isEmpty()) {
				ShowToast("密码不得为空");
				return;
			}
			if (!password.equals(repassword)) {
				ShowToast("两次 密码输入不一致 ！");
				return;
			}

			// 开启线程访问网络
			new Thread(new MyThread()).start();
			dialog.show();
			break;
		}
	}

	class MyThread implements Runnable {

		@Override
		public void run() {
			List<BasicNameValuePair> parms = new ArrayList<BasicNameValuePair>();
			parms.add(new BasicNameValuePair("n", username));
			parms.add(new BasicNameValuePair("p", password));
			// 通过工具类访问网络，并接收返回的数据
			String str = HttpUtils.postRequest(Config.IP_ADDRESS
					+ "/android/UserResiger", parms);
			str = str.replace("\"", "");// 替换掉数据中的引号
			Message message = Message.obtain();
			message.obj = str;// 设置消息传输的数据
			handler.sendMessage(message);// 发送消息
		}
	}
}
