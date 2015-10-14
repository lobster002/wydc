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
	// �ĸ������
	private EditText ed_username;
	private EditText ed_password;
	private EditText ed_repassword;
	private EditText ed_address;
	// ������ť�����ã��ύ
	private Button btn_reset;
	private Button btn_submit;
	// ���ڻ���û��������룬ȷ����������������
	private String username;
	private String password;
	private String repassword;
	// ������
	private ProgressDialog dialog;

	private  Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			String result = msg.obj.toString();
			// ��֤���յ�����
			if (!result.equals("n")) {
				dialog.dismiss();
				ShowToast("ע��ɹ���");
				finish();
			} else {
				dialog.dismiss();
				ShowToast("ע��ʧ�ܡ����û����ѱ�ע��");
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);// ��ֹ���뷨����̸��������
		setContentView(R.layout.activity_register);
		initView();// ��ʼ�����е���ͼ
		initEvent();// ��ʼ�����е��¼�
	}

	private void initView() {
		initLeftBtnAndTitle("�û�ע��");// ��ʼ���Զ���ͷ����
		// �ҵ�����UI���
		ed_username = (EditText) findViewById(R.id.ed_register_username);
		ed_password = (EditText) findViewById(R.id.ed_register_password);
		ed_repassword = (EditText) findViewById(R.id.ed_register_repassword);
		ed_address = (EditText) findViewById(R.id.ed_register_address);
		btn_reset = (Button) findViewById(R.id.btn_reset);
		btn_submit = (Button) findViewById(R.id.btn_submit);
		// ��ʼ��������
		dialog = new ProgressDialog(this);
		dialog.setMessage("�����ύ���ݡ�����");
		dialog.setCanceledOnTouchOutside(false);
	}

	// ���ü���
	private void initEvent() {
		// ������ť�ļ����¼�����
		btn_reset.setOnClickListener(this);
		btn_submit.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_reset:// ���Ϊ���ð�ť�������������������
			ed_username.setText("");
			ed_password.setText("");
			ed_repassword.setText("");
			ed_address.setText("");
			break;
		case R.id.btn_submit:// �ύ��ť
			// �Ȼ���û�����������
			username = ed_username.getText().toString();
			password = ed_password.getText().toString();
			repassword = ed_repassword.getText().toString();
			// ������֤�����Ƿ�Ϊ��
			if (username == null || username.isEmpty()) {
				ShowToast("�û�������Ϊ��");
				return;
			}
			if (password == null || password.isEmpty() || repassword == null
					|| repassword.isEmpty()) {
				ShowToast("���벻��Ϊ��");
				return;
			}
			if (!password.equals(repassword)) {
				ShowToast("���� �������벻һ�� ��");
				return;
			}

			// �����̷߳�������
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
			// ͨ��������������磬�����շ��ص�����
			String str = HttpUtils.postRequest(Config.IP_ADDRESS
					+ "/android/UserResiger", parms);
			str = str.replace("\"", "");// �滻�������е�����
			Message message = Message.obtain();
			message.obj = str;// ������Ϣ���������
			handler.sendMessage(message);// ������Ϣ
		}
	}
}
