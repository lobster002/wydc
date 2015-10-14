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
 * �û��ĵ�¼����
 */
public class LoginActivity extends BaseActivity implements OnClickListener {

	private EditText et_username;// �û��������
	private EditText et_password;// ���������

	private Button btn_login;// ��¼��ť
	private Button btn_register;// ע�ᰴť
	private String username;// �û���û����������ַ���
	private String password;// �û�������������ַ���
	private ProgressDialog dialog;// ���������
	// handler������Ϣ����
	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			String result = msg.obj.toString();
			// �жϷ���ֵ�Ƿ���ȷ
			if (!result.equals("-1")) {
				mApplication.getSpUtil().setSTRINGUSERINFO(result);// ���뻺���û���Ϣ
				mApplication.getSpUtil().setIS_FRIST_LOGIN(
						CurrentUserUitl.getCurUser(mApplication).getId());// ���뻺���û���ID
				startAnimActivity(MainActivity.class);// ���� ������
				dialog.dismiss();// ��������ʧ
				finish();// ������ǰ����
			} else {
				dialog.dismiss();
				ShowToast("�˺Ż��������");
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);// ����ʾactionbar
		getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);// ��ֹ���뷨����̸��������
		setContentView(R.layout.activity_login);
		initView();// ��ʼ����ͼ
		initEvent(); // ��ʼ�������¼�
	}

	// ��ʼ����ͼ
	private void initView() {
		initOnlyTitle("�û���¼");// ��ʼ���Զ���ͷ����
		// �ҵ����е�������
		et_username = (EditText) findViewById(R.id.et_username);
		et_password = (EditText) findViewById(R.id.et_password);
		btn_login = (Button) findViewById(R.id.btn_login);
		btn_register = (Button) findViewById(R.id.btn_register);
		// �½�������
		dialog = new ProgressDialog(this);
		dialog.setMessage("��½�С�����");
		dialog.setCanceledOnTouchOutside(false);
	}

	// ��ʼ�������¼�
	private void initEvent() {
		btn_login.setOnClickListener(this);
		btn_register.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_login:// ��¼��ť�ĵ���¼�
			// �ȱ�����֤��������ݣ�����û���������
			username = et_username.getText().toString();
			password = et_password.getText().toString();
			if (username == null || username.isEmpty()) {
				ShowToast("�û�������Ϊ��");
				return;
			}
			if (password == null || password.isEmpty()) {
				ShowToast("���벻��Ϊ��");
				return;
			}
			// �������̷߳�������
			new Thread(new MyThread()).start();
			dialog.show();// ����������
			break;
		case R.id.btn_register:// ע�ᰴť����¼�
			startAnimActivity(RegisterActivity.class);// ��ת��ע�����
			break;
		}
	}

	class MyThread implements Runnable {

		@Override
		public void run() {
			// ������POST�Ĳ����б����û��������ݻ�ò���װ����
			List<BasicNameValuePair> parms = new ArrayList<BasicNameValuePair>();
			parms.add(new BasicNameValuePair("n", username));
			parms.add(new BasicNameValuePair("p", password));
			// ͨ��ƴ���ַ�����ʽ����Ҫ���ʵ�URL��ַ����ͨ���������������
			String str = HttpUtils.postRequest(Config.IP_ADDRESS
					+ "/android/userlogin", parms);
			Message message = Message.obtain();// �����Ϣ�Ķ���
			message.obj = str;// ����Ҫ������Ϣ
			handler.sendMessage(message);// ����Ϣ������handler
		}
	}

}
