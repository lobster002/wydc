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

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

/**
 * 购物车界
 */
public class CartActivity extends ActivityBase implements OnClickListener,
		OnItemLongClickListener {
	// UI组件
	private ListView cart_list;
	private TextView cart_sum_price;
	private Button cart_all_pay;
	private Button cart_all_no;

	private ProgressDialog dialog = null;
	private CartListAdapter cart_list_adapter;
	private List<Cart> data = new ArrayList<Cart>();

	// 四中消息的状态码
	private static final int FINISH = 1;
	private static final int PAY = 2;
	private static final int NO = 3;
	private static final int REMOVE = 4;

	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			String result = msg.obj.toString();
			float sum = 0;
			switch (msg.what) {
			case FINISH:
				// 接收到的json字符串解析为list<cart>格式
				data = JsonUtils.JsonArray2Cartlist(result);
				cart_list_adapter.setList(data);
				if (data.size() == 0)
					ShowToast("购物车空空的");
				sum = 0;
				// 遍历数据，计算价�?
				for (Cart cart : data) {
					sum += cart.getPrice();
				}
				cart_sum_price.setText("¥" + sum + "�?");
				dialog.dismiss();
				break;

			case PAY:
				result = result.replace("\"", "");// 去除消息中的"
				if (result.equals("y")) {
					dialog.dismiss();
					// 计算价格
					for (Cart cart : data) {
						sum += cart.getPrice();
					}
					ShowToast("支付完成，本次共消费  ¥�?" + sum + "�?");
					data.clear();
					cart_list_adapter.setList(data);
				}
				cart_sum_price.setText("¥" + 0 + "�?");
				break;
			case NO:
				// 删除购物车中的所有物�?
				result = result.replace("\"", "");
				if (result.equals("y")) {
					dialog.dismiss();
					data.clear();
					cart_list_adapter.setList(data);
				}
				cart_sum_price.setText("¥" + 0 + "�?");
			case REMOVE:
				// 删除�?个物�?
				result = result.replace("\"", "");
				if (result.equals("y")) {
					sum = 0;
					// 重新计算价格
					for (Cart cart : data) {
						sum += cart.getPrice() * cart.getSum();
					}
					cart_sum_price.setText("¥" + sum + "�?");
				}
				break;
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_cart);
		initView();
		initEvent();
	}

	// 初始视图
	private void initView() {
		initLeftBtnAndTitle("我的购物�?");
		cart_list = (ListView) findViewById(R.id.cart_list);
		cart_sum_price = (TextView) findViewById(R.id.cart_sum_price);
		cart_all_no = (Button) findViewById(R.id.cart_all_no);
		cart_all_pay = (Button) findViewById(R.id.cart_all_pay);

		cart_list_adapter = new CartListAdapter(this, data);
		cart_list.setAdapter(cart_list_adapter);

		dialog = new ProgressDialog(this);
		dialog.setMessage("加载�?,请稍�?");
		dialog.setCanceledOnTouchOutside(false);

		List<BasicNameValuePair> parms = new ArrayList<BasicNameValuePair>();
		parms.add(new BasicNameValuePair("id", mApplication.getSpUtil()
				.getIS_FRIST_LOGIN() + ""));
		// 访问网络
		new Thread(new MyThread(
				Config.IP_ADDRESS + "/android/GetCartNoPayList", parms, FINISH))
				.start();
		dialog.show();
	}

	// 初始事件
	private void initEvent() {
		cart_all_no.setOnClickListener(this);
		cart_all_pay.setOnClickListener(this);
		cart_list.setOnItemLongClickListener(this);
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

	List<BasicNameValuePair> parms;

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.cart_all_no:// 删除全部商品
			parms = new ArrayList<BasicNameValuePair>();
			parms.add(new BasicNameValuePair("uid", mApplication.getSpUtil()
					.getIS_FRIST_LOGIN() + ""));
			new Thread(new MyThread(Config.IP_ADDRESS
					+ "/android/DeleteNoPayCart", parms, NO)).start();
			dialog.show();
			break;

		case R.id.cart_all_pay:// 全部付款
			parms = new ArrayList<BasicNameValuePair>();
			parms.add(new BasicNameValuePair("uid", mApplication.getSpUtil()
					.getIS_FRIST_LOGIN() + ""));
			new Thread(new MyThread(Config.IP_ADDRESS
					+ "/android/AllPayCartNoPay", parms, PAY)).start();
			dialog.show();
			break;
		}
	}

	// 长按事件
	@Override
	public boolean onItemLongClick(AdapterView<?> parent, View view,
			final int Position, long id) {
		AlertDialog.Builder builder = new Builder(this);
		builder.setTitle("提示");
		builder.setMessage("是否要删除所选择的商�??");
		builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// 删除单个购物车商�?
				parms = new ArrayList<BasicNameValuePair>();
				parms.add(new BasicNameValuePair("id", data.get(Position)
						.getId() + ""));
				new Thread(new MyThread(Config.IP_ADDRESS
						+ "/android/delCartNoPay", parms, REMOVE)).start();
				cart_list_adapter.remove(Position);
			}
		});
		builder.setNegativeButton("取消", null);
		builder.create().show();
		return false;
	}
}
