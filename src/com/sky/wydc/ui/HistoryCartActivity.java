package com.sky.wydc.ui;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.message.BasicNameValuePair;

import com.sky.wydc.R;
import com.sky.wydc.adapter.CartListAdapter;
import com.sky.wydc.bean.Cart;
import com.sky.wydc.config.Config;
import com.sky.wydc.httputils.HttpUtils;
import com.sky.wydc.httputils.JsonUtils;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Window;
import android.widget.ListView;

public class HistoryCartActivity extends ActivityBase {

	private ListView history_cart_list;
	private CartListAdapter cart_list_adapter;
	private ProgressDialog dialog = null;
	private static final int FINISH = 1;
	private List<Cart> data = new ArrayList<Cart>();

	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			String result = msg.obj.toString();
			switch (msg.what) {
			case FINISH:
				data = JsonUtils.JsonArray2Cartlist(result);
				cart_list_adapter.setList(data);
				dialog.dismiss();
				break;
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_histoty_cart);
		initView();
	}

	private void initView() {
		initLeftBtnAndTitle("历史订单");
		history_cart_list = (ListView) findViewById(R.id.history_cart_list);
		cart_list_adapter = new CartListAdapter(this, data);
		history_cart_list.setAdapter(cart_list_adapter);

		dialog = new ProgressDialog(this);
		dialog.setMessage("加载�?,请稍�?");
		dialog.setCanceledOnTouchOutside(false);

		List<BasicNameValuePair> parms = new ArrayList<BasicNameValuePair>();
		parms.add(new BasicNameValuePair("id", mApplication.getSpUtil()
				.getIS_FRIST_LOGIN() + ""));
		new Thread(new MyThread(Config.IP_ADDRESS + "/android/GetCartList",
				parms, FINISH)).start();
		dialog.show();
	}

	class MyThread implements Runnable {
		private String ip;
		private List<BasicNameValuePair> parms;
		private int what;

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
			message.what = what;
			handler.sendMessage(message);
		}
	}
}
