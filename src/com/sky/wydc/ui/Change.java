package com.sky.wydc.ui;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.message.BasicNameValuePair;

import com.sky.wydc.R;
import com.sky.wydc.adapter.ExpandableListViewAdapter;
import com.sky.wydc.bean.Meal;
import com.sky.wydc.config.Config;
import com.sky.wydc.httputils.HttpUtils;
import com.sky.wydc.httputils.JsonUtils;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ExpandableListView;

public class Change extends ActivityBase implements OnClickListener {
	// 扩展ListView
	private ExpandableListView mExpandableListView = null;
	// 扩展ListView的适配器
	private ExpandableListViewAdapter mExpandableListViewAdapter = null;
	private Meal meal = null;
	private Button meal_addcart = null;
	private Button meal_pay = null;
	private int lastposition = -1;
	private int POS = -1;
	private ProgressDialog dialog = null;
	// 标记数组 用于记录各个Group的打开或关闭状态
	private int[] check = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0 };
	List<BasicNameValuePair> parms;
	// 全局静态变量
	public static final int CHANGE = 1;
	public static final int ADD_CART = 2;
	public static final int PAY = 3;

	// 用来接收子线程传递的消息
	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case CHANGE:// 点击换菜时
				if (!msg.obj.toString().equals("-1")) {// 如果查询结果不为空
					if (check[POS] == 0)// 如果该分组为关闭状态 直接返回
						break;
					// 修改数据源
					mExpandableListViewAdapter.gridViewChild[POS] = JsonUtils
							.JsonArray2StringArray(msg.obj.toString());
					// 更新显示
					mExpandableListViewAdapter.notifyDataSetChanged();
				} else {
					ShowToast("没有可替换菜品");
				}
				break;
			case ADD_CART:
				// 添加购物车
				if (msg.obj.toString().equals("\"y\"")) {
					// 返回 y 即添加成功
					dialog.dismiss();
					ShowToast("添加购物车成功");
				} else {// 不成功时的提示
					ShowToast("未添加成功，请重试一下");
					dialog.dismiss();
				}
				break;
			case PAY:
				// 支付事件
				if (msg.obj.toString().equals("\"y\"")) {// 支付成功时 显示 支付成功
					dialog.dismiss();
					ShowToast("支付成功");
				} else {
					ShowToast("未支付成功，请重试一下");
					dialog.dismiss();
				}
				break;
			}
		}
	};

	// 界面打开时调用 OnCreat方法
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 设置不显示标题栏
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		// 加载布局
		setContentView(R.layout.change);
		// 获取 meal 资源
		meal = (Meal) getIntent().getSerializableExtra("meal");
		initLeftBtnAndTitle(meal.getName());
		// 初始哈布局
		init();
	}

	private void init() {
		// 初始化 消息框
		dialog = new ProgressDialog(this);
		dialog.setMessage("请等待。。。");
		dialog.setCanceledOnTouchOutside(false);

		// 取得控件
		mExpandableListView = (ExpandableListView) findViewById(R.id.expandableListView);
		// 设置适配器
		mExpandableListViewAdapter = new ExpandableListViewAdapter(this, meal);
		mExpandableListView.setAdapter(mExpandableListViewAdapter);
		// 设置 Group 点击事件
		mExpandableListView
				.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
					@Override
					public boolean onGroupClick(ExpandableListView parent,
							View v, int groupPosition, long id) {
						// 记录点击位置
						POS = groupPosition;
						// 标记 打开关闭状态
						check[groupPosition] = ~check[groupPosition];
						// 关闭 则 返回
						if (check[groupPosition] == 0)
							return false;
						// 记录本次点击位置
						lastposition = groupPosition;
						// 从Web端拿数据
						parms = new ArrayList<BasicNameValuePair>();
						parms.add(new BasicNameValuePair("name",
								mExpandableListViewAdapter.group[groupPosition]));
						new Thread(new MyThread(Config.IP_ADDRESS
								+ "/android/getOneDishes", parms, CHANGE))
								.start();
						mExpandableListViewAdapter.notifyDataSetChanged();
						return false;
					}
				});
		// 设置Child点击事件
		mExpandableListView
				.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
					@Override
					public boolean onChildClick(ExpandableListView parent,
							View v, int groupPosition, int childPosition,
							long id) {
						return false;
					}
				});
		// 取得组件 设置监听
		meal_addcart = (Button) findViewById(R.id.meal_addcart);
		meal_addcart.setOnClickListener(this);
		meal_pay = (Button) findViewById(R.id.meal_pay);
		meal_pay.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// 点击事件触发时 根据触发的控件的不同 执行不通的代码
		switch (v.getId()) {
		case R.id.meal_addcart:// 添加购物车
			dialog.show();// 显示 弹框
			// 在子线程内添加到购物车
			parms = new ArrayList<BasicNameValuePair>();
			parms.add(new BasicNameValuePair("uid", mApplication.getSpUtil()
					.getIS_FRIST_LOGIN() + ""));
			parms.add(new BasicNameValuePair("id", meal.getId() + ""));
			parms.add(new BasicNameValuePair("sum", "1"));
			StringBuilder builder = new StringBuilder();
			for (String str : mExpandableListViewAdapter.group) {
				builder.append(str);
				builder.append(";");
			}
			parms.add(new BasicNameValuePair("remark", builder.toString()));
			new Thread(new MyThread(Config.IP_ADDRESS + "/android/mealtocart",
					parms, ADD_CART)).start();
			break;
		case R.id.meal_pay:// 立即支付
			dialog.show();// 显示弹框
			// 在子线程内 执行
			parms = new ArrayList<BasicNameValuePair>();
			parms.add(new BasicNameValuePair("uid", mApplication.getSpUtil()
					.getIS_FRIST_LOGIN() + ""));
			parms.add(new BasicNameValuePair("id", meal.getId() + ""));
			parms.add(new BasicNameValuePair("sum", "1"));
			StringBuilder builder2 = new StringBuilder();
			for (String str : mExpandableListViewAdapter.group) {
				builder2.append(str);
				builder2.append(";");
			}
			parms.add(new BasicNameValuePair("remark", builder2.toString()));
			new Thread(new MyThread(Config.IP_ADDRESS + "/android/mealpay",
					parms, PAY)).start();
			break;
		}
	}

	// 子线程
	class MyThread implements Runnable {
		private String ip; // 链接IP
		private List<BasicNameValuePair> parms;// 数据
		private int what; // 消息

		// 初始化
		public MyThread(String ip, List<BasicNameValuePair> parms, int what) {
			this.ip = ip;
			this.parms = parms;
			this.what = what;
		}

		@Override
		public void run() {
			// post方法 执行传递参数
			String str = HttpUtils.postRequest(ip, parms);
			Message message = Message.obtain();
			message.obj = str;
			message.what = what;
			// 返回给主线程
			handler.sendMessage(message);
		}
	}
}
