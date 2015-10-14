package com.sky.wydc.ui;

import java.util.ArrayList;
import java.util.List;
import org.apache.http.message.BasicNameValuePair;
import com.sky.wydc.R;
import com.sky.wydc.config.Config;
import com.sky.wydc.httputils.HttpUtils;
import com.sky.wydc.utils.CurrentUserUitl;
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

/**
 * 用户的登录界面
 */
public class LoginActivity extends BaseActivity implements OnClickListener {

	private EditText et_username;// 用户名输入框
	private EditText et_password;// 密码输入框

	private Button btn_login;// 登录按钮
	private Button btn_register;// 注册按钮
	private String username;// 用获得用户名输入框的字符串
	private String password;// 用获得密码输入框的字符串
	private ProgressDialog dialog;// 定义进度条
	// handler对象消息接收
	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			String result = msg.obj.toString();
			// 判断返回值是否正确
			if (!result.equals("-1")) {
				mApplication.getSpUtil().setSTRINGUSERINFO(result);// 存入缓存用户信息
				mApplication.getSpUtil().setIS_FRIST_LOGIN(
						CurrentUserUitl.getCurUser(mApplication).getId());// 存入缓存用户的ID
				startAnimActivity(MainActivity.class);// 启动 主界面
				dialog.dismiss();// 进度条消失
				finish();// 结束当前界面
			} else {
				dialog.dismiss();
				ShowToast("账号或密码错误");
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);// 不显示actionbar
		getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);// 防止输入法软件盘覆盖输入框
		setContentView(R.layout.activity_login);
		initView();// 初始化视图
		initEvent(); // 初始化所有事件
	}

	// 初始化视图
	private void initView() {
		initOnlyTitle("用户登录");// 初始化自定义头布局
		// 找到所有的相关组件
		et_username = (EditText) findViewById(R.id.et_username);
		et_password = (EditText) findViewById(R.id.et_password);
		btn_login = (Button) findViewById(R.id.btn_login);
		btn_register = (Button) findViewById(R.id.btn_register);
		// 新建进度条
		dialog = new ProgressDialog(this);
		dialog.setMessage("登陆中。。。");
		dialog.setCanceledOnTouchOutside(false);
	}

	// 初始化所有事件
	private void initEvent() {
		btn_login.setOnClickListener(this);
		btn_register.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_login:// 登录按钮的点击事件
			// 先本地验证输入的数据，获得用户输入数据
			username = et_username.getText().toString();
			password = et_password.getText().toString();
			if (username == null || username.isEmpty()) {
				ShowToast("用户名不得为空");
				return;
			}
			if (password == null || password.isEmpty()) {
				ShowToast("密码不得为空");
				return;
			}
			// 启动子线程访问网络
			new Thread(new MyThread()).start();
			dialog.show();// 开启进度条
			break;
		case R.id.btn_register:// 注册按钮点击事件
			startAnimActivity(RegisterActivity.class);// 跳转到注册界面
			break;
		}
	}

	class MyThread implements Runnable {

		@Override
		public void run() {
			// 先设置POST的参数列表，将用户输入数据获得并封装参数
			List<BasicNameValuePair> parms = new ArrayList<BasicNameValuePair>();
			parms.add(new BasicNameValuePair("n", username));
			parms.add(new BasicNameValuePair("p", password));
			// 通过拼接字符串方式设置要访问的URL地址，并通过工具类访问网络
			String str = HttpUtils.postRequest(Config.IP_ADDRESS
					+ "/android/userlogin", parms);
			Message message = Message.obtain();// 获得消息的对象
			message.obj = str;// 设置要发送消息
			handler.sendMessage(message);// 将消息发送至handler
		}
	}

}
