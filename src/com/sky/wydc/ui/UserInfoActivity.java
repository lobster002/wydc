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

	// �������̹߳�������Ϣ
	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			if (!msg.obj.toString().equals("-1")) {// -1 ��ʾ�ɹ�
				mApplication.getSpUtil().setSTRINGUSERINFO(msg.obj.toString());
				dialog.dismiss();
				ShowToast("������� ");
				finish();
			} else {
				ShowToast("����ʧ��");
				dialog.dismiss();
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_userinfo);
		// ��ʼ������
		ininView();
		// ��ʼ���¼�
		initEvent();
	}

	private void ininView() {
		// ��ʾ������ ���÷��ذ�ť
		initLeftBtnAndTitle("������Ϣ�޸�");
		// ȡ�ø����ؼ�
		user = CurrentUserUitl.getCurUser(mApplication);
		ed_update_nickname = (TextView) findViewById(R.id.ed_update_nickname);
		ed_update_nickname.setText(user.getNickname());
		ed_update_address = (TextView) findViewById(R.id.ed_update_address);
		ed_update_address.setText(user.getAddress());
		btn_reset_update = (Button) findViewById(R.id.btn_reset_update);
		btn_update = (Button) findViewById(R.id.btn_update);
		dialog = new ProgressDialog(this);
		dialog.setMessage("������");
		dialog.setCanceledOnTouchOutside(false);
	}

	private void initEvent() {
		// ���õ���¼�
		btn_reset_update.setOnClickListener(this);
		btn_update.setOnClickListener(this);
	}

	List<BasicNameValuePair> parms;

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_reset_update:// ���ð�ť
			ed_update_nickname.setText(user.getNickname());
			ed_update_address.setText(user.getAddress());
			break;
		case R.id.btn_update:// ȷ���޸�
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

	// ���߳�
	class MyThread implements Runnable {
		private String ip;
		private List<BasicNameValuePair> parms;
		private int what;

		// ��ʼ��
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
