package com.sky.wydc.ui;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.message.BasicNameValuePair;

import com.sky.wydc.R;
import com.sky.wydc.bean.Dishes;
import com.sky.wydc.config.Config;
import com.sky.wydc.httputils.HttpUtils;
import com.sky.wydc.view.SmartImageView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

public class DishesDetaliActivity extends ActivityBase implements
		OnClickListener {

	// UI组件
	private SmartImageView imageView = null;
	private Button pay = null;
	private Button gouwuche = null;
	private TextView content = null;
	private TextView newprice = null;
	private ProgressDialog dialog = null;
	private Button dish_detail_cut;
	private Button dish_detail_add;
	private EditText dish_detail_sum;
	private EditText dishes_remark;
	private String remark;

	private Dishes dishes = null;// 传�?�过来的序列化对�?

	private int sum = 1;// 份数

	private static final int PAY = 1;// 立即支付
	private static final int ADD_CART = 2;// 加入到购物车

	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case PAY:
				if (msg.obj.toString().equals("y")) {
					dialog.dismiss();
					ShowToast("支付成功");
				} else {
					ShowToast("未支付成功，请重试");
					dialog.dismiss();
				}
				break;
			case ADD_CART:
				if (msg.obj.toString().equals("y")) {
					dialog.dismiss();
					ShowToast("添加购物车完成");
				} else {
					ShowToast("未添加成功，请重试");
					dialog.dismiss();
				}
				break;
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activtiy_dishes_detail);
		getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);// 防止输入法软件盘覆盖输入�?
		dishes = (Dishes) getIntent().getSerializableExtra("dishes");// 获得传�?�过来的序列化对�?
		super.initLeftBtnAndTitle(dishes.getName());// 设置标题为单品名�?
		init();
	}

	private void init() {

		imageView = (SmartImageView) findViewById(R.id.dish_detail_pic);
		LayoutParams paramas = (LayoutParams) imageView.getLayoutParams();
		paramas.height = mScreenWidth;// 修改控件的高等于宽的�?
		paramas.width = mScreenWidth;

		imageView.setLayoutParams(paramas);
		imageView.setImageUrl(Config.IP_ADDRESS + "/" + dishes.getImageurl());

		content = (TextView) findViewById(R.id.dish_detail_content);
		content.setText(dishes.getContext());

		newprice = (TextView) findViewById(R.id.dish_detail_newprice);
		newprice.setText("¥:" + dishes.getPrice() + "");

		dish_detail_sum = (EditText) findViewById(R.id.dish_detail_sum);
		dish_detail_sum.setText(sum + "");

		dialog = new ProgressDialog(this);
		dialog.setMessage("请等待�?��?��??");
		dialog.setCanceledOnTouchOutside(false);

		pay = (Button) findViewById(R.id.dishes_detail_zhifu);
		gouwuche = (Button) findViewById(R.id.dishes_detail_gouwuche);
		dish_detail_cut = (Button) findViewById(R.id.dish_detail_cut);
		dish_detail_add = (Button) findViewById(R.id.dish_detail_add);
		dishes_remark = (EditText) findViewById(R.id.dish_detail_remark);
		// 设置监听事件
		pay.setOnClickListener(this);
		gouwuche.setOnClickListener(this);
		dish_detail_cut.setOnClickListener(this);
		dish_detail_add.setOnClickListener(this);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == KeyEvent.KEYCODE_BACK) {
			finish();
			return true;
		} else
			return super.onKeyDown(keyCode, event);
	}

	List<BasicNameValuePair> parms;// 参数列表

	@Override
	public void onClick(View v) {
		switch (v.getId()) {

		case R.id.dishes_detail_gouwuche:// 设置参数，启动线程访问网�?
			remark = dishes_remark.getText().toString();
			if (remark.equals("") || remark.isEmpty()) {
				remark = dishes.getName();
			}
			dialog.show();
			parms = new ArrayList<BasicNameValuePair>();
			parms.add(new BasicNameValuePair("uid", mApplication.getSpUtil()
					.getIS_FRIST_LOGIN() + ""));
			parms.add(new BasicNameValuePair("n", dishes.getName()));
			parms.add(new BasicNameValuePair("sum", sum + ""));
			parms.add(new BasicNameValuePair("remark", remark));
			new Thread(new MyThread(
					Config.IP_ADDRESS + "/android/AddCartNopay", parms,
					ADD_CART)).start();
			break;
		case R.id.dishes_detail_zhifu:// 设置参数，启动线程访问网�?
			dialog.show();
			remark = dishes_remark.getText().toString();
			if (remark.equals("") || remark.isEmpty()) {
				remark = dishes.getName();
			}
			parms = new ArrayList<BasicNameValuePair>();
			parms.add(new BasicNameValuePair("uid", mApplication.getSpUtil()
					.getIS_FRIST_LOGIN() + ""));
			parms.add(new BasicNameValuePair("n", dishes.getName()));
			parms.add(new BasicNameValuePair("ispay", "1"));
			parms.add(new BasicNameValuePair("sum", sum + ""));
			parms.add(new BasicNameValuePair("remark", remark));
			new Thread(new MyThread(Config.IP_ADDRESS + "/android/AddCartPay",
					parms, PAY)).start();
			break;
		case R.id.dish_detail_cut:// 份数-1
			sum--;
			if (sum < 1) {
				sum = 1;
			}
			dish_detail_sum.setText(sum + "");
			break;
		case R.id.dish_detail_add:// 份数+1
			sum++;
			dish_detail_sum.setText(sum + "");
			break;
		}
	}

	// 访问网络的子线程
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
			str = str.replace("\"", "");
			Message message = Message.obtain();
			Log.i("zhifu", str);
			message.obj = str;
			message.what = what;
			handler.sendMessage(message);
		}
	}
}
